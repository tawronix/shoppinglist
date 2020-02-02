package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductInMemoryRepository implements ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();
    private long productIdSequence = 1L;

    @Override
    public Product insert(Product product) {
        product.setId(productIdSequence++);
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Product findById(long id) {
        return products.get(id);
    }

    @Override
    public Product update(Product product) {
        products.put(product.getId(), product);
        return products.get(product.getId());
    }

    @Override
    public long delete(long id) {
        Product product = products.remove(id);
        return product != null ? product.getId() : -1;
    }
}
