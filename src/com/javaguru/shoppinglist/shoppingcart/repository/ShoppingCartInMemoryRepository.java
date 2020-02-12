package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartInMemoryRepository implements ShoppingCartRepository {
    private final Map<Long, ShoppingCart> shoppingCarts = new HashMap<>();
    private Long shoppingCartIdSequence = 1L;

    @Override
    public ShoppingCart insert(ShoppingCart shoppingCart) {
        shoppingCart.setId(shoppingCartIdSequence++);
        shoppingCarts.put(shoppingCart.getId(), shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCart findById(Long id) {
        return shoppingCarts.get(id);
    }

    @Override
    public boolean delete(Long id) {
        ShoppingCart deletedShoppingCart = shoppingCarts.remove(id);
        return deletedShoppingCart != null;
    }
}
