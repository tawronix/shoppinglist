DROP SCHEMA IF EXISTS shoppinglist;
CREATE SCHEMA shoppinglist DEFAULT CHARACTER SET utf8;
USE shoppinglist;

CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    category VARCHAR(45) NULL,
    price DECIMAL(9,2) NOT NULL,
    discount DECIMAL(4,1) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (id),
    UNIQUE KEY (name)
);

CREATE TABLE shopping_carts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE shopping_cart_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    shopping_cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity DECIMAL(9,3) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_carts (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
