package com.javaguru.shoppinglist;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

class ShoppingListApplication {
    private final Map<Long, Product> productRepository;
    private long productIdSequence;
    private final Scanner scanner;

    public ShoppingListApplication() {
        productRepository = new HashMap<>();
        productIdSequence = 0L;
        scanner = new Scanner(System.in);
    }

    public void start() {
        // Application main loop
        while (true) {
            try {
                System.out.println("MENU | " +
                        "1. Create product | " +
                        "2. Find product by id | " +
                        "3. Edit product | " +
                        "4. Delete product | " +
                        "5. Exit");
                int userInput = Integer.parseInt(getUserInput("Select menu item"));
                switch (userInput) {
                    case 1:
                        createProduct();
                        break;
                    case 2:
                        findProductById();
                        break;
                    case 3:
                        editProduct();
                        break;
                    case 4:
                        deleteProduct();
                        break;
                    case 5:
                        return;
                    default:
                        throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong menu number! Please try again.");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-2);
            }
        }
    }

    private String getUserInput (String hint) {
        String userInput = null;
        try {
            System.out.print(hint + ": ");
            userInput = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Input stream was closed or interrupted!");
            System.exit(-1);
        }
        return userInput;
    }

    // Get user response to the question.
    // Returns True if the first char in input is 'y', otherwise False.
    private boolean getUserChoice(String question) {
        String userInput = getUserInput(question);
        char firstChar = userInput.length() > 0? userInput.toLowerCase().charAt(0) : 'n';
        return  firstChar == 'y';
    }

    private void createProduct() {
        Product product = new Product();
        // Product name
        while (true) {
            try {
                String name = getUserInput("Enter product name");
                product.setName(name);
                break;
            } catch (DataValidationException e) {
                System.out.println(e.getMessage() + " Please try again.");
            }
        }
        // Product category
        String category = getUserInput("Enter product category");
        product.setCategory(category);
        // Product price
        while (true) {
            try {
                BigDecimal price = new BigDecimal(getUserInput("Enter product price"));
                product.setPrice(price);
                break;
            } catch (DataValidationException | NumberFormatException e) {
                System.out.println(e.getMessage() + " Please try again.");
            }
        }
        // Product discount
        while (true) {
            try {
                BigDecimal discount = new BigDecimal(getUserInput("Enter product discount"));
                product.setDiscount(discount);
                break;
            } catch (DataValidationException | NumberFormatException e) {
                System.out.println(e.getMessage() + " Please try again.");
            }
        }
        // Product description
        String description = getUserInput("Enter product description (optional)");
        product.setDescription(description);

        product.setId(productIdSequence);
        productRepository.put(productIdSequence, product);
        productIdSequence++;
        System.out.printf("New product created. { ID: %d }%n", product.getId());
    }

    private void findProductById() {
        while (true) {
            try {
                long id = Long.parseLong(getUserInput("Enter product id"));
                Product findProductResult = productRepository.get(id);
                System.out.println(findProductResult != null ? findProductResult : "Product not found.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format! Please try again.");
            }
        }
    }

    private void editProduct() {
        long id;
        while (true) {
            try {
                id = Long.parseLong(getUserInput("Enter product id"));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format! Please try again.");
            }
        }
        Product product = productRepository.get(id);
        if (product != null) {
            System.out.println(product);
            if (getUserChoice("Do you want to edit NAME? (Y / N)")) {
                while (true) {
                    try {
                        String newName = getUserInput("Enter new name");
                        product.setName(newName);
                        break;
                    } catch (DataValidationException e) {
                        System.out.println(e.getMessage() + " Please try again.");
                    }
                }
            }
            if (getUserChoice("Do you want to edit CATEGORY? (Y / N)")) {
                String newCategory = getUserInput("Enter new category");
                product.setCategory(newCategory);
            }
            if (getUserChoice("Do you want to edit PRICE? (Y / N)")) {
                while (true) {
                    try {
                        BigDecimal newPrice = new BigDecimal(getUserInput("Enter new price"));
                        product.setPrice(newPrice);
                        break;
                    } catch (DataValidationException | NumberFormatException e) {
                        System.out.println(e.getMessage() + " Please try again.");
                    }
                }
            }
            if (getUserChoice("Do you want to edit DISCOUNT? (Y / N)")) {
                while (true) {
                    try {
                        BigDecimal newDiscount = new BigDecimal(getUserInput("Enter new discount"));
                        product.setDiscount(newDiscount);
                        break;
                    } catch (DataValidationException | NumberFormatException e) {
                        System.out.println(e.getMessage() + " Please try again.");
                    }
                }
            }
            if (getUserChoice("Do you want to edit DESCRIPTION? (Y / N)")) {
                String newDescription = getUserInput("Enter product description (optional)");
                product.setDescription(newDescription);
            }
            System.out.printf("Product updated. { ID: %d }%n", product.getId());
        } else {
            System.out.println("Product not found.");
        }
    }

    private void deleteProduct() {
        while (true) {
            try {
                long id = Long.parseLong(getUserInput("Enter product id"));
                Product product = productRepository.get(id);
                if (product != null) {
                    productRepository.remove(product.getId());
                    System.out.printf("Product deleted. { ID: %d }%n", product.getId());
                } else {
                    System.out.println("Product not found.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format! Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ShoppingListApplication application = new ShoppingListApplication();
        application.start();
    }
}
