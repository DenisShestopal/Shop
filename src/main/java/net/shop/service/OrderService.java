package net.shop.service;

import net.shop.model.Order;
import net.shop.model.Product;
import net.shop.model.User;
import net.shop.util.PermissionException;

import java.util.List;


public interface OrderService extends BaseService<Order> {
    List<Order> listOrders();

    boolean payOrder(User user, int orderId) throws PermissionException;

    boolean confirmOrder(User user, int orderId) throws PermissionException;

    Order getUnorderedOrderByUserId(User user);

    List<Product> getOrdersProductsList(int orderId);


}
