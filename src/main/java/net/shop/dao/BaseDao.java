package net.shop.dao;

import net.shop.model.BaseEntity;
import net.shop.model.Product;

public interface BaseDao<T extends BaseEntity> {

        void add(Product product);

        void update(Product product);

        void remove(int id);

        Product getById(int id);

}
