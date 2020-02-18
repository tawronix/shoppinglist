package com.javaguru.shoppinglist.console;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserInput {
    private final Scanner scanner = new Scanner(System.in);

    public String getString(String hint) {
        System.out.print(hint + ": ");
        String userInput = null;
        try {
            userInput = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Input stream was interrupted! Application will be closed.");
            System.exit(-1);
        }
        return userInput;
    }

    public Integer getInt(String hint) {
        int userInput;
        while (true) {
            try {
                userInput = Integer.parseInt(getString(hint));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format! Please try again.");
            }
        }
        return userInput;
    }

    public Long getLong(String hint) {
        long userInput;
        while (true) {
            try {
                userInput = Long.parseLong(getString(hint));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format! Please try again.");
            }
        }
        return userInput;
    }

    public BigDecimal getBigDecimal(String hint) {
        BigDecimal userInput;
        while (true) {
            try {
                userInput = new BigDecimal(getString(hint));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format! Please try again.");
            }
        }
        return userInput;
    }
}
