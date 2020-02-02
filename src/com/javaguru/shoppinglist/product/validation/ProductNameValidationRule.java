package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;

public class ProductNameValidationRule implements ProductValidationRule {
    @Override
    public void validate(Product product) {
        checkNotNull(product);

        String name = product.getName();
        if (name == null) {
            throw new ProductValidationException("Product name can't be empty.");
        }

        if (name.length() < 3 || name.length() > 32) {
            throw new ProductValidationException("Product name can't be less than 3 and more than 32 characters.");
        }
    }
}
