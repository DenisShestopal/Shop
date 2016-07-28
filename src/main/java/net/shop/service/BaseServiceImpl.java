package net.shop.service;

import net.shop.dao.BaseDao;
import net.shop.dao.BaseDaoImpl;
import net.shop.dao.ProductDao;
import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseServiceImpl <T extends BaseEntity> implements BaseService<T> {

    private final Class<T> genericType = (Class<T>) GenericTypeResolver
            .resolveTypeArgument(getClass(), BaseServiceImpl.class);

    private BaseDao baseDao;

    public void setProductDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    @Transactional
    public T add(T entity) {
        this.baseDao.add(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        this.baseDao.update(entity);
    }

    @Override
    @Transactional
    public boolean remove(int id) {
        this.baseDao.remove(id);
    }

    @Override
    @Transactional
    public T getById(int id) {
        return this.baseDao.getById(id);
    }
}
