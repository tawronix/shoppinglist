package com.javaguru.shoppinglist.console.menu;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;
import com.javaguru.shoppinglist.product.validation.ProductValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class EditProductMenu extends Menu {
    private final ProductService productService;

    private Product product;

    public EditProductMenu(ProductService productService) {
        this.productService = productService;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    protected String getTitle() {
        return "EDIT PRODUCT";
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Name", this::editName));
        menuItems.add(new MenuItem("Category", this::editCategory));
        menuItems.add(new MenuItem("Price", this::editPrice));
        menuItems.add(new MenuItem("Discount", this::editDiscount));
        menuItems.add(new MenuItem("Description", this::editDescription));
        menuItems.add(new MenuItem("Back", this::deactivate));
        return menuItems;
    }

    private void editName() {
        String name = userInput.getString("Enter new name");
        product.setName(name);
        updateProduct();
    }

    private void editCategory() {
        String category = userInput.getString("Enter new category");
        product.setCategory(category);
        updateProduct();
    }

    private void editPrice() {
        BigDecimal price = userInput.getBigDecimal("Enter new price");
        product.setPrice(price);
        updateProduct();
    }

    private void editDiscount() {
        BigDecimal discount = userInput.getBigDecimal("Enter new discount");
        product.setDiscount(discount);
        updateProduct();
    }

    private void editDescription() {
        String description = userInput.getString("Enter new description (optional)");
        product.setDescription(description);
        updateProduct();
    }

    private void updateProduct() {
        try {
            productService.updateProduct(product);
        } catch (ProductValidationException e) {
            System.out.println(e.getMessage());
            product = productService.findProductById(product.getId()).orElseThrow();
        }
        System.out.println(product);
    }
}
