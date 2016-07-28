package net.shop.service.impl;

import net.shop.dao.UserDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.User;
import net.shop.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    private UserDao userDao;

    public void setProductDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<User> listUnpaidUsers() {
        List<User> usersList = userDao.listUsers();
        List<User> resultList = new ArrayList<>();
        for (User user : usersList) {
            for (Order order : user.getOrderList()) {
                if(order.getStatus().equals(OrderStatus.ORDERED)){
                    resultList.add(user);
                    break;
                }
            }
        }
        return resultList;
    }

}
