package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;

import java.util.HashSet;
import java.util.Set;

public class ProductValidationService {
    private final Set<ProductValidationRule> validationRules = new HashSet<>();

    public ProductValidationService(ProductService productService) {
        validationRules.add(new ProductNameValidationRule(productService));
        validationRules.add(new ProductPriceValidationRule());
        validationRules.add(new ProductDiscountValidationRule());
    }

    public void validate(Product product) {
        validationRules.forEach(rule -> rule.validate(product));
    }
}
