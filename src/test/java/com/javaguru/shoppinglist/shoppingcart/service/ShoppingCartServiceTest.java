package com.javaguru.shoppinglist.shoppingcart.service;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartValidationService validationService;
    @InjectMocks
    private ShoppingCartService victim;
    @Captor
    private ArgumentCaptor<ShoppingCart> shoppingCartCaptor;

    @Test
    public void testCreateShoppingCart() {
        ShoppingCart dummyShoppingCart = new ShoppingCart();
        when(shoppingCartRepository.insert(any())).thenReturn(dummyShoppingCart);
        ShoppingCart shoppingCart = createShoppingCart();
        int originalHash = shoppingCart.hashCode();
        when(shoppingCartRepository.insert(shoppingCart)).thenReturn(shoppingCart);

        Long result = victim.createShoppingCart(shoppingCart);

        InOrder inOrder = inOrder(validationService, shoppingCartRepository);
        inOrder.verify(validationService).validate(shoppingCartCaptor.capture());
        inOrder.verify(shoppingCartRepository).insert(shoppingCartCaptor.capture());

        assertThat(shoppingCartCaptor.getAllValues()).containsOnly(shoppingCart);
        assertThat(shoppingCart.hashCode()).isEqualTo(originalHash);
        assertThat(result).isEqualTo(shoppingCart.getId());
    }

    private ShoppingCart createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(9999L);
        shoppingCart.setName("SHOPPING_CART");
        return shoppingCart;
    }
}
