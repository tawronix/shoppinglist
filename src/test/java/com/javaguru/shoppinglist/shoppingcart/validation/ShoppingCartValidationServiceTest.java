package com.javaguru.shoppinglist.shoppingcart.validation;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
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
public class ShoppingCartValidationServiceTest {
    private List<ShoppingCartValidationRule> validationRules;
    private ShoppingCartValidationService victim;
    @Captor
    private ArgumentCaptor<ShoppingCart> shoppingCartCaptor;

    @Before
    public void init() {
        validationRules = new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> validationRules.add(mock(ShoppingCartValidationRule.class)));
        victim = new ShoppingCartValidationService(validationRules);
    }

    @Test
    public void shouldValidateForAllRules() {
        ShoppingCart shoppingCart = new ShoppingCart();
        int originalShoppingCartHash = shoppingCart.hashCode();

        victim.validate(shoppingCart);

        validationRules.forEach(rule -> verify(rule).validate(shoppingCartCaptor.capture()));
        assertThat(shoppingCartCaptor.getAllValues()).containsOnly(shoppingCart);
        assertThat(shoppingCart.hashCode()).isEqualTo(originalShoppingCartHash);
    }
}
