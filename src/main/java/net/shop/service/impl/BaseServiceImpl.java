package net.shop.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.BaseDao;
import net.shop.model.BaseEntity;
import net.shop.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    //@Autowired
    private BaseDao<T> baseDao;

    @Autowired(required = true)
    //@Qualifier(value = "baseDao")
    public void setBaseDao(BaseDao baseDao) {
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
