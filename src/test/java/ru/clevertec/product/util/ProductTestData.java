package ru.clevertec.product.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.product.entity.Product;

@Data
@Accessors(chain = true)
@Builder(setterPrefix = "with")
public class ProductTestData {

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

    public Product buildProduct() {
        return new Product(uuid, name, description, price, created);
    }
}



