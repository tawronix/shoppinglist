package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductNameValidationRule implements ValidationRule<Product> {
    private final ProductRepository productRepository;

    public ProductNameValidationRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void validate(Product product) {
        String name = product.getName();
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Product name can't be empty.");
        }

        if (name.length() < 3 || name.length() > 32) {
            throw new ValidationException("Product name can't be less than 3 and more than 32 characters.");
        }

        Optional<Product> foundProduct = productRepository.getByName(name);
        if (foundProduct.isPresent() && !foundProduct.get().getId().equals(product.getId())) {
            throw new ValidationException("Product with the same name already exists.");
        }
    }
}
