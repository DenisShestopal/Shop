package net.shop.service.impl;

import net.shop.dao.UserDao;
import net.shop.model.User;
import net.shop.service.SecurityService;
import net.shop.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemorySecurityServiceImpl implements SecurityService {

    private UserDao userDao;

    private Map<String, Integer> usersTokenMap = new ConcurrentHashMap<>();

    @Autowired(required = true)
    @Qualifier(value = "userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     *
     * @param req
     * @param resp
     * @return userDTO if success or throws exception
     * @throws AuthenticateException
     */
    @Override
    @Transactional
    public User authenticate(HttpServletRequest req, HttpServletResponse resp) throws AuthenticateException {

        if (req.getCookies() == null) {
            req.setAttribute("exception", "Not authorized yet");
            throw new AuthenticateException();
        }

        Cookie[] cookies = req.getCookies();

        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TOKEN))
                if (usersTokenMap.containsKey(cookie.getValue()))
                    user = userDao.getById(usersTokenMap.get(cookie.getValue()));
        }
        if (user == null) {
            req.setAttribute("exception", "Please get authorized!");
            throw new AuthenticateException();
        }


        Hello.userLogin = user.getLogin();
        //erase credentials after authentication fo security
        return UserUtils.getShallowCloneWithoutSecureData(user, new User());
    }

    /**
     *
     * @param req
     * @param resp
     * @return userDTO if success or throws exception
     * @throws AuthorizationException
     */
    @Override
    @Transactional
    public User authorization(HttpServletRequest req, HttpServletResponse resp) throws AuthorizationException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userDao.getUserByLogin(login);
        password = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());

        if ((user == null)||(!user.getPassword().equals(password))) {
            req.setAttribute("exception", "Login or/and password are incorrect");
            throw new AuthorizationException("Login or/and password are incorrect");
        }

        if (user.getBlocked())
            throw new AuthorizationException("Your account is blocked. Please contact administration.");

        String token = UUID.randomUUID().toString();
        usersTokenMap.put(token, user.getId());
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setPath("/");
        resp.addCookie(cookie);
        return UserUtils.getShallowCloneWithoutSecureData(user, new User());
    }

    /**
     *
     * @param req
     * @param resp
     * @param user
     * @return true if success
     */
    @Override
    public boolean logout(HttpServletRequest req, HttpServletResponse resp, User user) {
        Cookie[] cookies = req.getCookies();
        String userToken = "";
        for (Cookie cookie : cookies)
            if (cookie.getName().equals(TOKEN)) userToken = cookie.getValue();
        usersTokenMap.remove(userToken);
        Cookie cookie = new Cookie(TOKEN, "deleted");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        return true;
    }

//    /**
//     *
//     * @param user
//     * @return userDTO without password for security thoughts
//     */
//    private static User getShallowCloneWithoutSecureData(User user) {
//        User result = new User();
//        result.setId(user.getId());
//        result.setAdmin(user.getAdmin());
//        result.setBlocked(user.getBlocked());
//        result.setLogin(user.getLogin());
//        return result;
//    }

    /**
     *
     * @param loggedUser
     * @param user
     * @return true if user's tokens was successfully removed from Map
     * @throws PermissionException
     */
    @Override
    public boolean deleteUserTokens(User loggedUser, User user) throws PermissionException {
        if (loggedUser.getAdmin()) {
            for (Map.Entry<String, Integer> entry : usersTokenMap.entrySet()) {
                if (user.getId().equals(entry.getValue())) {
                    usersTokenMap.remove(entry.getKey());
                }
            }
        } else throw new PermissionException();
        return true;
    }

}
