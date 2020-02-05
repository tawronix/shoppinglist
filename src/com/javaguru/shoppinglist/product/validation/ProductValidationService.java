package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;

import java.util.HashSet;
import java.util.Set;

public class ProductValidationService {
    private final Set<ProductValidationRule> validationRules = new HashSet<>();

    public ProductValidationService() {
        validationRules.add(new ProductNameValidationRule());
        validationRules.add(new ProductPriceValidationRule());
        validationRules.add(new ProductDiscountValidationRule());
    }

    public void validate(Product product) {
        validationRules.forEach(rule -> rule.validate(product));
    }
}
