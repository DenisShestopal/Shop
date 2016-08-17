package net.shop.service.impl;

import net.shop.dao.BaseDao;
import net.shop.dao.UserDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.User;
import net.shop.model.UserDTO;
import net.shop.service.SecurityService;
import net.shop.service.UserService;
import net.shop.util.PermissionException;
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
    private SecurityService securityService;

    @Autowired(required = true)
    @Qualifier(value = "securityService")
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
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

    /**
     * adds user with checking if already exists
     * @param loggedUser
     * @param entity
     * @return
     */
    @Override
    public User add(User loggedUser, User entity) {
        if (userDao.getUserByLogin(entity.getLogin()) != null) return null;
        entity.setPassword(Base64.getEncoder()
                .encodeToString((entity.getLogin() + ":" + entity.getPassword()).getBytes()));
        return super.add(loggedUser, entity);
    }

    /**
     * updates user with checking if another user with such login already exists
     * @param loggedUser
     * @param entity
     * @return
     */
    @Override
    public User update(User loggedUser, User entity) {
        if (userDao.getUserByLogin(entity.getLogin()) != null) return null;
        return super.update(loggedUser, entity);
    }

    /**
     * working with userDTO, need to be realized correctly
     * @param loggedUser
     * @return
     */
    @Override
    public List<UserDTO> listUnpaidUsers(User loggedUser) {
//        List<User> usersList = userDao.listUsers();
//        List<User> resultList = new ArrayList<>();
//        for (User user : usersList) {
//            for (Order order : user.getOrderList()) {
//                if (order.getStatus().equals(OrderStatus.ORDERED)) {
//                    resultList.add(user);
//                    break;
//                }
//            }
//        }
        return new ArrayList<>();//resultList;
    }

    /**
     * working with userDTO, need to add isAdmin verification
     * @param loggedUser
     * @return
     */
    @Override
    public List<UserDTO> listUsers(User loggedUser) {
        //TODO check isAdmin
        return this.userDao.listUsers();
    }

    @Override
    public boolean addUserToBlackList(User loggedUser, int userId) throws PermissionException {
        if (loggedUser.getAdmin()) {
            User user = userDao.getById(userId);
            user.setBlocked(true);
            userDao.update(user);
            securityService.deleteUserTokens(loggedUser, user);
        }
        return true;
    }

    @Override
    public boolean removeUserFromBlackList(User loggedUser, int userId) {
        if (loggedUser.getAdmin()) {
            User user = userDao.getById(userId);
            user.setBlocked(false);
            userDao.update(user);
        }
        return true;
    }

/**
 * need to be realized. returns a list of users which have confirmed but unpaid orders
  */
// @Override
//    public List<Boolean> listUsersWithOrderedAndUnpaid(User loggedUser, List<UserDTO> usersList) {
//        List<Boolean> boolList = null;
//        boolean[] hasOrdered = null;
//        boolean[] hasPaid = null;
//        int i = 0;
//        for (UserDTO user : usersList) {
//            for (Order order : user.getOrderList()) {
//                if (order.getStatus().equals(OrderStatus.ORDERED)) {
//                    hasOrdered[i] = true;
//                    i += 1;
//                }
//                if (order.getStatus().equals(OrderStatus.PAID)) {
//                    hasPaid[i] = true;
//                    i += 1;
//                }
//            }
//        }
//
//        for (int j = 0; i < hasOrdered.length; i++) {
//            if (hasOrdered[j] && !hasPaid[j])
//                boolList.add(true);
//            else
//                boolList.add(false);
//        }
//        return boolList;
//    }

}
