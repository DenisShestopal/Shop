package net.shop.controller;

import lombok.Getter;
import net.shop.service.SecurityService;
import net.shop.service.UserService;
import net.shop.util.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Deniska on 8/12/2016.
 */
@Controller
@RequestMapping(value = "authorization")
@Getter
public class SecurityController {

    private SecurityService securityService;
    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String userAuthorization(HttpServletRequest req, HttpServletResponse resp) throws AuthorizationException {
        String password = req.getParameter("password");

        req.setAttribute("user", getSecurityService().authorization(req, resp));
        return "redirect:/products";
    }
}
