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

    Order getOrderedOrderByUserId(User user);

    Order getPaidOrderByUserId(User user);

    List<Product> getOrdersProductsList(int orderId);

    boolean changeQuantity(User user, Integer productId, Integer quantity, String status);

    boolean removeProductFromUnorderedOrder(User user, Integer productId);

    boolean removeAllProductsFromUnorderedOrder(User user, Integer orderId);


}
