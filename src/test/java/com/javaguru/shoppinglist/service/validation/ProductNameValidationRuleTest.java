package com.javaguru.shoppinglist.service.validation;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.repository.ProductRepository;
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
    private ProductRepository productRepository;

    @InjectMocks
    private ProductNameValidationRule victim;

    @Test
    public void shouldFailValidation() {
        Product product = new Product();
        product.setId(999L);

        Product existingProduct = new Product();
        existingProduct.setId(111L);
        existingProduct.setName("Existing name");
        when(productRepository.getByName("Existing name")).thenReturn(Optional.of(existingProduct));

        product.setName(null);
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product name can't be empty.");

        product.setName("Na");
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product name can't be less than 3 and more than 32 characters.");

        product.setName("Very loooooooooooooooooooong name");
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product name can't be less than 3 and more than 32 characters.");

        product.setName("Existing name");
        assertThatThrownBy(() -> victim.validate(product))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Product with the same name already exists.");
    }

    @Test
    public void shouldPassValidation() {
        Product product = new Product();
        product.setId(999L);
        when(productRepository.getByName("Unique name")).thenReturn(Optional.empty());

        product.setName("Nam");
        victim.validate(product);

        product.setName("Loooooooooooooooooooooooong name");
        victim.validate(product);

        product.setName("Unique name");
        victim.validate(product);
    }
}