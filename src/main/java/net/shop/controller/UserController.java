package net.shop.controller;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.Getter;
import net.shop.model.User;
import net.shop.model.mock.LoggedUserMock;
import net.shop.service.SecurityService;
import net.shop.service.UserService;
import net.shop.util.AuthenticateException;
import net.shop.util.AuthorizationException;
import net.shop.util.PermissionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashSet;

@Controller
@RequestMapping(value = "users")
@Getter
public class UserController {

    private UserService userService;
    private SecurityService securityService;

    @Autowired(required = true)
    @Qualifier(value = "securityService")
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listUsers(HttpServletRequest req, HttpServletResponse resp) {

        User user = null;

        try {
            user = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        if (!user.getAdmin()) {
            req.setAttribute("exception", "Only admin can get the list of users");
            return "redirect:/products";
        }

        user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        req.setAttribute("user", new User());
        req.setAttribute("listUsers", this.userService.listUsers());

        return "users";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        if (!loggedUser.getAdmin()) {
            req.setAttribute("exception", "Only admin can add new user");
            return "redirect:/products";
        }

        User creatingUser = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")),
                Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());

        try {
            this.userService.add(creatingUser);
        } catch (ConstraintViolationException e) { //TODO ConstraintViolationException while adding existing user (login = unique field)
            req.setAttribute("exception", "User already exists");
            req.setAttribute("user", new User());
            req.setAttribute("listUsers", this.userService.listUsers());
            return "users";
        }

        return "redirect:/users";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        if (!loggedUser.getAdmin()) {
            req.setAttribute("exception", "Only admin can manage users");
            return "redirect:/products";
        }

        User updatingUser = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        String strUserId = req.getParameter("id");

        updatingUser.setId(Integer.valueOf(strUserId));
        this.userService.update(updatingUser);

        return "redirect:/users";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String getUserForEdit(HttpServletRequest req, HttpServletResponse resp) {
        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        if (!loggedUser.getAdmin()) {
            req.setAttribute("exception", "Only admin can manage users");
            return "redirect:/products";
        }

        User updatingUser = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        int userId = Integer.valueOf(req.getRequestURI().split("users/edit/")[1]);
        req.setAttribute("user", this.userService.getById(userId));
        req.setAttribute("listUsers", this.userService.listUsers());

//        updatingUser.setId(Integer.valueOf(userId));
//        this.userService.update(user);

        return "users";
    }

    @RequestMapping(value = "/blacklist", method = RequestMethod.GET)
    public String blackList(HttpServletRequest req, HttpServletResponse resp) {

        User user = null;

        try {
            user = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        if (!user.getAdmin()) {
            req.setAttribute("exception", "Only admin can see the blacklist");
            return "redirect:/products";
        }

        user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        req.setAttribute("user", new User());
        req.setAttribute("listUsers", this.userService.listUnpaidUsers());

        return "users";
    }

    @RequestMapping(value = "/addtoblacklist/{id}", method = RequestMethod.GET)
    public String addUserToBlackList(HttpServletRequest request, HttpServletResponse response) throws PermissionException {

        User user = null;

        try {
            user = getSecurityService().authenticate(request, response);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        if (!user.getAdmin()) {
            request.setAttribute("exception", "Only admin can add users to the blacklist");
            return "redirect:/products";
        }

        int userId = Integer.valueOf(request.getRequestURI().split("addtoblacklist/")[1]);
        User admin = new LoggedUserMock();
        getUserService().addUserToBlackList(admin, userId);

        return "redirect:/users";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(HttpServletRequest req, HttpServletResponse resp) {

        User user = null;

        try {
            user = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        if (!user.getAdmin()) {
            req.setAttribute("exception", "Only admin can manage users");
            return "redirect:/products";
        }

        int userId = Integer.valueOf(req.getRequestURI().split("users/remove/")[1]);
        this.userService.remove(userId);

        return "redirect:/users";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String productData(HttpServletRequest req, HttpServletResponse resp) {

        User user = null;

        try {
            user = securityService.authorization(req, resp);
        } catch (AuthorizationException e) {
            req.setAttribute("authorizationException", "Please get authorized first");
            return "authorization";
        }

        if (!user.getAdmin()) {
            req.setAttribute("exception", "Only admin can see users data");
            return "redirect:/products";
        }

        int userId = Integer.valueOf(req.getRequestURI().split("users/")[1]);
        req.setAttribute("user", this.userService.getById(userId));

        return "userdata";
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String authorizationView(HttpServletRequest req, HttpServletResponse resp) {
        return "authorization";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String authorization(HttpServletRequest req, HttpServletResponse resp) {

        try {
            securityService.authorization(req, resp);
        } catch (AuthorizationException e) {
            req.setAttribute("authorizationException", "Wrong login or password");
            return "authorization";
        }

        return "redirect:/products";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registration(HttpServletRequest req, HttpServletResponse resp) {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = new User(login, password, false, false, new HashSet<>());

        try {
            userService.add(user);
        } catch (Exception e) {
            req.setAttribute("exception", "Login Already Used");
            return "authorization";
        }
        try {
            securityService.authorization(req, resp);
        } catch (AuthorizationException e) {
            req.setAttribute("exception", "Authorization failed");
        }
        return "redirect:/products";
    }

}
