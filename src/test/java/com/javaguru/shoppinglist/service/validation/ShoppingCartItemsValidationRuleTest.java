package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.domain.ShoppingCartItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ShoppingCartItemsValidationRuleTest {
    private ShoppingCartItemsValidationRule victim = new ShoppingCartItemsValidationRule();
    private ShoppingCart shoppingCart;

    @Test
    public void shouldFailValidation() {
        shoppingCart = createTestShoppingCart();
        shoppingCart.getItems().get(0).setProduct(null);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart product can't be empty.");

        shoppingCart = createTestShoppingCart();
        shoppingCart.getItems().get(1).getProduct().setId(null);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart product can't be empty.");

        shoppingCart = createTestShoppingCart();
        shoppingCart.getItems().get(0).setQuantity(null);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart product quantity can't be empty.");

        shoppingCart = createTestShoppingCart();
        shoppingCart.getItems().get(1).setQuantity(new BigDecimal("-1"));
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart product quantity must be greater then zero.");
    }

    @Test
    public void shouldPassValidation() {
        ShoppingCart shoppingCart = createTestShoppingCart();
        victim.validate(shoppingCart);
    }

    private ShoppingCart createTestShoppingCart() {
        Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        ShoppingCartItem item1 = new ShoppingCartItem();
        item1.setProduct(product1);
        item1.setQuantity(new BigDecimal("3"));

        ShoppingCartItem item2 = new ShoppingCartItem();
        item2.setProduct(product2);
        item2.setQuantity(new BigDecimal("5"));

        List<ShoppingCartItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(999L);
        shoppingCart.setName("TEST_NAME");
        shoppingCart.setItems(items);

        return shoppingCart;
    }
}