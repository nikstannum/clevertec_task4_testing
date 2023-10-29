package ru.clevertec.product.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.mapper.testdata.ProductDtoTestData;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.repository.constant.RepoConstants;
import ru.clevertec.product.service.testdata.ProductTestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;
    @InjectMocks
    private ProductServiceImpl service;

    private static Stream<UUID> provideUuids() {
        return Stream.of(
                UUID.fromString("80e374af-e32e-41ad-9598-b62b9b454c96"),
                UUID.fromString("80e374af-e32e-41ad-9598-b62b9b454c97"));
    }

    @ParameterizedTest
    @MethodSource("provideUuids")
    void checkGetShouldThrowProductNotFoundExc(UUID uuid) {
        // given
        doReturn(Optional.empty()).when(repository).findById(uuid);
        // when-then
        Assertions.assertThrows(ProductNotFoundException.class, () -> service.get(uuid));
    }

    @Test
    void checkGetShouldReturnEquals() {
        // given
        Product product = Product.builder().build();
        doReturn(Optional.of(product)).when(repository).findById(RepoConstants.UUID);
        InfoProductDto expected = ProductDtoTestData.builder().build().buildInfoProductDto();
        doReturn(expected).when(mapper).toInfoProductDto(product);
        // when
        InfoProductDto actual = service.get(RepoConstants.UUID);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAll() {
        // given
        Product product = Product.builder().build();
        doReturn(Arrays.asList(product, product)).when(repository).findAll();
        InfoProductDto mapped = ProductDtoTestData.builder().build().buildInfoProductDto();
        doReturn(mapped).when(mapper).toInfoProductDto(product);
        // when
        List<InfoProductDto> actual = service.getAll();
        // then
        assertThat(actual).hasSize(2);
    }

    @Test
    void create() {
        // given
        ProductDto dtoToSave = ProductDtoTestData.builder().build().buildProductDto();
        Product productToSave = ProductTestData.builder().withUuid(null).build().buildProduct();
        doReturn(productToSave).when(mapper).toProduct(dtoToSave);

        Product savedProduct = ProductTestData.builder().build().buildProduct();
        doReturn(savedProduct).when(repository).save(productToSave);
        // when
        UUID actual = service.create(dtoToSave);
        // then
        assertThat(actual).isEqualTo(RepoConstants.UUID);
    }

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @Test
    void update() {
        // given
        Product existing = ProductTestData.builder().build().buildProduct();
        doReturn(Optional.of(existing)).when(repository).findById(RepoConstants.UUID);
        ProductDto dtoForUpdate = ProductDtoTestData.builder().withPrice(BigDecimal.TEN).build().buildProductDto();
        Product merged = ProductTestData.builder().withPrice(BigDecimal.TEN).build().buildProduct();
        doReturn(merged).when(mapper).merge(existing, dtoForUpdate);
        doReturn(merged).when(repository).save(merged);
        // when
        service.update(RepoConstants.UUID, dtoForUpdate);
        verify(repository).save(productCaptor.capture());
        Product actual = productCaptor.getValue();
        // then
        assertThat(actual).hasFieldOrPropertyWithValue("price", BigDecimal.TEN);
    }

    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @Test
    void delete() {
        // given
        UUID uuid = RepoConstants.UUID;
        // when
        service.delete(uuid);
        verify(repository, times(1)).delete(uuidCaptor.capture());
        UUID actual = uuidCaptor.getValue();
        // then
        assertThat(actual).isEqualTo(RepoConstants.UUID);
    }
}
