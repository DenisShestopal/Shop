package net.shop.dao;


import net.shop.model.Order;
import net.shop.model.User;

import java.util.List;

public interface OrderDao extends BaseDao<Order>{

    List<Order> listOrders();
}
