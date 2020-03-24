package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("in-memory")
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
    public Optional<ShoppingCart> findById(Long id) {
        return Optional.ofNullable(shoppingCarts.get(id));
    }

    @Override
    public void delete(Long id) {
        shoppingCarts.remove(id);
    }
}
