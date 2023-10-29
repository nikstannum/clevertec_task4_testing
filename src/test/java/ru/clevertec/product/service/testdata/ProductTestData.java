package ru.clevertec.product.service.testdata;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.constant.RepoConstants;

@Data
@Builder(setterPrefix = "with")
@Accessors(chain = true)
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
