package com.javaguru.shoppinglist.product.validation;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.product.service.ProductService;

public class ProductNameValidationRule implements ProductValidationRule {
    private final ProductService productService;

    public ProductNameValidationRule(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void validate(Product product) {
        checkNotNull(product);

        String name = product.getName();
        if (name == null) {
            throw new ProductValidationException("Product name can't be empty.");
        }

        if (name.length() < 3 || name.length() > 32) {
            throw new ProductValidationException("Product name can't be less than 3 and more than 32 characters.");
        }

        Product foundProduct = productService.findProductByName(name);
        if (foundProduct != null && foundProduct.getId() != product.getId()) {
            throw new ProductValidationException("Product with the same name already exists.");
        }
    }
}
