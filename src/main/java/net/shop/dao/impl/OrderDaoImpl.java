package net.shop.dao.impl;

import net.shop.dao.OrderDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<Order> listOrders() {
        Session session = getSessionFactory().getCurrentSession();
        List<Order> orderList = session.createQuery("from Order").list();

        for (Order order : orderList) {
            logger.info("Orders list: " + order);
        }

        return orderList;
    }

    /**
     * look if comming order presents in users orders list
      * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> listOrdersIdsByUser(Integer userId) {
        Session session = getSessionFactory().getCurrentSession();

        return session.createQuery("select id from net.shop.model.Order o where o.owner.id = :userId")
                .setParameter("userId", userId)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getOrderByUserIdAndStatus(int userId, OrderStatus status) {
        Session session = getSessionFactory().getCurrentSession();

        return session.createQuery("from net.shop.model.Order o where o.owner.id = :userId and o.status = :status")
                .setParameter("userId", userId)
                .setParameter("status", status)
                .list();
    }


}

