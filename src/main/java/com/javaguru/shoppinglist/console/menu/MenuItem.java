package com.javaguru.shoppinglist.console.menu;

public class MenuItem {
    private final String title;
    private final MenuAction menuAction;

    public MenuItem(String title, MenuAction menuAction) {
        this.title = title;
        this.menuAction = menuAction;
    }

    public String getTitle() {
        return title;
    }

    public void doAction() {
        menuAction.doAction();
    }
}
