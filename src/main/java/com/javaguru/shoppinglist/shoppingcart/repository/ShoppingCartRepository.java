package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository {
    ShoppingCart insert(ShoppingCart shoppingCart);

    Optional<ShoppingCart> findById(Long id);

    void delete(Long id);
}
