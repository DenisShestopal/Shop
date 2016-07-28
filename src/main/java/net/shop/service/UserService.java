package net.shop.service;

import net.shop.model.User;

import java.util.List;

public interface UserService extends BaseService<User> {
    public List<User> listUsers();
}
