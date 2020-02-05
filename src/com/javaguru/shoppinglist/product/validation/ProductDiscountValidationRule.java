package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;

import java.math.BigDecimal;

public class ProductDiscountValidationRule implements ProductValidationRule {
    private static final BigDecimal MIN_DISCOUNT = BigDecimal.ZERO;
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("100.0");

    @Override
    public void validate(Product product) {
        checkNotNull(product);

        BigDecimal discount = product.getDiscount();
        if (discount == null) {
            throw new ProductValidationException("Product discount can't be empty.");
        }

        if (discount.compareTo(MIN_DISCOUNT) < 0 || discount.compareTo(MAX_DISCOUNT) > 0) {
            throw new ProductValidationException(String.format("Product discount can't be negative or greater than %.1f%%.", MAX_DISCOUNT));
        }
    }
}
