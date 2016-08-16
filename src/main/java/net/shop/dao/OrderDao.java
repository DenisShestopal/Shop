package net.shop.dao;


import net.shop.model.Order;
import net.shop.model.OrderStatus;


import java.util.List;

public interface OrderDao extends BaseDao<Order> {

    List<Order> listOrders();

//    Order getOrderByUserId(int userId);

    List<Integer> listOrdersIdsByUser(Integer userId);

    List<Order> getOrderByUserIdAndStatus(int userId, OrderStatus status);

}
