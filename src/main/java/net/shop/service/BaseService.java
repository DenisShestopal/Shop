package net.shop.service;

import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.springframework.stereotype.Service;

public interface BaseService<T extends BaseEntity> {

    public T add(T entity);

    public T update(T entity);

    public boolean remove(int id);

    public T getById(int id);
}
