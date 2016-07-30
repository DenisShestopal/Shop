package net.shop.dao;


import net.shop.model.Order;
import net.shop.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderDao extends BaseDao<Order>{

    List<Order> listOrders();
}
