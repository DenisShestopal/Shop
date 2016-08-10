package net.shop.dao;

import net.shop.model.User;
import net.shop.util.AuthorizationException;

import java.util.List;


public interface UserDao extends BaseDao<User> {
    List<User> listUsers();

    User getUserByLogin(String login) throws AuthorizationException;
}