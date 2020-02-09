package com.javaguru.shoppinglist.shoppingcart;

import com.javaguru.shoppinglist.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private String name;
    private List<ShoppingCartItem> productList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShoppingCartItem> getProductList() {
        return productList;
    }

    public void addProduct(Product product, BigDecimal quantity) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(quantity);
        productList.add(shoppingCartItem);
    }
}
