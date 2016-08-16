package net.shop.service;

import net.shop.model.Product;
import net.shop.model.User;
import net.shop.util.NoOrdersException;

import java.util.List;


public interface ProductService extends BaseService<Product> {

    List<Product> listProducts(User loggedUser);

    boolean addToOrder(User loggedUser, int productId);
}
