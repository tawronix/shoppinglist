package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.console.ConsoleUI;
import com.javaguru.shoppinglist.console.MenuFactory;
import com.javaguru.shoppinglist.product.repository.ProductInMemoryRepository;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import com.javaguru.shoppinglist.product.service.ProductService;
import com.javaguru.shoppinglist.product.validation.*;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartInMemoryRepository;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.shoppingcart.service.ShoppingCartService;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartNameValidationRule;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartProductListValidationRule;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationRule;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationService;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListApplication {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductInMemoryRepository();

        List<ProductValidationRule> productValidationRules = new ArrayList<>();
        productValidationRules.add(new ProductNameValidationRule(productRepository));
        productValidationRules.add(new ProductPriceValidationRule());
        productValidationRules.add(new ProductDiscountValidationRule());

        ProductValidationService productValidationService = new ProductValidationService(productValidationRules);
        ProductService productService = new ProductService(productRepository, productValidationService);

        ShoppingCartRepository shoppingCartRepository = new ShoppingCartInMemoryRepository();

        List<ShoppingCartValidationRule> shoppingCartValidationRules = new ArrayList<>();
        shoppingCartValidationRules.add(new ShoppingCartNameValidationRule());
        shoppingCartValidationRules.add(new ShoppingCartProductListValidationRule());

        ShoppingCartValidationService shoppingCartValidationService = new ShoppingCartValidationService(shoppingCartValidationRules);
        ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartRepository, shoppingCartValidationService);

        MenuFactory menuFactory = new MenuFactory(productService, shoppingCartService);

        ConsoleUI consoleUI = new ConsoleUI(menuFactory.getMainMenu());
        consoleUI.start();
    }
}
