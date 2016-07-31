package net.shop.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.BaseDao;
import net.shop.model.BaseEntity;
import net.shop.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    public abstract BaseDao<T> getDao();

    @Override
    public T add(T entity) {
        return getDao().add(entity);
    }

    @Override
    public T update(T entity) {
        return getDao().update(entity);
    }

    @Override

    public boolean remove(int id) {
        return getDao().remove(id);
    }

    @Override
    public T getById(int id) {
        return getDao().getById(id);
    }
}
