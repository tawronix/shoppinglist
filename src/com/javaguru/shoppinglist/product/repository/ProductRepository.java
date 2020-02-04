package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;

public interface ProductRepository {
    Product insert(Product product);

    Product findById(long id);

    Product findByName(String name);

    Product update(Product product);

    Product delete(long id);
}
