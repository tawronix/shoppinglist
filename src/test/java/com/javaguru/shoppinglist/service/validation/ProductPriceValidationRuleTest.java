package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductPriceValidationRuleTest {
    private ProductPriceValidationRule victim = new ProductPriceValidationRule();

    @Test
    public void shouldFailValidation() {
        Product product = new Product();

        product.setPrice(null);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product price can't be empty.");

        product.setPrice(BigDecimal.ZERO);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product price must be greater than zero.");

        product.setPrice(new BigDecimal("-0.01"));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product price must be greater than zero.");
    }

    @Test
    public void shouldPassValidation() {
        Product product = new Product();
        product.setPrice(new BigDecimal("0.01"));
        victim.validate(product);
    }
}