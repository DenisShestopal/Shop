package net.shop.controller;

import lombok.Getter;
import lombok.Setter;
import net.shop.model.User;
import net.shop.service.UserService;
import net.shop.util.AuthException;
import net.shop.util.LoggedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.SecondaryTable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
//@RequestMapping(value = "users")
@Getter
@Setter
public class UserController {

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("user") User user){
        this.userService.add(user);
        return "redirect:/users";
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String listUsers(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", this.userService.listUsers());

        //return reference to the page "users"
        return "users";
    }

    @RequestMapping(value = "users/blacklist", method = RequestMethod.GET)
    public String blackList(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", this.userService.listUnpaidUsers());

        //return reference to the page "products"
        return "users";
    }

    public boolean addUserToBlackList(HttpServletRequest request, HttpServletResponse response) throws AuthException {
        int loggedUserId = userService.getUserIdFromRequest(request);
        User loggedUser = userService.getById(loggedUserId);
        //TODO validate if user has permission if admin ? next : exception
        String[] strUri = request.getRequestURI().split("userId=");
        int userId = Integer.valueOf(strUri[1]);
        User user = userService.getById(userId);
        user.setBlocked(true);
        userService.update(user);
        return true;
    }
}
