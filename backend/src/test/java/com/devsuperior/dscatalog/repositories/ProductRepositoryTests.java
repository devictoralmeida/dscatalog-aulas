package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;

    private long countTotalProducts;

    @BeforeEach
    void setUp() {
        countTotalProducts = 25L;
    }

    @Test
    void delete_should_delete_object_when_id_exists() {
        // Act
        repository.deleteById(1L);
        Optional<Product> result = repository.findById(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void save_should_persist_with_auto_increment_when_id_is_null() {
        // Arrange
        Product product = Factory.createProduct();
        product.setId(null);

        // Act
        product = repository.save(product);

        // Assert
        assertNotNull(product.getId());
        assertEquals(countTotalProducts + 1, product.getId());
    }
}
