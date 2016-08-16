package net.shop.service;

import net.shop.model.BaseEntity;
import net.shop.model.Product;
import net.shop.model.User;
import org.springframework.stereotype.Service;

public interface BaseService<T extends BaseEntity> {

    T add(User loggedUser, T entity);

    T update(User loggedUser, T entity);

    boolean remove(User loggedUser, Integer id);

    T getById(User loggedUser, Integer id);
}
