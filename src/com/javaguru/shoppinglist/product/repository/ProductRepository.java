package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;

public interface ProductRepository {
    Product insert(Product product);

    Product findById(Long id);

    Product update(Product product);

    Long delete(Long id);
}
