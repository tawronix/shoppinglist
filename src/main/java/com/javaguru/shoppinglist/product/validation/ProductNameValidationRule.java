package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductNameValidationRule implements ProductValidationRule {
    private final ProductRepository productRepository;

    public ProductNameValidationRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void validate(Product product) {
        checkNotNull(product);

        String name = product.getName();
        if (name == null) {
            throw new ProductValidationException("Product name can't be empty.");
        }

        if (name.length() < 3 || name.length() > 32) {
            throw new ProductValidationException("Product name can't be less than 3 and more than 32 characters.");
        }

        Optional<Product> foundProduct = productRepository.findByName(name);
        if (foundProduct.isPresent() && !foundProduct.get().getId().equals(product.getId())) {
            throw new ProductValidationException("Product with the same name already exists.");
        }
    }
}
