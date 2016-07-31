package net.shop.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.shop.dao.UserDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.User;
import net.shop.service.UserService;
import net.shop.util.AuthException;
import net.shop.util.LoggedUserUtil;
import net.shop.util.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    //@Autowired
    private UserDao userDao;

    @Autowired(required = true)
    //@Qualifier(value = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
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

    public int getUserIdFromRequest(HttpServletRequest request) throws AuthException {
        Cookie[] cookies = request.getCookies();
        String sId = "J_SESSION_ID";
        String userSessionId = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(sId)) {
                userSessionId = cookie.getValue();
            }
        }
        if (LoggedUserUtil.getSessionUserIdMap().containsKey(userSessionId)) {
            return LoggedUserUtil.getSessionUserIdMap().get(userSessionId);
        }
        throw new AuthException();
    }

    public List<User> listUsers() {
        return this.userDao.listUsers();
    }

    public boolean addUserToBlackList(User loggedUser, int userId) throws PermissionException {
        if (loggedUser.getAdmin()) {
            //TODO validate if user has permission if loggedUser ? next : exception
            User user = userDao.getById(userId);
            user.setBlocked(true);
            userDao.update(user);
            return true;
        } else {
            throw new PermissionException();
        }
    }

}
