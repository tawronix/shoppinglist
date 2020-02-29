package com.javaguru.shoppinglist.shoppingcart.service;

import com.javaguru.shoppinglist.shoppingcart.ProductListItem;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ShoppingCartService {
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartValidationService validationService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ShoppingCartValidationService validationService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.validationService = validationService;
    }

    public Long createShoppingCart(ShoppingCart shoppingCart) {
        validationService.validate(shoppingCart);
        ShoppingCart createdShoppingCart = shoppingCartRepository.insert(shoppingCart);
        return createdShoppingCart.getId();
    }

    public Optional<ShoppingCart> findById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public boolean deleteShoppingCart(Long id) {
        return shoppingCartRepository.delete(id);
    }

    public BigDecimal getShoppingCartTotalCost(ShoppingCart shoppingCart) {
        BigDecimal totalCost = new BigDecimal("0.00");
        for (ProductListItem item : shoppingCart.getProductList()) {
            BigDecimal p = item.getProduct().getPrice();
            BigDecimal d = item.getProduct().getDiscount();
            BigDecimal priceWithDiscount = p.multiply(BigDecimal.ONE.subtract(d.divide(ONE_HUNDRED, 5, RoundingMode.HALF_UP)));
            totalCost = totalCost.add(priceWithDiscount.multiply(item.getQuantity()));
        }
        return totalCost.setScale(2, RoundingMode.HALF_EVEN);
    }
}
