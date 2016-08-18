package net.shop.controller;

import lombok.Getter;
import net.shop.model.Order;
import net.shop.model.OrderStatus;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
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

    /**
     * @param req
     * @param resp
     * @return Page with unconfirmed user's orders
     */
    @RequestMapping(value = "unordered", method = RequestMethod.GET)
    public String unorderedOrders(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
            req.setAttribute("loggedUser", loggedUser.getLogin());
        } catch (AuthenticateException e) {
            return "authorization";
        }

        List<Order> orderList = orderService.getOrderByUserIdAndStatus(loggedUser, OrderStatus.UNORDERED);
        if (orderList.size() != 0) {
            req.setAttribute("userOrder", orderList.get(0));
            req.setAttribute("ordersList", orderList);
        } else
            req.setAttribute("userOrder", new Order());

        return "unordered";
    }

    /**
     * @param req
     * @param resp
     * @return Page with confirmed user's orders
     */
    @RequestMapping(value = "ordered", method = RequestMethod.GET)
    public String orderedOrders(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
            req.setAttribute("loggedUser", loggedUser.getLogin());
        } catch (AuthenticateException e) {
            return "authorization";
        }

        List<Order> orderList = orderService.getOrderByUserIdAndStatus(loggedUser, OrderStatus.ORDERED);
        if (orderList.size() != 0) {
            req.setAttribute("userOrder", orderList.get(0));
            req.setAttribute("ordersList", orderList);
        } else req.setAttribute("userOrder", new Order());
        req.setAttribute("order", new Order());
        return "ordered";
    }

    /**
     * @param req
     * @param resp
     * @return Page with paid user's orders
     */
    @RequestMapping(value = "paid", method = RequestMethod.GET)
    public String paidOrders(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
            req.setAttribute("loggedUser", loggedUser.getLogin());
        } catch (AuthenticateException e) {
            return "authorization";
        }

        List<Order> orderList = orderService.getOrderByUserIdAndStatus(loggedUser, OrderStatus.PAID);
        if (orderList.size() != 0)
            req.setAttribute("userOrder", orderList.get(orderList.size() - 1));
        else req.setAttribute("userOrder", new Order());
        req.setAttribute("order", new Order());
        return "paid";
    }

    /**
     * @param req
     * @param resp
     * @return Page with confirmed user's orders
     */
    @RequestMapping(value = "unordered/confirm/{orderId}", method = RequestMethod.GET)
    public String confirmOrder(@PathVariable("orderId") Integer orderId,
                               HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        try {
            if (!(getOrderService().confirmOrder(loggedUser, orderId))) {
                req.setAttribute("exception", "You can not confirm confirmed or paid order");
                return "unordered";
            }
        } catch (PermissionException e) {
            req.setAttribute("exception", "You don't have access to this user's orders");
            return "unordered";

        }
        return "redirect:/ordered";
    }

    @RequestMapping(value = "ordered/pay/{orderId}", method = RequestMethod.GET)
    public String payOrder(@PathVariable("orderId") Integer orderId,
                           HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;
        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        try {
            if (!(getOrderService().payOrder(loggedUser, orderId))) {
                req.setAttribute("exception", "You can not confirm unconfirmed or paid order");
                return "ordered";
            }

        } catch (PermissionException e) {
            req.setAttribute("exception", "You don't have access to this user's orders");
            return "unordered";
        }

        return "redirect:/paid";
    }

    /**
     * @param req
     * @param resp
     * @return Page with unconfirmed user's orders and changed quantity
     */
    @RequestMapping(value = "/unordered/changeQuantity", method = RequestMethod.POST)
    public String changeQuantity(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        Integer quantity = Integer.valueOf(req.getParameter("quantity"));
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        orderService.changeQuantity(loggedUser, productId, quantity);

        return "redirect:/unordered";
    }

    /**
     * @param req
     * @param resp
     * @return Page with unconfirmed user's orders and removed product by product Id
     */
    @RequestMapping(value = "unordered/removeProduct", method = RequestMethod.GET)
    public String removeProduct(@RequestParam(value = "productId") Integer productId,
                                @RequestParam(value = "orderId") Integer orderId,
                                HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser;
        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }
        orderService.removeProduct(loggedUser, orderId, productId);

        return "redirect:/unordered";
    }

    /**
     * @param req
     * @param resp
     * @return Page with unconfirmed user's orders and removed all products
     */
    @RequestMapping(value = "unordered/remove/{id}", method = RequestMethod.GET)
    public String removeAllProductsFromOrder(@PathVariable("id") Integer orderId,
                                             HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;
        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        orderService.remove(loggedUser, orderId);
        return "redirect:/unordered";
    }

}
