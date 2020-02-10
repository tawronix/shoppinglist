package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;

import java.util.Optional;

public interface ProductRepository {
    Product insert(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    boolean update(Product product);

    boolean delete(Long id);
}
