package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductNameValidationRuleTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductNameValidationRule victim;

    private Product product;

    @Test
    public void shouldThrowProductValidationException() {
        product = null;
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product must be not null.");

        product = createProduct(null);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product name can't be empty.");

        product = createProduct("AA");
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product name can't be less than 3 and more than 32 characters.");

        product = createProduct("A".repeat(33));
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product name can't be less than 3 and more than 32 characters.");

        Optional<Product> foundProduct = Optional.of(createProduct("TEST_PRODUCT", 9999L));
        when(productService.findProductByName("TEST_PRODUCT")).thenReturn(foundProduct);
        product = createProduct("TEST_PRODUCT", 1234L);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product with the same name already exists.");
    }

    @Test
    public void shouldPassValidationSuccessfully() {
        product = createProduct("AAA");
        victim.validate(product);

        product = createProduct("A".repeat(32));
        victim.validate(product);

        when(productService.findProductByName("TEST_PRODUCT")).thenReturn(Optional.empty());
        product = createProduct("TEST_PRODUCT");
        victim.validate(product);

        Optional<Product> foundProduct = Optional.of(createProduct("TEST_PRODUCT_2", 1234L));
        when(productService.findProductByName("TEST_PRODUCT_2")).thenReturn(foundProduct);
        product = createProduct("TEST_PRODUCT_2", 1234L);
        victim.validate(product);
    }

    private Product createProduct(String name) {
        Product product = new Product();
        product.setName(name);
        return product;
    }

    private Product createProduct(String name, Long id) {
        Product product = createProduct(name);
        product.setId(id);
        return product;
    }
}