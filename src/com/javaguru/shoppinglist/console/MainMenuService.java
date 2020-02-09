package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;
import com.javaguru.shoppinglist.product.validation.ProductValidationException;

import java.math.BigDecimal;

public class MainMenuService {
    private final UserInput userInput = new UserInput();
    private final ProductService productService = ProductService.getInstance();
    private final Menu menu;

    public MainMenuService(Menu menu) {
        this.menu = menu;
    }

    public void createProduct() {
        String name = userInput.getString("Enter product name");
        String category = userInput.getString("Enter product category");
        BigDecimal price = userInput.getBigDecimal("Enter product price");
        BigDecimal discount = userInput.getBigDecimal("Enter product discount");
        String description = userInput.getString("Enter product description (optional)");

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setDescription(description);

        try {
            Long id = productService.createProduct(product);
            System.out.printf("New product created. { ID: %d }%n", id);
        } catch (ProductValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void findProductById() {
        Long id = userInput.getLong("Enter product id");
        Product product = productService.findProductById(id);
        System.out.println(product != null ? product : "Product not found.");
    }

    public void editProduct() {
        Long id = userInput.getLong("Enter product id");
        Product product = productService.findProductById(id);
        if (product != null) {
            System.out.println(product);
            Menu editMenu = UIFactory.getInstance().getEditProductMenu(product);
            do {
                editMenu.show();
            } while (editMenu.isActive());
        } else {
            System.out.println("Product not found.");
        }
    }

    public void deleteProduct() {
        Long id = userInput.getLong("Enter product id");
        boolean result = productService.deleteProduct(id);
        System.out.println(result ? String.format("Product deleted. { ID: %d }", id) : "Product not found.");
    }

    public void shoppingCart() {
        Menu shoppingCartMenu = UIFactory.getInstance().getShoppingCartMenu();
        do {
            shoppingCartMenu.show();
        } while (shoppingCartMenu.isActive());
    }

    public void exit() {
        menu.setActive(false);
    }
}
