package com.javaguru.shoppinglist.product.service;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.repository.ProductInMemoryRepository;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import com.javaguru.shoppinglist.product.validation.ProductValidationService;

import java.util.Optional;

public class ProductService {
    private static final ProductService ourInstance = new ProductService();

    private ProductRepository productRepository = new ProductInMemoryRepository();
    private ProductValidationService validationService = new ProductValidationService(this);

    private ProductService() {
    }

    public static ProductService getInstance() {
        return ourInstance;
    }

    public Long createProduct(Product product) {
        validationService.validate(product);
        Product createdProduct = productRepository.insert(product);
        return createdProduct.getId();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    public boolean updateProduct(Product product) {
        validationService.validate(product);
        return productRepository.update(product);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.delete(id);
    }
}
