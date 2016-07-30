package net.shop.service.impl;

import net.shop.dao.UserDao;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
import net.shop.model.User;
import net.shop.service.UserService;
import net.shop.util.AuthException;
import net.shop.util.LoggedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
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
                if(order.getStatus().equals(OrderStatus.ORDERED)){
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
            if (cookie.getName().equals(sId)){
                userSessionId = cookie.getValue();
            }
        }
        if(LoggedUserUtil.getSessionUserIdMap().containsKey(userSessionId)){
            return LoggedUserUtil.getSessionUserIdMap().get(userSessionId);
        }
        throw new AuthException();
    }

}
