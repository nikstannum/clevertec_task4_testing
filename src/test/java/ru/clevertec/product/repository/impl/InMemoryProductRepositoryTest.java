package ru.clevertec.product.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.constant.Constants;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {
    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    void checkFindByIdShouldReturnEmpty(String string) {
        // given
        UUID uuid = UUID.fromString(string);
        // when
        Optional<Product> actual = repository.findById(uuid);
        // then
        assertThat(actual).isEmpty();
    }

    @Test
    void checkFindByIdShouldReturnEquals() {
        // given
        Product expected = Product.builder()
                .setUuid(Constants.UUID)
                .setName(Constants.APPLE)
                .setDescription(Constants.GREEN)
                .setPrice(Constants.APPLE_PRICE_1_05)
                .setCreated(LocalDateTime.MIN)
                .build();
        // when
        Product actual = repository.findById(Constants.UUID).orElseThrow();
        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkFindAllShouldHasSize1() {
        // when
        List<Product> products = repository.findAll();
        // then
        assertThat(products).hasSize(1);
    }

    @Test
    void checkSaveShouldUuidNotNull() {
        // given
        Product toSave = Product.builder()
                .setName("Pear")
                .setDescription(Constants.GREEN)
                .setPrice(Constants.PEAR_PRICE_1_11)
                .setCreated(LocalDateTime.MIN)
                .build();
        // when
        Product saved = repository.save(toSave);
        // then
        assertThat(saved.getUuid()).isNotNull();
    }

    @Test
    void delete() {
        // when
        repository.delete(Constants.UUID);
        // then
        assertThat(repository.findById(Constants.UUID)).isEmpty();
    }
}
