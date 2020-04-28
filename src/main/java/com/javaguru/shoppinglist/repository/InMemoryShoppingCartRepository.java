package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.domain.ShoppingCart;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("in-memory")
public class InMemoryShoppingCartRepository implements ShoppingCartRepository {
    private final Map<Long, ShoppingCart> shoppingCarts = new HashMap<>();
    private Long idSequence = 1L;

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Optional.ofNullable(shoppingCarts.get(id));
    }

    @Override
    public Optional<ShoppingCart> getByName(String name) {
        return shoppingCarts.values().stream()
                .filter(shoppingCart -> shoppingCart.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return new ArrayList<>(shoppingCarts.values());
    }

    @Override
    public Long save(ShoppingCart shoppingCart) {
        shoppingCart.setId(idSequence++);
        shoppingCarts.put(shoppingCart.getId(), shoppingCart);
        return shoppingCart.getId();
    }

    @Override
    public void delete(ShoppingCart shoppingCart) {
        shoppingCarts.remove(shoppingCart.getId());
    }
}
