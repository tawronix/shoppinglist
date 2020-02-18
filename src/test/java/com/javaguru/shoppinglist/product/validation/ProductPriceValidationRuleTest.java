package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductPriceValidationRuleTest {
    private ProductPriceValidationRule victim = new ProductPriceValidationRule();
    private Product product;

    @Test
    public void shouldThrowProductValidationException() {
        product = null;
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product must be not null.");

        product = createProduct(null);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product price can't be empty.");

        product = createProduct(BigDecimal.ZERO);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product price must be greater than zero.");

        product = createProduct(new BigDecimal("-0.01"));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product price must be greater than zero.");
    }

    @Test
    public void shouldPassValidationSuccessfully() {
        product = createProduct(new BigDecimal("0.01"));
        victim.validate(product);
    }

    private Product createProduct(BigDecimal price) {
        Product product = new Product();
        product.setPrice(price);
        return product;
    }
}