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
import java.util.Set;

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
    public boolean addToOrder(int userId, int productId) {
        User user = userDao.getById(userId);
        Product product = productDao.getById(productId);

        Order order = orderDao.getById(1);//TODO look if user has orders

        Set<Order> userOrderList = user.getOrderList();
        userOrderList.contains(order);

        boolean hasUnordered = false;
        if (!order.getStatus().equals(OrderStatus.UNORDERED))
            for (Order iterOrder : userOrderList) {
                if (iterOrder.getStatus().equals(OrderStatus.UNORDERED)) {
                    hasUnordered = true;
                    order = iterOrder;
                }
            }

        if (!hasUnordered)
            order = new Order(OrderStatus.UNORDERED, user, new HashMap<>());

        order.getProductList().put(product, 1);//should we put here a product to the orders table as a new "order's product"?

        orderDao.update(order);
        return true;

    }
}
