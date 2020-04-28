package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.domain.ShoppingCart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShoppingCartItemsValidationRule implements ValidationRule<ShoppingCart> {
    @Override
    public void validate(ShoppingCart shoppingCart) {
        shoppingCart.getItems().forEach(shoppingCartItem -> {
            Product product = shoppingCartItem.getProduct();
            if (product == null || product.getId() == null) {
                throw new ValidationException("ShoppingCart product can't be empty.");
            }

            BigDecimal quantity = shoppingCartItem.getQuantity();
            if (quantity == null) {
                throw new ValidationException("ShoppingCart product quantity can't be empty.");
            }

            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException("ShoppingCart product quantity must be greater then zero.");
            }
        });
    }
}
