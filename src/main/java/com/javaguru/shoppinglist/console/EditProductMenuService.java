package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;
import com.javaguru.shoppinglist.product.validation.ProductValidationException;

import java.math.BigDecimal;

public class EditProductMenuService {
    private final ProductService productService;
    private final Menu menu;
    private final UserInput userInput = new UserInput();

    private Product product;

    public EditProductMenuService(ProductService productService, Menu menu) {
        this.productService = productService;
        this.menu = menu;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void editName() {
        String name = userInput.getString("Enter new name");
        product.setName(name);
    }

    public void editCategory() {
        String category = userInput.getString("Enter new category");
        product.setCategory(category);
    }

    public void editPrice() {
        BigDecimal price = userInput.getBigDecimal("Enter new price");
        product.setPrice(price);
    }

    public void editDiscount() {
        BigDecimal discount = userInput.getBigDecimal("Enter new discount");
        product.setDiscount(discount);
    }

    public void editDescription() {
        String description = userInput.getString("Enter new description (optional)");
        product.setDescription(description);
    }

    public void save() {
        try {
            boolean result = productService.updateProduct(product);
            System.out.println(result ? String.format("Product updated. { ID: %d }", product.getId()) : "Error. Product has not been updated.");
            menu.setActive(false);
        } catch (ProductValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancel() {
        menu.setActive(false);
    }
}
