package net.shop.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.BaseDao;
import net.shop.dao.OrderDao;

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


@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

    private OrderDao orderDao;

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
        Order order = orderDao.getById(orderId);
        if (!(order.getOwner().getId() == user.getId()) || !user.getAdmin()) {
            throw new PermissionException("User is not appropriative");
        }
        order.setStatus(OrderStatus.ORDERED);
        orderDao.update(order);
        return true;
    }

    @Override
    public boolean payOrder(User user, int orderId) throws PermissionException {
        Order order = orderDao.getById(orderId);
        if (!(order.getOwner().getId() == user.getId()) || !user.getAdmin()) {
            throw new PermissionException("User is not appropriative");
        }
        order.setStatus(OrderStatus.PAID);
        orderDao.update(order);
        return true;
    }

    //TODO CONFIRMED? add a method returning a products list of this order
//    public List<Product> getOrdersProductsList(int orderId){
//
//    }

}
