package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductInMemoryRepositoryTest {
    private ProductInMemoryRepository victim = new ProductInMemoryRepository();

    @Test
    public void testRepository() {
        Product testProduct = createProduct();
        Product insertResult = victim.insert(testProduct);
        assertThat(insertResult.getId()).isNotNull();

        Optional<Product> findByIdResult = victim.findById(insertResult.getId());
        assertThat(findByIdResult.isPresent()).isTrue();
        assertThat(findByIdResult.get()).isEqualTo(insertResult);

        Optional<Product> findByIdResult2 = victim.findById(-1L);
        assertThat(findByIdResult2.isEmpty()).isTrue();

        Optional<Product> findByNameResult = victim.findByName(insertResult.getName().toLowerCase());
        assertThat(findByNameResult.isPresent()).isTrue();
        assertThat(findByNameResult.get()).isEqualTo(insertResult);

        Optional<Product> findByNameResult2 = victim.findByName("NOT_EXISTING_NAME");
        assertThat(findByNameResult2.isEmpty()).isTrue();

        changeProduct(insertResult);
        victim.update(insertResult);
        Optional<Product> updatedProduct = victim.findByName(insertResult.getName());
        assertThat(updatedProduct.isPresent()).isTrue();
        assertThat(updatedProduct.get()).isEqualTo(insertResult);

        victim.delete(insertResult.getId());
        Optional<Product> deletedProduct = victim.findById(insertResult.getId());
        assertThat(deletedProduct.isEmpty()).isTrue();
    }

    private Product createProduct() {
        Product product = new Product();
        product.setName("TEST_PRODUCT");
        product.setCategory("CATEGORY");
        product.setPrice(new BigDecimal("25.00"));
        product.setDiscount(new BigDecimal("3.5"));
        product.setDescription("DESCRIPTION");
        return product;
    }

    private void changeProduct(Product product) {
        product.setName(product.getName() + "*");
        product.setCategory(product.getCategory() + "*");
        product.setPrice(product.getPrice().add(new BigDecimal("0.01")));
        product.setDiscount(product.getDiscount().add(new BigDecimal("0.5")));
        product.setDescription(product.getDescription() + "*");
    }
}
