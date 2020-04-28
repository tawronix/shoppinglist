package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductPriceValidationRule implements ValidationRule<Product> {
    @Override
    public void validate(Product product) {
        BigDecimal price = product.getPrice();
        if (price == null) {
            throw new ValidationException("Product price can't be empty.");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Product price must be greater than zero.");
        }
    }
}
