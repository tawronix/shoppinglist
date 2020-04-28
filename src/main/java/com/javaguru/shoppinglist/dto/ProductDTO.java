package com.javaguru.shoppinglist.dto;

import com.javaguru.shoppinglist.domain.Product;

import java.math.BigDecimal;

public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private BigDecimal discount;
    private String description;

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        category = product.getCategory();
        price = product.getPrice();
        discount = product.getDiscount();
        description = product.getDescription();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                '}';
    }
}
