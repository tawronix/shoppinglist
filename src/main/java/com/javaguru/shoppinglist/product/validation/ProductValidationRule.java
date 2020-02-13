package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;

public interface ProductValidationRule {
    void validate(Product product);

    default void checkNotNull(Product product) {
        if (product == null) {
            throw new ProductValidationException("Product must be not null.");
        }
    }
}
