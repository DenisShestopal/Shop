package net.shop.dao;

import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Product product) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(product);
        logger.info("Product successfully saved. Product details: " + product);
    }

    @Override
    public void update(Product product) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(product);
        logger.info("Product successfully updated. Product details: " + product);
    }

    @Override
    public void remove(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Product product = (Product) session.load(Product.class, new Integer(id));

        if (product != null) {
            session.delete(product);
        }
        logger.info("Product successfully removed. Product details: " + product);

    }

    @Override
    public Product getById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Product product = (Product) session.load(Product.class, new Integer(id));
        logger.info("Product successfully loaded. Product details: " + product);

        return product;
    }
}