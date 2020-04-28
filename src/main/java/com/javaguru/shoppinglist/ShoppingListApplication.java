package com.javaguru.shoppinglist;

import com.javaguru.shoppinglist.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ShoppingListApplication {
    public static void main(String[] args) {
        ShoppingListApplication.run();
    }

    public static void run() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
