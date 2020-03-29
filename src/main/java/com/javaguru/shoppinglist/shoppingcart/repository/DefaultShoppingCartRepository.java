package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.product.Product;
import com.javaguru.shoppinglist.shoppingcart.ProductListItem;
import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@Profile({"jdbc", "default"})
public class DefaultShoppingCartRepository implements ShoppingCartRepository {
    private final JdbcTemplate jdbcTemplate;

    public DefaultShoppingCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public ShoppingCart insert(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, shoppingCart.getName());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            shoppingCart.setId(keyHolder.getKey().longValue());

            String query2 = "INSERT INTO shopping_cart_items (shopping_cart_id, product_id, quantity) VALUES (?, ?, ?)";
            List<ProductListItem> productList = shoppingCart.getProductList();
            jdbcTemplate.batchUpdate(query2, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, shoppingCart.getId());
                    ps.setLong(2, productList.get(i).getProduct().getId());
                    ps.setBigDecimal(3, productList.get(i).getQuantity());
                }

                @Override
                public int getBatchSize() {
                    return productList.size();
                }
            });
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        String query =
                "SELECT s.id, s.name, p.id, p.name, p.category, p.price, p.discount, p.description, i.quantity " +
                        "FROM shopping_carts s " +
                        "LEFT JOIN shopping_cart_items i ON s.id = i.shopping_cart_id " +
                        "LEFT JOIN  products p ON i.product_id = p.id " +
                        "WHERE s.id = ?";

        List<ShoppingCart> shoppingCartList = jdbcTemplate.query(query, new Object[]{id}, (resultSet, i) -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setId(resultSet.getLong("s.id"));
            shoppingCart.setName(resultSet.getString("s.name"));
            do {
                Product product = new Product();
                product.setId(resultSet.getLong("p.id"));
                product.setName(resultSet.getString("p.name"));
                product.setCategory(resultSet.getString("category"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setDiscount(resultSet.getBigDecimal("discount"));
                product.setDescription(resultSet.getString("description"));
                shoppingCart.addProduct(product, resultSet.getBigDecimal("quantity"));
            } while (resultSet.next());
            return shoppingCart;
        });
        return Optional.ofNullable(!shoppingCartList.isEmpty() ? shoppingCartList.get(0) : null);
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM shopping_carts WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
