package com.javaguru.shoppinglist.console.menu;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;
import com.javaguru.shoppinglist.shoppingcart.ProductListItem;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import com.javaguru.shoppinglist.shoppingcart.service.ShoppingCartService;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ShoppingCartsMenu extends Menu {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    public ShoppingCartsMenu(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @Override
    protected String getTitle() {
        return "SHOPPING CARTS";
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Create", this::createShoppingCart));
        menuItems.add(new MenuItem("Find by id", this::findShoppingCartById));
        menuItems.add(new MenuItem("Delete", this::deleteShoppingCart));
        menuItems.add(new MenuItem("Back", this::deactivate));
        return menuItems;
    }

    private void createShoppingCart() {
        String name = userInput.getString("Enter shopping cart name");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setName(name);

        while (true) {
            Long id = userInput.getLong("Enter product id (0 - to finish)");
            if (id == 0) break;
            try {
                Product product = productService.findProductById(id).orElseThrow();
                System.out.println(product);
                BigDecimal quantity = userInput.getBigDecimal("Enter quantity");
                shoppingCart.addProduct(product, quantity);
            } catch (NoSuchElementException e) {
                System.out.println("Product not found.");
            }
        }

        try {
            Long id = shoppingCartService.createShoppingCart(shoppingCart);
            System.out.printf("New shopping cart created. { ID: %d }%n", id);
        } catch (ShoppingCartValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findShoppingCartById() {
        Long id = userInput.getLong("Enter shopping cart id");
        try {
            ShoppingCart shoppingCart = shoppingCartService.findShoppingCartById(id).orElseThrow();
            List<ProductListItem> productList = shoppingCart.getProductList();
            System.out.println("Shopping cart: " + shoppingCart.getName());
            productList.forEach(item -> {
                Product product = item.getProduct();
                System.out.printf("\t%d. %s | Price: %.2f | Discount: %.1f%% | Quantity: %.3f%n",
                        productList.indexOf(item) + 1, product.getName(), product.getPrice(), product.getDiscount(), item.getQuantity());
            });
            System.out.println("\tTotal cost: " + shoppingCartService.getShoppingCartTotalCost(shoppingCart));
        } catch (NoSuchElementException e) {
            System.out.println("Shopping cart not found.");
        }
    }

    private void deleteShoppingCart() {
        Long id = userInput.getLong("Enter shopping cart id");
        try {
            shoppingCartService.findShoppingCartById(id).orElseThrow();
            shoppingCartService.deleteShoppingCart(id);
            System.out.printf("Shopping cart deleted. { ID: %d }%n", id);
        } catch (NoSuchElementException e) {
            System.out.println("Shopping cart not found.");
        }
    }
}
