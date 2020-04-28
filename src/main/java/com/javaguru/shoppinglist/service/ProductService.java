package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.service.validation.ValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ValidationService<Product> validationService;

    public ProductService(ProductRepository productRepository, ValidationService<Product> validationService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    public Product findById(Long id) {
        return productRepository.get(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found (id=" + id + ")"));
    }

    public List<Product> findAll() {
        return productRepository.getAll();
    }

    public Long save(Product product) {
        validationService.validate(product);
        return productRepository.save(product);
    }

    @Transactional
    public void update(Product productData) {
        validationService.validate(productData);
        Product product = findById(productData.getId());
        product.setName(productData.getName());
        product.setCategory(productData.getCategory());
        product.setPrice(productData.getPrice());
        product.setDiscount(productData.getDiscount());
        product.setDescription(productData.getDescription());
    }

    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
