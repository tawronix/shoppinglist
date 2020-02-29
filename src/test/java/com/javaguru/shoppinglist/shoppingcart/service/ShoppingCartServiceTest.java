package com.javaguru.shoppinglist.shoppingcart.service;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

    @Test
    public void testFindById() {
        Optional<ShoppingCart> shoppingCart = Optional.of(createShoppingCart());
        when(shoppingCartRepository.findById(9999L)).thenReturn(shoppingCart);

        Optional<ShoppingCart> result = victim.findById(9999L);

        assertThat(result).isEqualTo(shoppingCart);
    }

    @Test
    public void testDeleteShoppingCart() {
        Long id = 9999L;
        victim.deleteShoppingCart(id);
        verify(shoppingCartRepository).delete(id);
    }

    @Test
    public void shouldCalculateTotalCost() {
        ShoppingCart shoppingCart = createShoppingCart();

        Product p1 = new Product();
        p1.setPrice(new BigDecimal("25.00"));
        p1.setDiscount(new BigDecimal("5.0"));
        shoppingCart.addProduct(p1, new BigDecimal("10.0"));

        Product p2 = new Product();
        p2.setPrice(new BigDecimal("19.99"));
        p2.setDiscount(new BigDecimal("0"));
        shoppingCart.addProduct(p2, new BigDecimal("3.5"));

        Product p3 = new Product();
        p3.setPrice(new BigDecimal("33.75"));
        p3.setDiscount(new BigDecimal("7.5"));
        shoppingCart.addProduct(p3, new BigDecimal("7.325"));

        BigDecimal result = victim.getShoppingCartTotalCost(shoppingCart);

        assertThat(result).isEqualTo(new BigDecimal("536.14"));
    }

    private ShoppingCart createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(9999L);
        shoppingCart.setName("SHOPPING_CART");
        return shoppingCart;
    }
}
