package com.javaguru.shoppinglist.service;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
import com.javaguru.shoppinglist.service.validation.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ValidationService<Product> validationService;

    @Spy
    @InjectMocks
    private ProductService victim;

    @Test
    public void findById() {
        Product product = createTestProduct();
        when(productRepository.get(999L)).thenReturn(Optional.of(product));
        when(productRepository.get(111L)).thenReturn(Optional.empty());

        Product result = victim.findById(999L);
        assertThat(result).isEqualTo(product);

        assertThatThrownBy(() -> victim.findById(111L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Product not found (id=111)");
    }

    @Test
    public void findAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(createTestProduct());
        when(productRepository.getAll()).thenReturn(productList);

        List<Product> result = victim.findAll();
        assertThat(result).isEqualTo(productList);
    }

    @Test
    public void save() {
        Product product = createTestProduct();
        when(productRepository.save(product)).thenReturn(product.getId());

        Long result = victim.save(product);

        InOrder inOrder = Mockito.inOrder(validationService, productRepository);
        inOrder.verify(validationService).validate(product);
        inOrder.verify(productRepository).save(product);

        assertThat(result).isEqualTo(product.getId());
    }

    @Test
    public void update() {
        Product oldProduct = createTestProduct();
        Product newProduct = createTestProduct();
        newProduct.setName("NEW_NAME");
        Mockito.doReturn(oldProduct).when(victim).findById(oldProduct.getId());

        victim.update(newProduct);

        verify(validationService).validate(newProduct);
        assertThat(oldProduct).isEqualTo(newProduct);
    }

    @Test
    public void delete() {
        Product product = createTestProduct();
        Mockito.doReturn(product).when(victim).findById(product.getId());

        victim.delete(product.getId());

        verify(productRepository).delete(product);
    }

    private Product createTestProduct() {
        Product product = new Product();
        product.setId(999L);
        product.setName("TEST_NAME");
        return product;
    }
}