package com.javaguru.shoppinglist.product;

import java.math.BigDecimal;

public class Product {
    private long id;
    private String name;
    private String category;
    private BigDecimal price;
    private BigDecimal discount;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        String format = "Product { ID: %d | Name: %s | Category: %s | Price: %.2f | Discount: %.1f%% | Description: %s }";
        return String.format(format, id, name, category, price, discount, description);
    }
}
