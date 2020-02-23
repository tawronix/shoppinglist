package com.javaguru.shoppinglist.config;

import com.javaguru.shoppinglist.product.repository.ProductInMemoryRepository;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartInMemoryRepository;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.javaguru.shoppinglist")
public class AppConfig {
    @Bean
    public ProductRepository productRepository() {
        return new ProductInMemoryRepository();
    }

    @Bean
    public ShoppingCartRepository shoppingCartRepository() {
        return new ShoppingCartInMemoryRepository();
    }
}
