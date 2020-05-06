package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.repository.ShoppingCartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartNameValidationRuleTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartNameValidationRule victim;

    @Test
    public void shouldFailValidation() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(999L);

        ShoppingCart existingShoppingCart = new ShoppingCart();
        existingShoppingCart.setId(111L);
        existingShoppingCart.setName("Existing name");
        when(shoppingCartRepository.getByName("Existing name")).thenReturn(Optional.of(existingShoppingCart));

        shoppingCart.setName(null);
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart name can't be empty.");

        shoppingCart.setName("Na");
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart name can't be less than 3 and more than 32 characters.");

        shoppingCart.setName("Very loooooooooooooooooooong name");
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart name can't be less than 3 and more than 32 characters.");

        shoppingCart.setName("Existing name");
        assertThatThrownBy(() -> victim.validate(shoppingCart))
                .isInstanceOf(ValidationException.class)
                .hasMessage("ShoppingCart with the same name already exists.");
    }

    @Test
    public void shouldPassValidation() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(999L);
        when(shoppingCartRepository.getByName("Unique name")).thenReturn(Optional.empty());

        shoppingCart.setName("Nam");
        victim.validate(shoppingCart);

        shoppingCart.setName("Loooooooooooooooooooooooong name");
        victim.validate(shoppingCart);

        shoppingCart.setName("Unique name");
        victim.validate(shoppingCart);
    }
}