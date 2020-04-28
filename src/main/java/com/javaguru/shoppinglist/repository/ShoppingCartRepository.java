package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.domain.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository {
    Optional<ShoppingCart> get(Long id);

    Optional<ShoppingCart> getByName(String name);

    List<ShoppingCart> getAll();

    Long save(ShoppingCart shoppingCart);

    void delete(ShoppingCart shoppingCart);
}
