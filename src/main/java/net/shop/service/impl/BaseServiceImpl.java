package net.shop.service.impl;

import net.shop.dao.BaseDao;
import net.shop.model.BaseEntity;
import net.shop.service.BaseService;


public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private BaseDao<T> baseDao;

    public void setProductDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public T add(T entity) {
        return baseDao.add(entity);
    }

    @Override
    public T update(T entity) {
        return baseDao.update(entity);
    }

    @Override

    public boolean remove(int id) {
        return baseDao.remove(id);
    }

    @Override
    public T getById(int id) {
        return baseDao.getById(id);
    }
}
