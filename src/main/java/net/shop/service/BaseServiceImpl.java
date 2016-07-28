package net.shop.service;

import net.shop.dao.BaseDao;
import net.shop.dao.ProductDao;
import net.shop.model.BaseEntity;
import net.shop.model.Product;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseServiceImpl <T extends BaseEntity> implements BaseService<T> {
    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public void add(Product product) {
        this.productDao.add(product);
    }

    @Override
    @Transactional
    public void update(Product product) {
        this.productDao.update(product);
    }

    @Override
    @Transactional
    public void remove(int id) {
        this.productDao.remove(id);
    }

    @Override
    @Transactional
    public Product getById(int id) {
        return this.productDao.getById(id);
    }
}
