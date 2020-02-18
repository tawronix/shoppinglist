package com.javaguru.shoppinglist.shoppingcart.service;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCartItem;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartInMemoryRepository;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShoppingCartService {
    public static final BigDecimal ONR_HUNDRED = new BigDecimal("100");

    private static final ShoppingCartService ourInstance = new ShoppingCartService();

    private final ShoppingCartRepository repository = new ShoppingCartInMemoryRepository();
    private final ShoppingCartValidationService validationService = new ShoppingCartValidationService();

    private ShoppingCartService() {
    }

    public static ShoppingCartService getInstance() {
        return ourInstance;
    }

    public Long createShoppingCart(ShoppingCart shoppingCart) {
        validationService.validate(shoppingCart);
        ShoppingCart createdShoppingCart = repository.insert(shoppingCart);
        return createdShoppingCart.getId();
    }

    public ShoppingCart findById(Long id) {
        return repository.findById(id);
    }

    public boolean deleteShoppingCart(Long id) {
        return repository.delete(id);
    }

    public BigDecimal getShoppingCartTotalCost(ShoppingCart shoppingCart) {
        BigDecimal totalCost = new BigDecimal("0.00");
        for (ShoppingCartItem item : shoppingCart.getProductList()) {
            BigDecimal p = item.getProduct().getPrice();
            BigDecimal d = item.getProduct().getDiscount();
            BigDecimal priceWithDiscount = p.multiply(BigDecimal.ONE.subtract(d.divide(ONR_HUNDRED, 5, RoundingMode.HALF_UP)));
            totalCost = totalCost.add(priceWithDiscount.multiply(item.getQuantity()));
        }
        return totalCost.setScale(2, RoundingMode.HALF_EVEN);
    }
}
