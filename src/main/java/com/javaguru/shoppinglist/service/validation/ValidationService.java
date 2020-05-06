package com.javaguru.shoppinglist.service.validation;

import java.util.Set;

public class ValidationService<T> {
    private final Set<ValidationRule<T>> validationRules;

    public ValidationService(Set<ValidationRule<T>> validationRules) {
        this.validationRules = validationRules;
    }

    public void validate(T domain) {
        if (domain == null) {
            throw new ValidationException("Domain being validated must be not null.");
        }
        validationRules.forEach(rule -> rule.validate(domain));
    }
}
