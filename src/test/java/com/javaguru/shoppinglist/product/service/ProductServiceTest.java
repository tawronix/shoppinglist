package com.javaguru.shoppinglist.product.service;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.repository.ProductRepository;
import com.javaguru.shoppinglist.product.validation.ProductValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

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
        Product dummyProduct = new Product();
        when(productRepository.insert(any())).thenReturn(dummyProduct);
        Product product = createTestProduct();
        int originalProductHash = product.hashCode();
        when(productRepository.insert(product)).thenReturn(product);

        Long result = victim.createProduct(product);

        InOrder inOrder = inOrder(validationService, productRepository);
        inOrder.verify(validationService).validate(productCaptor.capture());
        inOrder.verify(productRepository).insert(productCaptor.capture());

        Product validatedProduct = productCaptor.getAllValues().get(0);
        Product insertedProduct = productCaptor.getAllValues().get(1);
        assertThat(validatedProduct).isEqualTo(product);
        assertThat(insertedProduct).isEqualTo(product);
        assertThat(product.hashCode()).isEqualTo(originalProductHash);
        assertThat(result).isEqualTo(product.getId());
    }

    @Test
    public void testFindProductById() {
        Long id = 9999L;
        victim.findProductById(id);
        verify(productRepository).findById(id);
    }

    @Test
    public void testFindProductByName() {
        String name = "SOME_NAME";
        victim.findProductByName(name);
        verify(productRepository).findByName(name);
    }

    @Test
    public void testUpdateProduct() {
        Product product = createTestProduct();
        int originalProductHash = product.hashCode();

        victim.updateProduct(product);

        InOrder inOrder = inOrder(validationService, productRepository);
        inOrder.verify(validationService).validate(productCaptor.capture());
        inOrder.verify(productRepository).update(productCaptor.capture());

        Product validatedProduct = productCaptor.getAllValues().get(0);
        Product updatedProduct = productCaptor.getAllValues().get(1);
        assertThat(validatedProduct).isEqualTo(product);
        assertThat(updatedProduct).isEqualTo(product);
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