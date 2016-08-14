package net.shop.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.BaseDao;
import net.shop.dao.OrderDao;

import net.shop.dao.ProductDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.Product;
import net.shop.model.User;
import net.shop.service.OrderService;
import net.shop.util.NoOrdersException;
import net.shop.util.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

    private OrderDao orderDao;
    private ProductDao productDao;

    public ProductDao getProductDao() {
        return productDao;
    }

    @Override
    public BaseDao<Order> getDao() {
        return orderDao;
    }

    @Autowired
    @Qualifier(value = "orderDao")
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> listOrders() {
        return this.orderDao.listOrders();
    }

    @Override
    public boolean confirmOrder(User user, int orderId) throws PermissionException {
        Order order = orderDao.getUnorderedOrderByUserId(user.getId());

        order.setStatus(OrderStatus.ORDERED);
        orderDao.update(order);
        return true;
    }

    @Override
    public boolean payOrder(User user, int orderId) throws PermissionException {
        Order order = orderDao.getOrderedOrderByUserId(user.getId());

        order.setStatus(OrderStatus.PAID);
        orderDao.update(order);
        return true;
    }

    @Override
    public boolean changeQuantity(User user, Integer productId, Integer quantity) {
        Order order = orderDao.getUnorderedOrderByUserId(user.getId());

        Map<Product, Integer> products = order.getProductList();
        for (Product product : products.keySet()) {
            if(product.getId().equals(productId))
                products.put(product, quantity);
        }

        orderDao.update(order);
        return true;
    }

    //TODO CONFIRMED? add a method returning a products list of this order
    public List<Product> getOrdersProductsList(int orderId) {
        return getProductDao().listProducts();
    }


    @Override
    public Order getUnorderedOrderByUserId(User user) {
        return orderDao.getUnorderedOrderByUserId(user.getId());
    }

    @Override
    public Order getOrderedOrderByUserId(User user) {
        return orderDao.getOrderedOrderByUserId(user.getId());
    }

    @Override
    public Order getPaidOrderByUserId(User user) {
        return orderDao.getPaidOrderByUserId(user.getId());
    }
}
