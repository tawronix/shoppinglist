package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import com.javaguru.shoppinglist.shoppingcart.ProductListItem;
import com.javaguru.shoppinglist.shoppingcart.service.ShoppingCartService;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ShoppingCartMenuService {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final Menu menu;
    private final UserInput userInput = new UserInput();

    public ShoppingCartMenuService(ShoppingCartService shoppingCartService, ProductService productService, Menu menu) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.menu = menu;
    }

    public void createShoppingCart() {
        String name = userInput.getString("Enter shopping cart name");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setName(name);
        addProductsToShoppingCart(shoppingCart);

        try {
            Long id = shoppingCartService.createShoppingCart(shoppingCart);
            System.out.printf("New shopping cart created. { ID: %d }%n", id);
        } catch (ShoppingCartValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void findShoppingCart() {
        Long id = userInput.getLong("Enter shopping cart id");
        ShoppingCart shoppingCart = shoppingCartService.findById(id);
        if (shoppingCart != null) {
            printShoppingCart(shoppingCart);
        } else {
            System.out.println("Shopping cart not found.");
        }
    }

    public void deleteShoppingCart() {
        Long id = userInput.getLong("Enter shopping cart id");
        boolean result = shoppingCartService.deleteShoppingCart(id);
        System.out.println(result ? String.format("Shopping cart deleted. { ID: %d }", id) : "Shopping cart not found.");
    }

    public void back() {
        menu.setActive(false);
    }

    private void addProductsToShoppingCart(ShoppingCart shoppingCart) {
        boolean addNextProduct = true;
        while (addNextProduct) {
            Long id = userInput.getLong("Enter product id (0 - to finish)");
            if (id != 0) {
                Optional<Product> product = productService.findProductById(id);
                if (product.isPresent()) {
                    System.out.println(product);
                    BigDecimal quantity = userInput.getBigDecimal("Enter quantity");
                    shoppingCart.addProduct(product.get(), quantity);
                } else {
                    System.out.println("Product not found.");
                }
            } else {
                addNextProduct = false;
            }
        }
    }

    private void printShoppingCart(ShoppingCart shoppingCart) {
        BigDecimal totalCost = shoppingCartService.getShoppingCartTotalCost(shoppingCart);
        List<ProductListItem> productList = shoppingCart.getProductList();
        System.out.println("-".repeat(100));
        System.out.printf("Shopping cart: \"%s\" | Total cost: %.2f%n", shoppingCart.getName(), totalCost);
        productList.forEach(item -> {
            Product product = item.getProduct();
            System.out.printf("\t%d. %s | Price: %.2f | Discount: %.1f%% | Quantity: %.3f%n",
                    productList.indexOf(item) + 1, product.getName(), product.getPrice(),
                    product.getDiscount(), item.getQuantity()
            );
        });
        System.out.println("-".repeat(100));
    }
}
