package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("in-memory")
public class InMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();
    private Long idSequence = 1L;

    @Override
    public Optional<Product> get(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Optional<Product> getByName(String name) {
        return products.values().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Long save(Product product) {
        product.setId(idSequence++);
        products.put(product.getId(), product);
        return product.getId();
    }

    @Override
    public void delete(Product product) {
        products.remove(product.getId());
    }
}
