package net.shop.service;

import net.shop.model.Order;

import java.util.List;


public interface OrderService extends BaseService<Order> {
    public List<Order> listOrders();
    public boolean confirmOrder(int orderId);
    public boolean payOrder(int orderId);

}
