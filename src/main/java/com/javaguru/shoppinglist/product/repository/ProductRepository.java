package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository {
    Product insert(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    void update(Product product);

    void delete(Long id);
}
