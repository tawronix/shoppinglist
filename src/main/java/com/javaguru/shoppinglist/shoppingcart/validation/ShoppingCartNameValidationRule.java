package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartNameValidationRule implements ShoppingCartValidationRule {
    @Override
    public void validate(ShoppingCart shoppingCart) {
        checkNotNull(shoppingCart);

        String name = shoppingCart.getName();
        if (name == null || name.isEmpty()) {
            throw new ShoppingCartValidationException("Shopping cart name can't be empty.");
        }
    }
}
