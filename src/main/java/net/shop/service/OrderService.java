package net.shop.service;

import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.Product;
import net.shop.model.User;
import net.shop.util.PermissionException;

import java.util.List;


public interface OrderService extends BaseService<Order> {
    List<Order> listOrders(User loggedUser);

    boolean payOrder(User loggedUser, int orderId) throws PermissionException;

    boolean confirmOrder(User loggedUser, int orderId) throws PermissionException;

    List<Order> getOrderByUserIdAndStatus(User loggedUser, OrderStatus status);

    boolean changeQuantity(User loggedUser, Integer productId, Integer quantity);

    boolean removeProduct(User loggedUser, Integer orderId, Integer productId);
}
