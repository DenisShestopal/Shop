package net.shop.service;

import net.shop.dao.OrderDao;

import net.shop.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService{
    private OrderDao orderDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public List<Order> listOrders() {
        return this.orderDao.listOrders();
    }
}
