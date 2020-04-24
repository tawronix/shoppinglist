package com.javaguru.shoppinglist.shoppingcart.repository;

import com.javaguru.shoppinglist.shoppingcart.ShoppingCart;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@Profile({"hibernate", "default"})
public class HibernateShoppingCartRepository implements ShoppingCartRepository {
    private final SessionFactory sessionFactory;

    public HibernateShoppingCartRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart insert(ShoppingCart shoppingCart) {
        sessionFactory.getCurrentSession().save(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        ShoppingCart shoppingCart = sessionFactory.getCurrentSession().get(ShoppingCart.class, id);

        if (shoppingCart != null) {
            Hibernate.initialize(shoppingCart.getProductList());
        }

        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public void delete(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        sessionFactory.getCurrentSession().delete(shoppingCart);
    }
}
