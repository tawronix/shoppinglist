package com.javaguru.shoppinglist.product.repository;

import com.javaguru.shoppinglist.product.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("local")
public class DefaultProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public DefaultProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product insert(Product product) {
        String query = "INSERT INTO products (name, category, price, discount, description) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setBigDecimal(3, product.getPrice());
            ps.setBigDecimal(4, product.getDiscount());
            ps.setString(5, product.getDescription());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            product.setId(keyHolder.getKey().longValue());
        }
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String query =
                "SELECT id, name, category, price, discount, description " +
                        "FROM products WHERE id = ?";

        List<Product> productList = jdbcTemplate.query(
                query, new Object[]{id}, new BeanPropertyRowMapper<>(Product.class)
        );
        return Optional.ofNullable(!productList.isEmpty() ? productList.get(0) : null);
    }

    @Override
    public Optional<Product> findByName(String name) {
        String query =
                "SELECT id, name, category, price, discount, description " +
                        "FROM products WHERE name = ?";

        List<Product> productList = jdbcTemplate.query(
                query, new Object[]{name}, new BeanPropertyRowMapper<>(Product.class)
        );
        return Optional.ofNullable(!productList.isEmpty() ? productList.get(0) : null);
    }

    @Override
    public void update(Product product) {
        String query =
                "UPDATE products SET name = ?, category = ?, price = ?, discount = ?, description = ? " +
                        "WHERE id = ?";

        jdbcTemplate.update(
                query,
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getDiscount(),
                product.getDescription(),
                product.getId()
        );
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
