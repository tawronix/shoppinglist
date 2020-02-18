package com.javaguru.shoppinglist.console;

public class ConsoleUI {
    public void start() {
        Menu mainMenu = UIFactory.getInstance().getMainMenu();
        try {
            do {
                mainMenu.show();
                System.out.println("-".repeat(100));
            } while (mainMenu.isActive());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
