package com.javaguru.shoppinglist.console.menu;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenu extends Menu {
    private final Menu productsMenu;
    private final Menu shoppingCartsMenu;

    public MainMenu(Menu productsMenu, Menu shoppingCartsMenu) {
        this.productsMenu = productsMenu;
        this.shoppingCartsMenu = shoppingCartsMenu;
    }

    @Override
    protected String getTitle() {
        return "MENU";
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Products", this::products));
        menuItems.add(new MenuItem("Shopping carts", this::shoppingCarts));
        menuItems.add(new MenuItem("Exit", this::deactivate));
        return menuItems;
    }

    private void products() {
        productsMenu.show();
    }

    private void shoppingCarts() {
        shoppingCartsMenu.show();
    }
}
