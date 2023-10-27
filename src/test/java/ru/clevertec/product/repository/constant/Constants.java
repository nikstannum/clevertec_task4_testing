package ru.clevertec.product.repository.constant;

import java.math.BigDecimal;
import java.util.UUID;

public interface Constants {
    UUID UUID = java.util.UUID.fromString("b9a6d63a-bd10-4388-8912-c4ab3411c188");
    String APPLE = "Apple";
    String GREEN = "Green";
    BigDecimal APPLE_PRICE_1_05 = BigDecimal.valueOf(1.05);
    BigDecimal PEAR_PRICE_1_11 = BigDecimal.valueOf(1.11);

}
