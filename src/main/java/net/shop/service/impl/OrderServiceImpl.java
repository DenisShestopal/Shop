package net.shop.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.OrderDao;

import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.User;
import net.shop.service.OrderService;
import net.shop.util.NoOrdersException;
import net.shop.util.PermissionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
    private OrderDao orderDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public List<Order> listOrders() {
        return this.orderDao.listOrders();
    }

    public boolean confirmOrder(User user, int orderId) throws PermissionException {
        Order order = orderDao.getById(orderId);
        if (!(order.getOwner().getId() == user.getId()) || !user.getAdmin()) {
            throw new PermissionException("User is not appropriative");
        }
        order.setStatus(OrderStatus.ORDERED);
        orderDao.update(order);
        return true;
    }

    public boolean payOrder(User user, int orderId) throws PermissionException {
        Order order = orderDao.getById(orderId);
        if (!(order.getOwner().getId() == user.getId()) || !user.getAdmin()) {
            throw new PermissionException("User is not appropriative");
        }
        order.setStatus(OrderStatus.PAID);
        orderDao.update(order);
        return true;
    }

}
