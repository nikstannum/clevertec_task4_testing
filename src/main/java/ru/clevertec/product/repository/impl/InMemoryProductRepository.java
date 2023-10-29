package ru.clevertec.product.repository.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<UUID, Product> storage = init();

    @Override
    public Optional<Product> findById(UUID uuid) {
        return storage.containsKey(uuid) ? Optional.of(storage.get(uuid)) : Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public Product save(Product product) {
        if (product.getUuid() == null) {
            UUID uuid;
            do {
                uuid = UUID.randomUUID();
            } while (storage.containsKey(uuid));
            product.setUuid(uuid);
        }
        storage.put(product.getUuid(), product);
        return product;
    }

    @Override
    public void delete(UUID uuid) {
        storage.remove(uuid);
    }

    private Map<UUID, Product> init() {
        Map<UUID, Product> map = new HashMap<>();
        UUID uuid = UUID.fromString("b9a6d63a-bd10-4388-8912-c4ab3411c188");
        Product product = Product.builder()
                .setUuid(uuid)
                .setName("Apple")
                .setDescription("Green")
                .setPrice(BigDecimal.valueOf(1.05))
                .setCreated(LocalDateTime.MIN)
                .build();
        map.put(uuid, product);
        return map;
    }
}
