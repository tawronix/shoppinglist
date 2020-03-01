package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShoppingCartProductListValidationRule implements ShoppingCartValidationRule {
    @Override
    public void validate(ShoppingCart shoppingCart) {
        checkNotNull(shoppingCart);

        shoppingCart.getProductList().forEach(productListItem -> {
            Product product = productListItem.getProduct();
            if (product == null) {
                throw new ShoppingCartValidationException("Product can't be null.");
            }

            BigDecimal quantity = productListItem.getQuantity();
            if (quantity == null) {
                throw new ShoppingCartValidationException("Quantity can't be null.");
            }

            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ShoppingCartValidationException("Quantity must be greater than zero.");
            }
        });
    }
}
