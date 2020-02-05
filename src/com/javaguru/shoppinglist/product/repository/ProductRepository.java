package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;

public interface ProductRepository {
    Product insert(Product product);

    Product findById(Long id);

    Product findByName(String name);

    boolean update(Product product);

    boolean delete(Long id);
}
