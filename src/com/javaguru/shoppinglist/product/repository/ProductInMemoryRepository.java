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
        products.put(product.getId(), createCopy(product));
        return product;
    }

    @Override
    public Product findById(long id) {
        return createCopy(products.get(id));
    }

    @Override
    public Product findByName(String name) {
        Product[] productList = products.values().stream().filter(product -> product.getName().equalsIgnoreCase(name)).toArray(Product[]::new);
        return productList.length > 0 ? createCopy(productList[0]) : null;
    }

    @Override
    public Product update(Product product) {
        Product storedProduct = products.get(product.getId());
        storedProduct.setName(product.getName());
        storedProduct.setCategory(product.getCategory());
        storedProduct.setPrice(product.getPrice());
        storedProduct.setDiscount(product.getDiscount());
        storedProduct.setDescription(product.getDescription());
        return product;
    }

    @Override
    public Product delete(long id) {
        return products.remove(id);
    }

    private Product createCopy(Product original) {
        Product productCopy = new Product();
        productCopy.setId(original.getId());
        productCopy.setName(original.getName());
        productCopy.setCategory(original.getCategory());
        productCopy.setPrice(original.getPrice());
        productCopy.setDiscount(original.getDiscount());
        productCopy.setDescription(original.getDescription());
        return productCopy;
    }
}
