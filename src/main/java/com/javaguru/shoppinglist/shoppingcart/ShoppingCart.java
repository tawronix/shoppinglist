package com.javaguru.shoppinglist.shoppingcart;

import com.javaguru.shoppinglist.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private String name;
    private List<ProductListItem> productList = new ArrayList<>();

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

    public List<ProductListItem> getProductList() {
        return productList;
    }

    public void addProduct(Product product, BigDecimal quantity) {
        ProductListItem shoppingCartItem = new ProductListItem();
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(quantity);
        productList.add(shoppingCartItem);
    }
}
