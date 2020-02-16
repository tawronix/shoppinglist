package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;

import java.util.List;

public class ShoppingCartValidationService {
    private final List<ShoppingCartValidationRule> validationRules;

    public ShoppingCartValidationService(List<ShoppingCartValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    public void validate(ShoppingCart shoppingCart) {
        validationRules.forEach(rule -> rule.validate(shoppingCart));
    }
}
