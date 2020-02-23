package com.javaguru.shoppinglist.product.service;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import com.javaguru.shoppinglist.product.validation.ProductValidationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidationService validationService;

    public ProductService(ProductRepository productRepository, ProductValidationService validationService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
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

    public void updateProduct(Product product) {
        validationService.validate(product);
        productRepository.update(product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}
