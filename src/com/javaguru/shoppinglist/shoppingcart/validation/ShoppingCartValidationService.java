package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartValidationService {
    private List<ShoppingCartValidationRule> rules = new ArrayList<>();

    public ShoppingCartValidationService() {
        rules.add(new ShoppingCartNameValidationRule());
        rules.add(new ShoppingCartProductListValidationRule());
    }

    public void validate(ShoppingCart shoppingCart) {
        rules.forEach(rule -> rule.validate(shoppingCart));
    }
}
