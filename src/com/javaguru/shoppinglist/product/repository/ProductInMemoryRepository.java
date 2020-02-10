package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductInMemoryRepository implements ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();
    private Long productIdSequence = 1L;

    @Override
    public Product insert(Product product) {
        product.setId(productIdSequence++);
        products.put(product.getId(), createCopy(product));
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(createCopy(products.get(id)));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return products.values().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(this::createCopy);
    }

    @Override
    public boolean update(Product product) {
        Product storedProduct = products.get(product.getId());
        storedProduct.setName(product.getName());
        storedProduct.setCategory(product.getCategory());
        storedProduct.setPrice(product.getPrice());
        storedProduct.setDiscount(product.getDiscount());
        storedProduct.setDescription(product.getDescription());
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Product deletedProduct = products.remove(id);
        return deletedProduct != null;
    }

    private Product createCopy(Product original) {
        if (original == null) return null;
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
