package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartNameValidationRuleTest {
    private ShoppingCartNameValidationRule victim = new ShoppingCartNameValidationRule();

    private ShoppingCart shoppingCart;

    @Test
    public void shouldThrowShoppingCartValidationException() {
        shoppingCart = null;
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Shopping cart must be not null.");

        shoppingCart = createShoppingCart(null);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Shopping cart name can't be empty.");

        shoppingCart = createShoppingCart("");
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ShoppingCartValidationException.class)
                .hasMessage("Shopping cart name can't be empty.");
    }

    @Test
    public void shouldPassValidationSuccessfully() {
        shoppingCart = createShoppingCart("Valid name");
        victim.validate(shoppingCart);
    }

    private ShoppingCart createShoppingCart(String name) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setName(name);
        return shoppingCart;
    }
}