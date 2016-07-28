package net.shop.dao;

import net.shop.model.Product;

import java.util.List;

/**
 * Created by Deniska on 7/27/2016.
 */
public interface ProductDao {

    public void addProduct(Product product);

    public void updateProduct(Product product);

    public void removeProduct(int id);

    public Product getProductById(int id);

    public List<Product> listProducts();

}
