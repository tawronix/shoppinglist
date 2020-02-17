package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartRepository {
    ShoppingCart insert(ShoppingCart shoppingCart);

    Optional<ShoppingCart> findById(Long id);

    boolean delete(Long id);
}
