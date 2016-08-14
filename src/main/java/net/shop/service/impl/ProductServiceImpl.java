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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Product> listProducts() {
        return this.productDao.listProducts();
    }

    @Override
    public boolean addToOrder(User user, int productId) {

        Product product = productDao.getById(productId);
        int userId = user.getId();

        Order order = orderDao.getUnorderedOrderByUserId(userId);

//        Order order = null;
//        for (Order iterOrder : order) {
//            if (iterOrder.getStatus().equals(OrderStatus.UNORDERED)) {
//                order = iterOrder;
//            }
//        }

        if (order == null) {
            order = new Order(OrderStatus.UNORDERED, user, new HashMap<>());
        }

        if (!order.getProductList().containsKey(product))
            order.getProductList().put(product, 1);

        if (order.isNew())
            orderDao.add(order);
        else
            orderDao.update(order);

        return true;

    }
}
