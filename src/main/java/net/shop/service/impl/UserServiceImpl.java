package net.shop.service.impl;

import net.shop.dao.BaseDao;
import net.shop.dao.UserDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.User;
import net.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserDao userDao;

    @Override
    public User add(User entity) {
        if(userDao.getUserByLogin(entity.getLogin()) != null) return null;
        entity.setPassword(Base64.getEncoder()
                .encodeToString((entity.getLogin() + ":" + entity.getPassword()).getBytes()));
        return super.add(entity);
    }

    @Autowired(required = true)
    @Qualifier(value = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public BaseDao<User> getDao() {
        return userDao;
    }

    @Override
    public List<User> listUnpaidUsers() {
        List<User> usersList = userDao.listUsers();
        List<User> resultList = new ArrayList<>();
        for (User user : usersList) {
            for (Order order : user.getOrderList()) {
                if (order.getStatus().equals(OrderStatus.ORDERED)) {
                    resultList.add(user);
                    break;
                }
            }
        }
        return resultList;
    }

    public List<User> listUsers() {
        return this.userDao.listUsers();
    }

    @Override
    public boolean addUserToBlackList(User loggedUser, int userId){
        if (loggedUser.getAdmin()) {
            User user = userDao.getById(userId);
            if (user.getBlocked()) {
                user.setBlocked(false);
            } else {
                user.setBlocked(true);
            }
            userDao.update(user);

        }
        return true;
    }

}
