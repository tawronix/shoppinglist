package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.domain.ShoppingCart;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Optional<ShoppingCart> get(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(ShoppingCart.class, id));
    }

    @Override
    public Optional<ShoppingCart> getByName(String name) {
        Query<ShoppingCart> query = sessionFactory.getCurrentSession().createQuery(
                "SELECT s FROM ShoppingCart s WHERE s.name like :name", ShoppingCart.class);
        query.setParameter("name", name);
        return Optional.ofNullable(query.uniqueResult());
    }

    @Override
    public List<ShoppingCart> getAll() {
        return sessionFactory.getCurrentSession().createQuery(
                "SELECT s FROM ShoppingCart s", ShoppingCart.class).getResultList();
    }

    @Override
    public Long save(ShoppingCart shoppingCart) {
        shoppingCart.getItems().forEach(shoppingCartItem -> shoppingCartItem.setShoppingCart(shoppingCart));
        return (Long) sessionFactory.getCurrentSession().save(shoppingCart);
    }

    @Override
    public void delete(ShoppingCart shoppingCart) {
        sessionFactory.getCurrentSession().delete(shoppingCart);
    }
}
