package net.shop.dao;

import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;


public interface BaseDao<T extends BaseEntity> {

    void setSessionFactory(SessionFactory sessionFactory);

    T add(T entity);

    T update(T entity);

    boolean remove(int id);

    T getById(int id);//TODO change name to read

}
