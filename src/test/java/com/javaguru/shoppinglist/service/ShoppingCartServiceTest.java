package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.domain.ShoppingCartItem;
import com.javaguru.shoppinglist.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.service.validation.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ValidationService<ShoppingCart> validationService;

    @Spy
    @InjectMocks
    private ShoppingCartService victim;

    @Test
    public void findById() {
        ShoppingCart shoppingCart = createTestShoppingCart();
        when(shoppingCartRepository.get(999L)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartRepository.get(111L)).thenReturn(Optional.empty());

        ShoppingCart result = victim.findById(999L);
        assertThat(result).isEqualTo(shoppingCart);

        assertThatThrownBy(() -> victim.findById(111L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("ShoppingCart not found (id=111)");
    }

    @Test
    public void findAll() {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartList.add(createTestShoppingCart());
        when(shoppingCartRepository.getAll()).thenReturn(shoppingCartList);

        List<ShoppingCart> result = victim.findAll();

        assertThat(result).isEqualTo(shoppingCartList);
    }

    @Test
    public void save() {
        ShoppingCart shoppingCart = createTestShoppingCart();
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart.getId());

        Long result = victim.save(shoppingCart);

        InOrder inOrder = Mockito.inOrder(validationService, shoppingCartRepository);
        inOrder.verify(validationService).validate(shoppingCart);
        inOrder.verify(shoppingCartRepository).save(shoppingCart);

        assertThat(result).isEqualTo(shoppingCart.getId());
    }

    @Test
    public void update() {
        ShoppingCart oldShoppingCart = createTestShoppingCart();
        ShoppingCart newShoppingCart = createTestShoppingCart();
        newShoppingCart.setName("NEW_NAME");
        newShoppingCart.getItems().remove(1);
        Product product = new Product();
        product.setId(3L);
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(product);
        item.setQuantity(new BigDecimal("1"));
        newShoppingCart.getItems().add(item);
        Mockito.doReturn(oldShoppingCart).when(victim).findById(oldShoppingCart.getId());

        victim.update(newShoppingCart);

        verify(validationService).validate(newShoppingCart);
        assertThat(oldShoppingCart).isEqualTo(newShoppingCart);
    }

    @Test
    public void delete() {
        ShoppingCart shoppingCart = createTestShoppingCart();
        Mockito.doReturn(shoppingCart).when(victim).findById(shoppingCart.getId());

        victim.delete(shoppingCart.getId());

        verify(shoppingCartRepository).delete(shoppingCart);
    }

    private ShoppingCart createTestShoppingCart() {
        Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        ShoppingCartItem item1 = new ShoppingCartItem();
        item1.setId(1L);
        item1.setProduct(product1);
        item1.setQuantity(new BigDecimal("3"));

        ShoppingCartItem item2 = new ShoppingCartItem();
        item2.setId(2L);
        item2.setProduct(product2);
        item2.setQuantity(new BigDecimal("5"));

        List<ShoppingCartItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(999L);
        shoppingCart.setName("TEST_NAME");
        shoppingCart.setItems(items);

        return shoppingCart;
    }
}