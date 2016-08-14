package net.shop.controller;

import lombok.Getter;
import net.shop.model.Order;
import net.shop.model.User;
import net.shop.service.OrderService;
import net.shop.service.SecurityService;
import net.shop.service.UserService;
import net.shop.util.AuthenticateException;
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
//@RequestMapping(value = "orders")
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

    @RequestMapping(value = "unordered", method = RequestMethod.GET)
    public String unorderedOrders(HttpServletRequest req, HttpServletResponse resp){

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        req.setAttribute("order", new Order());
        req.setAttribute("userOrder", orderService.getUnorderedOrderByUserId(loggedUser));

        return "unordered";
    }

    @RequestMapping(value = "ordered", method = RequestMethod.GET)
    public String orderedOrders(HttpServletRequest req, HttpServletResponse resp){

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        req.setAttribute("order", new Order());
        req.setAttribute("userOrder", orderService.getOrderedOrderByUserId(loggedUser));

        return "ordered";
    }

    @RequestMapping(value = "paid", method = RequestMethod.GET)
    public String paidOrders(HttpServletRequest req, HttpServletResponse resp){

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        req.setAttribute("order", new Order());
        req.setAttribute("userOrder", orderService.getPaidOrderByUserId(loggedUser));

        return "paid";
    }

    @RequestMapping(value = "unordered/confirm/{orderId}", method = RequestMethod.GET)
    public String confirmOrder(HttpServletRequest req, HttpServletResponse resp) throws NoOrdersException{

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        int orderId = Integer.valueOf(req.getRequestURI().split("unordered/confirm/")[1]);

        try {
            getOrderService().confirmOrder(loggedUser, orderId);
        } catch (PermissionException e) {
            req.setAttribute("exception", "You don't have access to this user's orders");
            return "unordered";

        }
//        req.setAttribute("order", new Order());
//        req.setAttribute("userOrder", orderService.getOrderedOrderByUserId(loggedUser));
        return "redirect:/ordered";
    }

    @RequestMapping(value = "ordered/pay/{orderId}", method = RequestMethod.GET)
    public String payOrder(HttpServletRequest req, HttpServletResponse resp) throws NoOrdersException {

        User loggedUser = null;
        try {
            loggedUser = getSecurityService().authenticate(req, resp);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }

        int orderId = Integer.valueOf(req.getRequestURI().split("ordered/pay/")[1]);

        try {
            getOrderService().payOrder(loggedUser, orderId);
        } catch (PermissionException e) {
            req.setAttribute("exception", "You don't have access to this user's orders");
            return "unordered";
        }

        return "redirect:/paid";
    }

    @RequestMapping(value = "/unordered/changeQuantity", method = RequestMethod.POST)
    public String getQuantityFromUnordered(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);//TODO Check authorization if correct
        } catch (AuthenticateException e) {
            return "authorization";
        }
        Integer quantity = Integer.parseInt(req.getAttribute("quantity").toString());
        Integer productId = Integer.parseInt(req.getAttribute("productId").toString());

        orderService.changeQuantity(loggedUser, productId, quantity);

        return "redirect:/unordered";
    }
}
