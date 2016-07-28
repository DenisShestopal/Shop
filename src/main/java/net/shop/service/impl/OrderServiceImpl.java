package net.shop.service.impl;

import net.shop.dao.OrderDao;

import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.service.OrderService;
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

    public boolean confirmOrder(int orderId){
        Order order = orderDao.getById(orderId);
        order.setStatus(OrderStatus.ORDERED);
        orderDao.update(order);
        return true;
    }
    public boolean payOrder(int orderId){
        Order order = orderDao.getById(orderId);
        order.setStatus(OrderStatus.PAID);
        orderDao.update(order);
        return true;
    }
}
