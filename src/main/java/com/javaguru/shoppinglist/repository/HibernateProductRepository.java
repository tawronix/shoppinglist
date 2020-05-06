package com.javaguru.shoppinglist.repository;

import com.javaguru.shoppinglist.domain.Product;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Profile({"hibernate", "default"})
public class HibernateProductRepository implements ProductRepository {
    private final SessionFactory sessionFactory;

    public HibernateProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Product.class, id));
    }

    public Optional<Product> getByName(String name) {
        return Optional.ofNullable(
                sessionFactory.getCurrentSession().createQuery(
                        "SELECT p FROM Product p WHERE p.name like :name", Product.class)
                        .setParameter("name", name)
                        .uniqueResult()
        );
    }

    @Override
    public List<Product> getAll() {
        return sessionFactory.getCurrentSession().createQuery(
                "SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    @Override
    public Long save(Product product) {
        return (Long) sessionFactory.getCurrentSession().save(product);
    }

    @Override
    public void delete(Product product) {
        sessionFactory.getCurrentSession().delete(product);
    }
}
