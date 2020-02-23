package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartValidationService {
    private final List<ShoppingCartValidationRule> validationRules;

    public ShoppingCartValidationService(List<ShoppingCartValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    public void validate(ShoppingCart shoppingCart) {
        validationRules.forEach(rule -> rule.validate(shoppingCart));
    }
}
