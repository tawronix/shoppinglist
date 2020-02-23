package com.javaguru.shoppinglist.console.menu;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;
import com.javaguru.shoppinglist.product.validation.ProductValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ProductsMenu extends Menu {
    private static final String ENTER_PRODUCT_ID = "Enter product id";
    private static final String PRODUCT_NOT_FOUND = "Product not found.";

    private final ProductService productService;
    private final EditProductMenu editProductMenu;

    public ProductsMenu(ProductService productService, EditProductMenu editProductMenu) {
        this.productService = productService;
        this.editProductMenu = editProductMenu;
    }

    @Override
    protected String getTitle() {
        return "PRODUCTS";
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Create", this::createProduct));
        menuItems.add(new MenuItem("Find by id", this::findProductById));
        menuItems.add(new MenuItem("Find by name", this::findProductByName));
        menuItems.add(new MenuItem("Edit", this::editProduct));
        menuItems.add(new MenuItem("Delete", this::deleteProduct));
        menuItems.add(new MenuItem("Back", this::deactivate));
        return menuItems;
    }

    private void createProduct() {
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

    private void findProductById() {
        Long id = userInput.getLong(ENTER_PRODUCT_ID);
        Optional<Product> product = productService.findProductById(id);
        System.out.println(product.isPresent() ? product.get() : PRODUCT_NOT_FOUND);
    }

    private void findProductByName() {
        String name = userInput.getString("Enter product name");
        Optional<Product> product = productService.findProductByName(name);
        System.out.println(product.isPresent() ? product.get() : PRODUCT_NOT_FOUND);
    }

    private void editProduct() {
        Long id = userInput.getLong(ENTER_PRODUCT_ID);
        try {
            Product product = productService.findProductById(id).orElseThrow();
            System.out.println(product);
            editProductMenu.setProduct(product);
            editProductMenu.show();
        } catch (NoSuchElementException e) {
            System.out.println(PRODUCT_NOT_FOUND);
        }
    }

    private void deleteProduct() {
        Long id = userInput.getLong(ENTER_PRODUCT_ID);
        try {
            productService.findProductById(id).orElseThrow();
            productService.deleteProduct(id);
            System.out.printf("Product deleted. { ID: %d }%n", id);
        } catch (NoSuchElementException e) {
            System.out.println(PRODUCT_NOT_FOUND);
        }
    }
}
