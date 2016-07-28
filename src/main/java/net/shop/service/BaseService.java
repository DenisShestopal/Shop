package net.shop.service;

import net.shop.model.BaseEntity;
import net.shop.model.Product;

public interface BaseService<T extends BaseEntity> {

    public void add(Product product);

    public void update(Product product);

    public void remove(int id);

    public Product getById(int id);
}
