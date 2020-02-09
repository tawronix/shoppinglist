package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;

import java.math.BigDecimal;

public class ShoppingCartProductListValidationRule implements ShoppingCartValidationRule {
    @Override
    public void validate(ShoppingCart shoppingCart) {
        checkNotNull(shoppingCart);

        shoppingCart.getProductList().forEach(productLine -> {
            Product product = productLine.getProduct();
            if (product == null) {
                throw new ShoppingCartValidationException("Product can't be null.");
            }

            BigDecimal quantity = productLine.getQuantity();
            if (quantity == null) {
                throw new ShoppingCartValidationException("Quantity can't be null.");
            }

            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ShoppingCartValidationException("Quantity must be greater than zero.");
            }
        });
    }
}
