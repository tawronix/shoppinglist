package com.javaguru.shoppinglist.console;

public class ConsoleUI {
    private final Menu mainMenu;

    public ConsoleUI(Menu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void start() {
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
