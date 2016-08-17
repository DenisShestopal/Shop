package net.shop.service.impl;

import net.shop.dao.BaseDao;
import net.shop.model.BaseEntity;
import net.shop.model.User;
import net.shop.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    public abstract BaseDao<T> getDao();

    @Override
    public T add(User loggedUser, T entity) {
        return getDao().add(entity);
    }

    @Override
    public T update(User loggedUser, T entity){
        return getDao().update(entity);
    }

    @Override
    public boolean remove(User loggedUser, Integer id) {
        return getDao().remove(id);
    }

    @Override
    public T getById(User loggedUser, Integer id) {
        return getDao().getById(id);
    }
}
