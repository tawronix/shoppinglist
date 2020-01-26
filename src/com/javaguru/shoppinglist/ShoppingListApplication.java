package com.javaguru.shoppinglist;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ShoppingListApplication {

    public static void main(String[] args) {
        Map<Long, Product> productRepository = new HashMap<>();
        long productIdSequence = 0L;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("1. Create product");
                System.out.println("2. Find product by id");
                System.out.println("3. Exit");
                System.out.print("Select menu item: ");
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1: // Create product
                        Product product = new Product();
                        // Product name
                        while (true) {
                            System.out.print("Enter product name: ");
                            try {
                                String name = scanner.nextLine();
                                product.setName(name);
                                break;
                            } catch (DataValidationException e) {
                                System.out.println(e.getMessage() + " Please try again.");
                            }
                        }
                        // Product category
                        System.out.print("Enter product category: ");
                        String category = scanner.nextLine();
                        product.setCategory(category);
                        // Product price
                        while (true) {
                            System.out.print("Enter product price: ");
                            try {
                                BigDecimal price = new BigDecimal(scanner.nextLine());
                                product.setPrice(price);
                                break;
                            } catch (DataValidationException | NumberFormatException e) {
                                System.out.println(e.getMessage() + " Please try again.");
                            }
                        }
                        // Product discount
                        while (true) {
                            System.out.print("Enter product discount: ");
                            try {
                                BigDecimal discount = new BigDecimal(scanner.nextLine());
                                product.setDiscount(discount);
                                break;
                            } catch (DataValidationException | NumberFormatException e) {
                                System.out.println(e.getMessage() + " Please try again.");
                            }
                        }
                        // Product description
                        System.out.print("Enter product description (optional): ");
                        String description = scanner.nextLine();
                        product.setDescription(description);

                        product.setId(productIdSequence);
                        productRepository.put(productIdSequence, product);
                        productIdSequence++;
                        System.out.printf("New product created successfully. { ID: %d }%n", product.getId());
                        break;
                    case 2: // Find product by id
                        System.out.print("Enter product id: ");
                        long id = scanner.nextLong();
                        Product findProductResult = productRepository.get(id);
                        System.out.println(findProductResult != null? findProductResult : "Product not found.");
                        break;
                    case 3: // Exit
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error! Please try again.");
            }
        }
    }
}
