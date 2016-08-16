package net.shop.service;


import net.shop.model.User;
import net.shop.model.UserDTO;
import net.shop.util.AuthenticateException;
import net.shop.util.PermissionException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends BaseService<User> {

    List<UserDTO> listUnpaidUsers(User loggedUser);

    List<UserDTO> listUsers(User loggedUser);

    boolean addUserToBlackList (User loggedUser, int userId) throws PermissionException;

    boolean removeUserFromBlackList(User loggedUser, int userId);

}
