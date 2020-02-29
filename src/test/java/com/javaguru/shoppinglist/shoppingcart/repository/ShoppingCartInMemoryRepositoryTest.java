package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartInMemoryRepositoryTest {
    private ShoppingCartInMemoryRepository victim = new ShoppingCartInMemoryRepository();

    @Test
    public void testRepository() {
        ShoppingCart shoppingCart = createShoppingCart();
        ShoppingCart insertResult = victim.insert(shoppingCart);
        assertThat(insertResult.getId()).isNotNull();

        Optional<ShoppingCart> findByIdResult = victim.findById(insertResult.getId());
        assertThat(findByIdResult.isPresent()).isTrue();
        assertThat(findByIdResult.get()).isEqualTo(insertResult);

        Optional<ShoppingCart> findByIdResult2 = victim.findById(-1L);
        assertThat(findByIdResult2.isEmpty()).isTrue();

        victim.delete(insertResult.getId());
        Optional<ShoppingCart> deletedShoppingCart = victim.findById(insertResult.getId());
        assertThat(deletedShoppingCart.isEmpty()).isTrue();
    }

    private ShoppingCart createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(9999L);
        shoppingCart.setName("SHOPPING_CART");
        shoppingCart.addProduct(new Product(), BigDecimal.ZERO);
        shoppingCart.addProduct(new Product(), BigDecimal.ZERO);
        return shoppingCart;
    }
}
