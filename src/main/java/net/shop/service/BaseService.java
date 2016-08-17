package net.shop.service;

import net.shop.model.BaseEntity;
import net.shop.model.User;

public interface BaseService<T extends BaseEntity> {

    T add(User loggedUser, T entity);

    T update(User loggedUser, T entity);

    boolean remove(User loggedUser, Integer id);

    T getById(User loggedUser, Integer id);
}
