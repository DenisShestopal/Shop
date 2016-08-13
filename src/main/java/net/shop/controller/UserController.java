package net.shop.controller;

import lombok.Getter;
import net.shop.model.User;
import net.shop.model.mock.LoggedUserMock;
import net.shop.service.SecurityService;
import net.shop.service.UserService;
import net.shop.util.AuthenticateException;
import net.shop.util.AuthorizationException;
import net.shop.util.PermissionException;
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

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        //TODO get user by id and user's authority. if admin ? next : exception

        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        req.setAttribute("user", new User());
        req.setAttribute("listUsers", this.userService.listUsers());

        //return reference to the page "users"
        return "users";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest req, HttpServletResponse resp) {

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        //TODO get user by id and user's authority. if admin ? next : exception

        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        String strUserId = req.getParameter("id");
        this.userService.add(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) {

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }
        //TODO get user by id and user's authority. if admin ? next : exception

        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        String strUserId = req.getParameter("id");

        user.setId(Integer.valueOf(strUserId));
        this.userService.update(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(HttpServletRequest req, HttpServletResponse resp) {
        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        //TODO get user by id and user's authority. if admin ? next : exception
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        int userId = Integer.valueOf(req.getRequestURI().split("users/edit/")[1]);
        req.setAttribute("user", this.userService.getById(userId));
        req.setAttribute("listUsers", this.userService.listUsers());

        user.setId(Integer.valueOf(userId));
        this.userService.update(user);

        return "users";
    }

    @RequestMapping(value = "/blacklist", method = RequestMethod.GET)
    public String blackList(HttpServletRequest req, HttpServletResponse resp) {

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        //TODO get user by id and user's authority. if admin ? next : exception

        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")), Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        req.setAttribute("user", new User());
        req.setAttribute("listUsers", this.userService.listUnpaidUsers());

        //return reference to the page "products"
        return "users";
    }

    @RequestMapping(value = "/addtoblacklist/{id}", method = RequestMethod.GET)
    public String addUserToBlackList(HttpServletRequest request, HttpServletResponse response) throws PermissionException {

        try {
            getSecurityService().authenticate(request, response);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        //TODO get user by id and user's authority. if admin ? next : exception

        int userId = Integer.valueOf(request.getRequestURI().split("addtoblacklist/")[1]);

        User admin = new LoggedUserMock();

        getUserService().addUserToBlackList(admin, userId);

        return "redirect:/users";

    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(HttpServletRequest req, HttpServletResponse resp) {
        //TODO get user by id and user's authority. if admin ? next : exception

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        int userId = Integer.valueOf(req.getRequestURI().split("users/remove/")[1]);
        this.userService.remove(userId);

        return "redirect:/users";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String productData(HttpServletRequest req, HttpServletResponse resp) {
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
            req.setAttribute("exception", "LoginAlreadyUsed");
            return "authorization";
        }
        try {
            securityService.authorization(req, resp);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        return "redirect:/products";
    }

}
