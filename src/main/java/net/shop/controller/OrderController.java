package net.shop.controller;

import lombok.Getter;
import net.shop.model.Order;
import net.shop.model.User;
import net.shop.model.mock.LoggedUserMock;
import net.shop.service.OrderService;
import net.shop.service.SecurityService;
import net.shop.service.UserService;
import net.shop.util.AuthenticateException;
import net.shop.util.AuthorizationException;
import net.shop.util.NoOrdersException;
import net.shop.util.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "orders")
@Getter
public class OrderController {


    private SecurityService securityService;
    private OrderService orderService;
    private UserService userService;

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

    @Autowired(required = true)
    @Qualifier(value = "orderService")
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String userOrder(HttpServletRequest req, HttpServletResponse resp){

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        req.setAttribute("order", new Order());
//        req.setAttribute("listOrders", this.orderService.listOrders());
        req.setAttribute("userOrder", orderService.getUnorderedOrderByUserId(loggedUser));

        return "orders";
    }

    @RequestMapping(value = "confirm/{orderId}")
    public String confirmOrder(HttpServletRequest request, HttpServletResponse response) throws NoOrdersException{

        User user = null;

        try {
            user = getSecurityService().authenticate(request, response);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        int orderId = Integer.valueOf(request.getRequestURI().split("orderId=")[1]);

        try {
            getOrderService().confirmOrder(user, orderId);
        } catch (PermissionException e) {
            request.setAttribute("exception", "You don't have access to this user's orders");
            return "orders";

        }
        return "redirect:/orders";
    }

    @RequestMapping(value = "pay/{orderId}")
    public String payOrder(HttpServletRequest request, HttpServletResponse response) throws NoOrdersException {

        User user = null;
        try {
            user = getSecurityService().authenticate(request, response);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        int orderId = Integer.valueOf(request.getRequestURI().split("orderId=")[1]);

        try {
            getOrderService().payOrder(user, orderId);
        } catch (PermissionException e) {
            request.setAttribute("exception", "You don't have access to this user's orders");
            return "orders";
        }
        return "redirect:/orders";
    }
}
