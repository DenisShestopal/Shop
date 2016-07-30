package net.shop.service;

import net.shop.model.Product;

import java.util.List;


public interface ProductService extends BaseService<Product>{

    List<Product> listProducts();
}
