package ru.clevertec.product.mapper.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.constants.MapperConstants;
import ru.clevertec.product.mapper.testdata.ProductDtoTestData;
import ru.clevertec.product.repository.constant.RepoConstants;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperImplTest {
    private ProductMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapperImpl();
    }

    @Test
    void checkToProductShouldHasNullFieldsUuidAndCreated() {
        // given
        ProductDto dto = new ProductDto(RepoConstants.APPLE, RepoConstants.GREEN, RepoConstants.APPLE_PRICE_1_05);
        String[] expectedNullFields = new String[]{MapperConstants.FIELD_UUID, MapperConstants.FIELD_CREATED};
        // when
        Product actual = mapper.toProduct(dto);
        // then
        assertThat(actual).hasNoNullFieldsOrPropertiesExcept(expectedNullFields);
    }

    @Test
    void toInfoProductDto() {
        // given
        Product product = Product.builder()
                .setUuid(RepoConstants.UUID)
                .setName(RepoConstants.APPLE)
                .setDescription(RepoConstants.GREEN)
                .setPrice(RepoConstants.APPLE_PRICE_1_05)
                .setCreated(LocalDateTime.MIN)
                .build();
        InfoProductDto expected = new InfoProductDto(
                RepoConstants.UUID,
                RepoConstants.APPLE,
                RepoConstants.GREEN,
                RepoConstants.APPLE_PRICE_1_05);
        // when
        InfoProductDto actual = mapper.toInfoProductDto(product);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void merge() {
        // given
        ProductDto dto = ProductDtoTestData.builder().build().buildProductDto();
        Product product = Product.builder()
                .setUuid(RepoConstants.UUID)
                .setName("old name")
                .setDescription("old description")
                .setPrice(BigDecimal.ZERO)
                .setCreated(LocalDateTime.MIN)
                .build();
        Product expected = Product.builder()
                .setUuid(RepoConstants.UUID)
                .setName(RepoConstants.APPLE)
                .setDescription(RepoConstants.GREEN)
                .setPrice(RepoConstants.APPLE_PRICE_1_05)
                .setCreated(LocalDateTime.MIN)
                .build();
        // when
        Product actual = mapper.merge(product, dto);
        // then
        assertThat(actual).isEqualTo(expected);
    }
}
