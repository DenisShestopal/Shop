package net.shop.dao.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.BaseDao;
import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    //get type of T by reflection
    @SuppressWarnings("unchecked")
    private final Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseDaoImpl.class);

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T add(T entity) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(entity);
        logger.info("Product successfully saved. Product details: " + entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(entity);
        logger.info("Product successfully updated. Product details: " + entity);
        return entity;
    }

    @Override
    public boolean remove(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Product product = (Product) session.load(Product.class, new Integer(id));

        if (product != null) {
            session.delete(product);
            logger.info("Product successfully removed. Product details: " + product);
            return true;
        }
        logger.info("Product wasn't removed. Product not found: " + product);
        return false;
    }

    @Override
    public T getById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        T entity = (T) session.load(genericType, new Integer(id));
        logger.info("Product successfully loaded. Product details: " + entity);

        return entity;
    }
}