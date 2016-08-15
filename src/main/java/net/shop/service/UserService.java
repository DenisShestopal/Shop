package net.shop.service;


import net.shop.model.User;
import net.shop.util.AuthenticateException;
import net.shop.util.PermissionException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends BaseService<User> {
    List<User> listUnpaidUsers();

    List<User> listUsers();

    boolean addUserToBlackList (User loggedUser, int userId);

}
