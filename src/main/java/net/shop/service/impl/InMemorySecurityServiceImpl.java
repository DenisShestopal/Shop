package net.shop.service.impl;

import net.shop.dao.UserDao;
import net.shop.model.User;
import net.shop.service.SecurityService;
import net.shop.util.AuthenticateException;
import net.shop.util.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class InMemorySecurityServiceImpl implements SecurityService {


    private UserDao userDao;

    private Map<String, Integer> usersTokenMap = new ConcurrentHashMap<>();

    @Autowired(required = true)
    @Qualifier(value = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

//    @Autowired
//    @Qualifier(value = "usersTokenMap")
//    public void setUsersTokenMap(Map<String, Integer> usersTokenMap) {
//        this.usersTokenMap = usersTokenMap;
//    }

    @Override
    public User authenticate(HttpServletRequest req, HttpServletResponse resp) throws AuthenticateException {

        if(req.getCookies()==null)
            throw new AuthenticateException();

        Cookie[] cookies = req.getCookies();

        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie == null)
                throw new AuthenticateException();
            if (cookie.getName().equals(TOKEN))
                if (usersTokenMap.containsKey(cookie.getValue()))
                    user = userDao.getById(Integer.valueOf(cookie.getValue()));
        }
        if (user == null)
            throw new AuthenticateException();
        return user;
    }

    @Override
    public User authorization(HttpServletRequest req, HttpServletResponse resp) throws AuthorizationException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userDao.getUserByLogin(login);
        password = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());

        if(!user.getPassword().equals(password))
            throw new AuthorizationException("Login or/and password are incorrect");

        String token = UUID.randomUUID().toString();
        usersTokenMap.put(token, user.getId());
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setPath("/");
        resp.addCookie(cookie);
        return user;
    }
}
