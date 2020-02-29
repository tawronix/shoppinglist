package com.javaguru.shoppinglist.product.service;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import com.javaguru.shoppinglist.product.validation.ProductValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductValidationService validationService;
    @InjectMocks
    private ProductService victim;
    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Test
    public void testCreateProduct() {
        Product product = createTestProduct();
        int originalProductHash = product.hashCode();
        when(productRepository.insert(product)).thenReturn(product);

        Long result = victim.createProduct(product);

        InOrder inOrder = inOrder(validationService, productRepository);
        inOrder.verify(validationService).validate(productCaptor.capture());
        inOrder.verify(productRepository).insert(productCaptor.capture());

        assertThat(productCaptor.getAllValues()).containsOnly(product);
        assertThat(product.hashCode()).isEqualTo(originalProductHash);
        assertThat(result).isEqualTo(product.getId());
    }

    @Test
    public void testFindProductById() {
        Optional<Product> product = Optional.of(createTestProduct());
        when(productRepository.findById(9999L)).thenReturn(product);

        Optional<Product> result = victim.findProductById(9999L);

        assertThat(result).isEqualTo(product);
    }

    @Test
    public void testFindProductByName() {
        Optional<Product> product = Optional.of(createTestProduct());
        when(productRepository.findByName("TEST_PRODUCT")).thenReturn(product);

        Optional<Product> result = victim.findProductByName("TEST_PRODUCT");

        assertThat(result).isEqualTo(product);
    }

    @Test
    public void testUpdateProduct() {
        Product product = createTestProduct();
        int originalProductHash = product.hashCode();

        victim.updateProduct(product);

        InOrder inOrder = inOrder(validationService, productRepository);
        inOrder.verify(validationService).validate(productCaptor.capture());
        inOrder.verify(productRepository).update(productCaptor.capture());

        assertThat(productCaptor.getAllValues()).containsOnly(product);
        assertThat(product.hashCode()).isEqualTo(originalProductHash);
    }

    @Test
    public void testDeleteProduct() {
        Long id = 9999L;
        victim.deleteProduct(id);
        verify(productRepository).delete(id);
    }

    private Product createTestProduct() {
        Product product = new Product();
        product.setId(9999L);
        product.setName("TEST_PRODUCT");
        return product;
    }
}
