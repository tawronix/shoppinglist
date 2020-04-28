package com.javaguru.shoppinglist.dto;

import com.javaguru.shoppinglist.domain.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private Long id;
    private String name;
    private List<ShoppingCartItemDTO> items;

    public ShoppingCartDTO(ShoppingCart shoppingCart) {
        id = shoppingCart.getId();
        name = shoppingCart.getName();
        items = new ArrayList<>();
        shoppingCart.getItems().forEach(shoppingCartItem -> items.add(new ShoppingCartItemDTO(shoppingCartItem)));
    }

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

    public List<ShoppingCartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItemDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
