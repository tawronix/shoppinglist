package com.javaguru.shoppinglist.console;

public class MenuItem {
    private final String title;
    private final MenuItemAction action;
    private int number;

    public MenuItem(String title, MenuItemAction action) {
        this.title = title;
        this.action = action;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void doAction() {
        action.doAction();
    }

    @Override
    public String toString() {
        return String.format("%d. %s", number, title);
    }
}
