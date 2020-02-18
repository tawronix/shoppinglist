package com.javaguru.shoppinglist.console;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final String title;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final UserInput userInput = new UserInput();

    private boolean active;

    public Menu(String title) {
        this.title = title;
    }

    public void addItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        menuItem.setNumber(menuItems.indexOf(menuItem) + 1);
    }

    public void show() {
        active = true;
        System.out.print(title);
        menuItems.forEach(menuItem -> System.out.print(" | " + menuItem));
        System.out.println();

        while (true) {
            int menuItemNumber = userInput.getInt("Select menu item");
            try {
                MenuItem menuItem = menuItems.get(menuItemNumber - 1);
                menuItem.doAction();
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid menu item number! Please try again.");
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
