package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartProductListValidationRuleTest {
    private ShoppingCartProductListValidationRule victim = new ShoppingCartProductListValidationRule();

    private ShoppingCart shoppingCart;

    @Test
    public void shouldThrowShoppingCartValidationException() {
        shoppingCart = null;
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Shopping cart must be not null.");

        shoppingCart = createShoppingCart(null, null);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Product can't be null.");

        shoppingCart = createShoppingCart(new Product(), null);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Quantity can't be null.");

        shoppingCart = createShoppingCart(new Product(), BigDecimal.ZERO);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Quantity must be greater than zero.");

        shoppingCart = createShoppingCart(new Product(), new BigDecimal("-0.1"));
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Quantity must be greater than zero.");
    }

    @Test
    public void shouldPassValidationSuccessfully() {
        shoppingCart = createShoppingCart(new Product(), BigDecimal.ONE);
        victim.validate(shoppingCart);
    }

    private ShoppingCart createShoppingCart(Product product, BigDecimal quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(product, quantity);
        return shoppingCart;
    }
}
