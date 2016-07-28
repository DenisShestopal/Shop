package net.shop.dao;

import net.shop.model.Product;

import java.util.List;


public interface ProductDao extends BaseDao<Product> {

    List<Product> listProducts();

}
