package com.javaguru.shoppinglist.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest {
    @Mock
    private ValidationRule<Object> validationRule;

    private Set<ValidationRule<Object>> validationRules;

    private ValidationService<Object> victim;

    @Before
    public void init() {
        validationRules = new HashSet<>();
        IntStream.range(0, 5).forEach(i -> validationRules.add(validationRule));
        victim = new ValidationService<>(validationRules);
    }

    @Test
    public void validate() {
        Object domain = new Object();

        victim.validate(domain);

        validationRules.forEach(rule -> verify(rule).validate(domain));

        assertThatThrownBy(() -> victim.validate(null))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Domain being validated must be not null.");
    }
}