package net.shop.service;

import net.shop.dao.ProductDao;
import net.shop.dao.UserDao;
import net.shop.model.Product;
import net.shop.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService  {
    private UserDao userDao;

    public void setProductDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return this.userDao.listUsers();
    }
}
