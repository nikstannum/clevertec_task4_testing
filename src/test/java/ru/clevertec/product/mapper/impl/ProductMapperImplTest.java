package ru.clevertec.product.mapper.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.MapperConstants;
import ru.clevertec.product.util.ProductDtoTestData;
import ru.clevertec.product.util.RepoConstants;

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
        ProductDto dto = ProductDtoTestData.builder()
                .withName(RepoConstants.APPLE)
                .withDescription(RepoConstants.GREEN)
                .withPrice(RepoConstants.APPLE_PRICE_1_05).build().buildProductDto();

        // when
        Product actual = mapper.toProduct(dto);

        // then
        assertThat(actual).hasNoNullFieldsOrPropertiesExcept(MapperConstants.FIELD_UUID, MapperConstants.FIELD_CREATED);
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
        InfoProductDto expected = ProductDtoTestData.builder()
                .withUuid(RepoConstants.UUID)
                .withName(RepoConstants.APPLE)
                .withDescription(RepoConstants.GREEN)
                .withPrice(RepoConstants.APPLE_PRICE_1_05).build().buildInfoProductDto();

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
