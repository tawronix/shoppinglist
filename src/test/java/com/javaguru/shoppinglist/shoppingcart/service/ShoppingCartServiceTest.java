package com.javaguru.shoppinglist.shoppingcart.service;

import com.javaguru.shoppinglist.shoppingcart.repository.ShoppingCartRepository;
import com.javaguru.shoppinglist.shoppingcart.validation.ShoppingCartValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartValidationService validationService;
    @InjectMocks
    private ShoppingCartService victim;

    @Test
    public void test() {
        //TODO: test implementation
    }
}
