package com.javaguru.shoppinglist.console.menu;

import com.javaguru.shoppinglist.console.UserInput;

import java.util.List;

public abstract class Menu {
    protected final UserInput userInput = new UserInput();

    private final String title = getTitle();
    private final List<MenuItem> menuItems = getMenuItems();

    private boolean active;

    public void show() {
        active = true;
        while (active) {
            printMenu();
            waitUserChoice();
        }
    }

    protected abstract String getTitle();

    protected abstract List<MenuItem> getMenuItems();

    protected void deactivate() {
        this.active = false;
    }

    private void printMenu() {
        StringBuffer menuString = new StringBuffer(title);
        menuItems.forEach(menuItem -> menuString.append(String.format(" | %d. %s", menuItems.indexOf(menuItem) + 1, menuItem.getTitle())));

        System.out.println("-".repeat(menuString.length()));
        System.out.println(menuString);
        System.out.println("-".repeat(menuString.length()));
    }

    private void waitUserChoice() {
        while (true) {
            int itemNumber = userInput.getInt("Select menu item");
            try {
                menuItems.get(itemNumber - 1).doAction();
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid menu item number! Please try again.");
            }
        }
    }
}
