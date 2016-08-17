package net.shop.dao.impl;

import net.shop.dao.BaseDao;
import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

public abstract class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    //get type of T by reflection
    @SuppressWarnings("unchecked")
    private final Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseDaoImpl.class);

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    public abstract SessionFactory getSessionFactory();


    @Override
    public T add(T entity) {
        Session session = getSessionFactory().getCurrentSession();
        session.persist(entity);
        logger.info("Entity successfully saved. Entity details: " + entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        Session session = getSessionFactory().getCurrentSession();
        session.update(entity);
        logger.info("Entity successfully updated. Entity details: " + entity);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(int id) {
        Session session = getSessionFactory().getCurrentSession();
        T entity = (T) session.load(genericType, id);

        if (entity != null) {
            session.delete(entity);
            logger.info("Entity successfully removed. Entity details: " + entity);
            return true;
        }
        logger.info("Entity wasn't removed. Entity not found: " + entity);
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getById(int id) {
        Session session = getSessionFactory().getCurrentSession();
        T entity = (T) session.load(genericType, id);
        logger.info("Entity successfully loaded. Entity details: " + entity);

        return entity;
    }
}