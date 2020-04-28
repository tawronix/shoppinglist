package com.javaguru.shoppinglist.service.validation;

public interface ValidationRule<T> {
    void validate(T domain);
}
