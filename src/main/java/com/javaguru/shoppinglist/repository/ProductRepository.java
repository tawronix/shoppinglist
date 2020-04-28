package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> get(Long id);

    Optional<Product> getByName(String name);

    List<Product> getAll();

    Long save(Product product);

    void delete(Product product);
}
