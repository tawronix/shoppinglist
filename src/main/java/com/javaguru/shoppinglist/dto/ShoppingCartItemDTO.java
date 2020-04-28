package com.javaguru.shoppinglist.dto;

import com.javaguru.shoppinglist.domain.ShoppingCartItem;

import java.math.BigDecimal;

public class ShoppingCartItemDTO {
    private Long id;
    private ProductDTO product;
    private BigDecimal quantity;

    public ShoppingCartItemDTO(ShoppingCartItem shoppingCartItem) {
        id = shoppingCartItem.getId();
        product = new ProductDTO(shoppingCartItem.getProduct());
        quantity = shoppingCartItem.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShoppingCartItemDTO{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
