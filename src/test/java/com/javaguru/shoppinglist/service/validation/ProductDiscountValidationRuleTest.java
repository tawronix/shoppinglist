package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static com.javaguru.shoppinglist.service.validation.ProductDiscountValidationRule.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductDiscountValidationRuleTest {
    private ProductDiscountValidationRule victim = new ProductDiscountValidationRule();

    @Test
    public void shouldFailValidation() {
        Product product = new Product();

        product.setDiscount(null);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product discount can't be empty.");

        product.setDiscount(MIN_DISCOUNT.subtract(new BigDecimal("0.1")));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage(String.format("Product discount can't be less than %.1f%% or greater than %.1f%%.", MIN_DISCOUNT, MAX_DISCOUNT));

        product.setDiscount(MAX_DISCOUNT.add(new BigDecimal("0.1")));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage(String.format("Product discount can't be less than %.1f%% or greater than %.1f%%.", MIN_DISCOUNT, MAX_DISCOUNT));

        product.setPrice(MIN_PRICE_FOR_DISCOUNT.subtract(new BigDecimal("0.01")));
        product.setDiscount(new BigDecimal("10"));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage(String.format("Discount is not applicable for products with price less than %.2f", MIN_PRICE_FOR_DISCOUNT));
    }

    @Test
    public void shouldPassValidation() {
        Product product = new Product();
        product.setPrice(MIN_PRICE_FOR_DISCOUNT);

        product.setDiscount(MIN_DISCOUNT);
        victim.validate(product);

        product.setDiscount(MAX_DISCOUNT);
        victim.validate(product);
    }
}