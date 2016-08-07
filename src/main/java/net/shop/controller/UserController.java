package net.shop.controller;

import lombok.Getter;
import lombok.Setter;
import net.shop.model.User;
import net.shop.model.mock.LoggedUserMock;
import net.shop.service.UserService;
import net.shop.util.AuthException;
import net.shop.util.LoggedUserUtil;
import net.shop.util.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.SecondaryTable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@Controller
@RequestMapping(value = "users")
@Getter
public class UserController {

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


//    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
//    public String add(@ModelAttribute("user") User user) {
//        this.userService.add(user);
//        return "redirect:/users";
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String listUsers(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")),Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        req.setAttribute("user", new User());
        req.setAttribute("listUsers", this.userService.listUsers());

        //return reference to the page "users"
        return "users";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")),Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        String strUserId = req.getParameter("id");
        //TODO divide operations to 'add' + 'update'. Relocate update operation invocation to "/edit" controller.
//        if (strUserId == null) {
            this.userService.add(user);
//        } else {
//            user.setId(Integer.valueOf(strUserId));
//            this.userService.update(user);
//        }

        return "redirect:/users";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")),Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        String strUserId = req.getParameter("id");

            user.setId(Integer.valueOf(strUserId));
            this.userService.update(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    //TODO get user by id and user's authority. if admin ? next : exception
    public String edit(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")),Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        int userId = Integer.valueOf(req.getRequestURI().split("users/edit/")[1]);
        //String strUserId = req.getParameter("id");
        req.setAttribute("user", this.userService.getById(userId));
        req.setAttribute("listUsers", this.userService.listUsers());

        user.setId(Integer.valueOf(userId));
            this.userService.update(user);

        return "users";
    }

    @RequestMapping(value = "/blacklist", method = RequestMethod.GET)
    public String blackList(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(req.getParameter("login"), req.getParameter("password"),
                Boolean.parseBoolean(req.getParameter("admin")),Boolean.parseBoolean(req.getParameter("blocked")), new HashSet<>());
        req.setAttribute("user", new User());
        req.setAttribute("listUsers", this.userService.listUnpaidUsers());

        //return reference to the page "products"
        return "users";
    }

    @RequestMapping(value = "/addtoblacklist/{id}", method = RequestMethod.GET)
    public String addUserToBlackList(HttpServletRequest request, HttpServletResponse response) throws AuthException, PermissionException {
//        int loggedUserId = userService.getUserIdFromRequest(request);
//        User loggedUser = userService.getById(loggedUserId);
        int userId = Integer.valueOf(request.getRequestURI().split("addtoblacklist/")[1]);
//        String[] strUri = request.getRequestURI().split("userId=");
//        int userId = Integer.valueOf(strUri[1]);
        User admin = new LoggedUserMock();

        getUserService().addUserToBlackList(admin, userId);

        return "redirect:/users";

    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(HttpServletRequest req, HttpServletResponse resp) {
        //TODO get user by id and user's authority. if admin ? next : exception
        int userId = Integer.valueOf(req.getRequestURI().split("users/remove/")[1]);
        this.userService.remove(userId);

        return "redirect:/users";
    }

    @RequestMapping("/{id}")
    public String productData(HttpServletRequest req, HttpServletResponse resp) {
        int userId = Integer.valueOf(req.getRequestURI().split("users/")[1]);
        req.setAttribute("user", this.userService.getById(userId));

        return "userdata";
    }
}
