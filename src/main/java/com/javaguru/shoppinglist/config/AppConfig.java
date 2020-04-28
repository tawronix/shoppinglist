package com.javaguru.shoppinglist.config;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.service.validation.ValidationRule;
import com.javaguru.shoppinglist.service.validation.ValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

@Configuration
@ComponentScan(basePackages = "com.javaguru.shoppinglist")
@PropertySource("classpath:app.properties")
public class AppConfig {
    @Bean
    public ValidationService<Product> productValidationService(Set<ValidationRule<Product>> validationRules) {
        return new ValidationService<>(validationRules);
    }

    @Bean
    public ValidationService<ShoppingCart> shoppingCartValidationService(Set<ValidationRule<ShoppingCart>> validationRules) {
        return new ValidationService<>(validationRules);
    }
}
