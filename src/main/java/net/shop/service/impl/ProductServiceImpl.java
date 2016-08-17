package net.shop.service.impl;


import net.shop.dao.BaseDao;
import net.shop.dao.OrderDao;
import net.shop.dao.ProductDao;
import net.shop.dao.UserDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.Product;
import net.shop.model.User;
import net.shop.service.ProductService;
import net.shop.util.NoOrdersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    private ProductDao productDao;
    private OrderDao orderDao;
    private UserDao userDao;

    @Autowired
    @Qualifier(value = "productDao")
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    @Qualifier(value = "orderDao")
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public BaseDao<Product> getDao() {
        return productDao;
    }

    @Override
    public Product add(User loggedUser, Product entity) {
        if(productDao.getProductByCode(entity.getCode()) != null) return null;
        return super.add(loggedUser, entity);
    }

    @Override
    public Product update(User loggedUser, Product entity) {
        if(productDao.getProductByCode(entity.getCode()) != null) return null;
        return super.update(loggedUser, entity);
    }

    @Override
    public List<Product> listProducts(User loggedUser) {
        return this.productDao.listProducts();
    }

    /**
     *
     * @param loggedUser
     * @param productId
     * @return true if product was successfully added to basket (unordered order)
     */
    @Override
    public boolean addToOrder(User loggedUser, int productId) {

        Product product = productDao.getById(productId);
        List<Order> orders = orderDao.getOrderByUserIdAndStatus(loggedUser.getId(), OrderStatus.UNORDERED);
        Order order;
        if (orders.size() == 0) {
            order = new Order(OrderStatus.UNORDERED, loggedUser, new HashMap<>());
        } else order = orders.get(0);

        if (!order.getProductList().containsKey(product))
            order.getProductList().put(product, 1);

        if (order.isNew())
            orderDao.add(order);
        else
            orderDao.update(order);

        return true;

    }
}
