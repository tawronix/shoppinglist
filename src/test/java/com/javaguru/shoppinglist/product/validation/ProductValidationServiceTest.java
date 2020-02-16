package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductValidationServiceTest {
    private List<ProductValidationRule> validationRules;
    private ProductValidationService victim;
    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Before
    public void init() {
        validationRules = new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> validationRules.add(mock(ProductValidationRule.class)));
        victim = new ProductValidationService(validationRules);
    }

    @Test
    public void shouldValidateForAllRules() {
        Product product = new Product();
        int originalProductHash = product.hashCode();

        victim.validate(product);

        validationRules.forEach(rule -> verify(rule).validate(productCaptor.capture()));
        assertThat(productCaptor.getAllValues()).containsOnly(product);
        assertThat(product.hashCode()).isEqualTo(originalProductHash);
    }
}
