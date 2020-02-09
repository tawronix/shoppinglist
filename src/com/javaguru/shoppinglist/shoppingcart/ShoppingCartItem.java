package com.javaguru.shoppinglist.shoppingcart;

import com.javaguru.shoppinglist.product.Product;

import java.math.BigDecimal;

public class ShoppingCartItem {
    private Product product;
    private BigDecimal quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
