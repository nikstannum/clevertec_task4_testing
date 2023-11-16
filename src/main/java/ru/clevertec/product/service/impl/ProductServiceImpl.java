package ru.clevertec.product.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public InfoProductDto get(UUID uuid) {
        return productRepository.findById(uuid)
                .map(mapper::toInfoProductDto)
                .orElseThrow(() -> new ProductNotFoundException(uuid));
    }

    @Override
    public List<InfoProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(mapper::toInfoProductDto)
                .toList();
    }

    @Override
    public UUID create(ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        product.setCreated(LocalDateTime.now());
        Product saved = productRepository.save(product);
        return saved.getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        Product existing = productRepository.findById(uuid).orElseThrow(() -> new ProductNotFoundException(uuid));
        Product merged = mapper.merge(existing, productDto);
        productRepository.save(merged);
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }
}
