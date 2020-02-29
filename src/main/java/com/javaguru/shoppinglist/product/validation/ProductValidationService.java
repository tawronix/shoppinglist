package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;

import java.util.List;

public class ProductValidationService {
    private final List<ProductValidationRule> validationRules;

    public ProductValidationService(List<ProductValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    public void validate(Product product) {
        validationRules.forEach(rule -> rule.validate(product));
    }
}
