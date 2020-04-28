package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.domain.ShoppingCartItem;
import com.javaguru.shoppinglist.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.service.validation.ValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ValidationService<ShoppingCart> validationService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ValidationService<ShoppingCart> validationService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.validationService = validationService;
    }

    public ShoppingCart findById(Long id) {
        return shoppingCartRepository.get(id)
                .orElseThrow(() -> new NoSuchElementException("ShoppingCart not found (id=" + id + ")"));
    }

    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.getAll();
    }

    public Long save(ShoppingCart shoppingCart) {
        validationService.validate(shoppingCart);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public void update(ShoppingCart shoppingCartData) {
        validationService.validate(shoppingCartData);

        ShoppingCart shoppingCart = findById(shoppingCartData.getId());
        shoppingCart.setName(shoppingCartData.getName());
        List<ShoppingCartItem> currentItems = shoppingCart.getItems();
        List<ShoppingCartItem> updatedItems = shoppingCartData.getItems();

        currentItems.removeIf(currentItem -> updatedItems.stream()
                .noneMatch(updatedItem -> currentItem.getId().equals(updatedItem.getId()))
        );

        currentItems.forEach(currentItem -> {
            ShoppingCartItem updatedItem = updatedItems.stream()
                    .filter(item -> currentItem.getId().equals(item.getId()))
                    .findAny().orElseThrow();
            currentItem.setProduct(updatedItem.getProduct());
            currentItem.setQuantity(updatedItem.getQuantity());
        });

        currentItems.addAll(updatedItems.stream()
                .filter(item -> item.getId() == null)
                .peek(item -> item.setShoppingCart(shoppingCart))
                .collect(Collectors.toList()));
    }

    public void delete(Long id) {
        ShoppingCart shoppingCart = findById(id);
        shoppingCartRepository.delete(shoppingCart);
    }
}
