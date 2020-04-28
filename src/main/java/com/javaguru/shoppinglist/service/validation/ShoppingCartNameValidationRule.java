package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.repository.ShoppingCartRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShoppingCartNameValidationRule implements ValidationRule<ShoppingCart> {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartNameValidationRule(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void validate(ShoppingCart shoppingCart) {
        String name = shoppingCart.getName();
        if (name == null || name.isEmpty()) {
            throw new ValidationException("ShoppingCart name can't be empty.");
        }

        if (name.length() < 3 || name.length() > 32) {
            throw new ValidationException("ShoppingCart name can't be less than 3 and more than 32 characters.");
        }

        Optional<ShoppingCart> foundShoppingCart = shoppingCartRepository.getByName(name);
        if (foundShoppingCart.isPresent() && !foundShoppingCart.get().getId().equals(shoppingCart.getId())) {
            throw new ValidationException("ShoppingCart with the same name already exists.");
        }
    }
}
