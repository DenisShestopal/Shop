package net.shop.dao;

import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.springframework.stereotype.Repository;


public interface BaseDao<T extends BaseEntity> {

        T add(T entity);

        T update(T entity);

        boolean remove(int id);

        T getById(int id);//TODO change name to read

}
