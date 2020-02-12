package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;

public interface ShoppingCartRepository {
    ShoppingCart insert(ShoppingCart shoppingCart);

    ShoppingCart findById(Long id);

    boolean delete(Long id);
}
