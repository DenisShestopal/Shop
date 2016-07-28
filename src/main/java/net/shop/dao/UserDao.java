package net.shop.dao;

import net.shop.model.User;

import java.util.List;


public interface UserDao extends BaseDao<User> {
    List<User> listUsers();
}