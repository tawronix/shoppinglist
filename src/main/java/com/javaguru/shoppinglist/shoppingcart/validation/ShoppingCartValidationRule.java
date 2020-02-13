package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;

public interface ShoppingCartValidationRule {
    void validate(ShoppingCart shoppingCart);

    default void checkNotNull(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            throw new ShoppingCartValidationException("Shopping cart must be not null.");
        }
    }
}
