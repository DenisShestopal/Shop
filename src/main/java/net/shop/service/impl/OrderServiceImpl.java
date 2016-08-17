package net.shop.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.BaseDao;
import net.shop.dao.OrderDao;

import net.shop.dao.ProductDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.Product;
import net.shop.model.User;
import net.shop.service.OrderService;
import net.shop.util.NoOrdersException;
import net.shop.util.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

    private OrderDao orderDao;
    private ProductDao productDao;

    @Autowired
    @Qualifier(value = "productDao")
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public BaseDao<Order> getDao() {
        return orderDao;
    }

    @Autowired
    @Qualifier(value = "orderDao")
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> listOrders(User loggedUser) {
        return this.orderDao.listOrders();
    }

    @Override
    public boolean confirmOrder(User loggedUser, int orderId) throws PermissionException {
        Order order = orderDao.getOrderByUserIdAndStatus(loggedUser.getId(), OrderStatus.UNORDERED).get(0);
        if (!order.getStatus().equals(OrderStatus.UNORDERED)) return false;
        order.setStatus(OrderStatus.ORDERED);
        orderDao.update(order);
        return true;
    }

    @Override
    public boolean payOrder(User user, int orderId) throws PermissionException {
        Order order = orderDao.getOrderByUserIdAndStatus(user.getId(), OrderStatus.ORDERED).get(0);
        if (!order.getStatus().equals(OrderStatus.ORDERED)) return false;
        order.setStatus(OrderStatus.PAID);
        orderDao.update(order);
        return true;
    }

    /**
     *
     * @param loggedUser
     * @param productId
     * @param quantity
     * @return true if quantity of products successfully changed
     */
    @Override
    public boolean changeQuantity(User loggedUser, Integer productId, Integer quantity) {

        Order order = orderDao.getOrderByUserIdAndStatus(loggedUser.getId(), OrderStatus.UNORDERED).get(0);

        Map<Product, Integer> products = order.getProductList();
        for (Product product : products.keySet()) {
            if (product.getId().equals(productId))
                products.put(product, quantity);
        }

        orderDao.update(order);
        return true;
    }

    /**
     *
     * @param loggedUser
     * @param status
     * @return user's orders list by order status
     */
    @Override
    public List<Order> getOrderByUserIdAndStatus(User loggedUser, OrderStatus status) {
        return orderDao.getOrderByUserIdAndStatus(loggedUser.getId(), status);
    }

    /**
     *
     * @param loggedUser
     * @param orderId
     * @param productId
     * @return true if product was successfully removed from order
     */
    @Override
    public boolean removeProduct(User loggedUser, Integer orderId, Integer productId) {

        List<Integer> ordersId = orderDao.listOrdersIdsByUser(loggedUser.getId());
        if (ordersId.contains(orderId)) {
            Order order = orderDao.getById(orderId);
            order.getProductList().remove(productDao.getById(productId));
            orderDao.update(order);
            return true;
        }
        return false;
    }

    /**
     *
     * @param loggedUser
     * @param orderId
     * @return true if all products were removed from order
     */
    @Override
    public boolean remove(User loggedUser, Integer orderId) {
        List<Integer> userOrdersId = orderDao.listOrdersIdsByUser(loggedUser.getId());
        if (userOrdersId.contains(orderId))
            return super.remove(loggedUser, orderId);
        throw new RuntimeException();//TODO add own exception implementation. PermissionDenied
    }

}
