package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.console.menu.Menu;
import org.springframework.stereotype.Component;

@Component
public class ConsoleUI {
    private final Menu mainMenu;

    public ConsoleUI(Menu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void start() {
        try {
            mainMenu.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
