package ru.clevertec.product.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.RepoConstants;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {

    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        repository.init();
    }

    @ParameterizedTest
    @MethodSource("provideUuids")
    void checkFindByIdShouldReturnEmpty(UUID uuid) {
        // when
        Optional<Product> actual = repository.findById(uuid);

        // then
        assertThat(actual).isEmpty();
    }

    private static Stream<UUID> provideUuids() {
        return Stream.of(
                UUID.fromString("80e374af-e32e-41ad-9598-b62b9b454c97"),
                null);
    }

    @Test
    void checkFindByIdShouldReturnEquals() {
        // given
        Product expected = Product.builder()
                .setUuid(RepoConstants.UUID)
                .setName(RepoConstants.APPLE)
                .setDescription(RepoConstants.GREEN)
                .setPrice(RepoConstants.APPLE_PRICE_1_05)
                .setCreated(LocalDateTime.MIN)
                .build();

        // when
        Product actual = repository.findById(RepoConstants.UUID).orElseThrow();

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
                .setDescription(RepoConstants.GREEN)
                .setPrice(RepoConstants.PEAR_PRICE_1_11)
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
        repository.delete(RepoConstants.UUID);

        // then
        assertThat(repository.findById(RepoConstants.UUID)).isEmpty();
    }
}
