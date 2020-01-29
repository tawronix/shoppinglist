package com.javaguru.shoppinglist;

import java.math.BigDecimal;

public class Product {

    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private BigDecimal discount;
    private String description;

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
        if (name.length() < 3 || name.length() > 32) {
            throw new DataValidationException("Product name can't be less than 3 and more than 32 characters.");
        }
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
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DataValidationException("Product price must be greater than zero.");
        }
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(new BigDecimal("100")) > 0 ) {
            throw new DataValidationException("Product discount can't be negative or greater than 100%.");
        }
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
        String format = "Product { ID: %d | Name: %s | Category: %s | Price: %.2f | Discount: %.0f%% | Description: %s }";
        return String.format(format, id, name, category, price, discount, description);
    }
}
