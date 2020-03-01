package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.config.AppConfig;
import com.javaguru.shoppinglist.console.ConsoleUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ShoppingListApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean(ConsoleUI.class).start();
    }
}
