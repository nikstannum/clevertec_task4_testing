package ru.clevertec.product.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;

@Data
@Builder(setterPrefix = "with")
@Accessors(chain = true)
public class ProductDtoTestData {

    @Builder.Default
    private UUID uuid = RepoConstants.UUID;

    @Builder.Default
    private String name = RepoConstants.APPLE;

    @Builder.Default
    private String description = RepoConstants.GREEN;

    @Builder.Default
    private BigDecimal price = RepoConstants.APPLE_PRICE_1_05;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.MIN;

    public ProductDto buildProductDto() {
        return new ProductDto(name, description, price);
    }

    public InfoProductDto buildInfoProductDto() {
        return new InfoProductDto(uuid, name, description, price);
    }
}
