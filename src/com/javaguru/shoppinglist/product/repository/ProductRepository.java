package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;

public interface ProductRepository {
    Product insert(Product product);

    Product findById(long id);

    Product update(Product product);

    long delete(long id);
}
