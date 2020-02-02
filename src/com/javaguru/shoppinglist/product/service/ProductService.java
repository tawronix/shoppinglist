package com.javaguru.shoppinglist.product.service;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.repository.ProductInMemoryRepository;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import com.javaguru.shoppinglist.product.validation.ProductValidationService;

public class ProductService {
    private final ProductRepository productRepository = new ProductInMemoryRepository();
    private final ProductValidationService validationService = new ProductValidationService();

    public long createProduct(Product product) {
        validationService.validate(product);
        Product createdProduct = productRepository.insert(product);
        return createdProduct.getId();
    }

    public Product findProductById(long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Product product) {
        validationService.validate(product);
        return productRepository.update(product);
    }

    public long deleteProduct(long id) {
        return productRepository.delete(id);
    }
}
