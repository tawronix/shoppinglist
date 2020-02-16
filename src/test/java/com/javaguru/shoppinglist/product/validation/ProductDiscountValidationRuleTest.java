package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static com.javaguru.shoppinglist.product.validation.ProductDiscountValidationRule.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductDiscountValidationRuleTest {
    private ProductDiscountValidationRule victim = new ProductDiscountValidationRule();
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
                .hasMessage("Product discount can't be empty.");

        product = createProduct(MIN_DISCOUNT.subtract(new BigDecimal("0.1")));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage(String.format("Product discount can't be less than %.1f%% or greater than %.1f%%.", MIN_DISCOUNT, MAX_DISCOUNT));

        product = createProduct(MAX_DISCOUNT.add(new BigDecimal("0.1")));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage(String.format("Product discount can't be less than %.1f%% or greater than %.1f%%.", MIN_DISCOUNT, MAX_DISCOUNT));

        product = createProduct(new BigDecimal("10"), MIN_PRICE_FOR_DISCOUNT.subtract(new BigDecimal("0.01")));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage(String.format("Discount is not applicable for products with price less than %.2f", MIN_PRICE_FOR_DISCOUNT));
    }

    @Test
    public void shouldPassValidationSuccessfully() {
        product = createProduct(MIN_DISCOUNT);
        victim.validate(product);

        product = createProduct(MAX_DISCOUNT);
        victim.validate(product);
    }

    private Product createProduct(BigDecimal discount) {
        return createProduct(discount, MIN_PRICE_FOR_DISCOUNT);
    }

    private Product createProduct(BigDecimal discount, BigDecimal price) {
        Product product = new Product();
        product.setDiscount(discount);
        product.setPrice(price);
        return product;
    }
}