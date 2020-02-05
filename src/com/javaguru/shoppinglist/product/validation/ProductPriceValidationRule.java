package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;

import java.math.BigDecimal;

public class ProductPriceValidationRule implements ProductValidationRule {
    @Override
    public void validate(Product product) {
        checkNotNull(product);

        BigDecimal price = product.getPrice();
        if (price == null) {
            throw new ProductValidationException("Product price can't be empty.");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException("Product price must be greater than zero.");
        }
    }
}
