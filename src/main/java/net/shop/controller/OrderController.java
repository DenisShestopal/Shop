package net.shop.controller;

import lombok.Getter;
import net.shop.model.Order;
import net.shop.model.User;
import net.shop.model.mock.LoggedUserMock;
import net.shop.service.OrderService;
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
@RequestMapping(value = "orders")
@Getter
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "orderService")
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String userOrder(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("order", new Order());
        req.setAttribute("listOrders", this.orderService.listOrders());
        //TODO using HttpServletRequest take userId and return to Response methods: getOrderByUserId

        //return reference to the page "oreders"
        return "orders";
    }

    @RequestMapping(value = "confirm/{orderId}")
    public String confirmOrder(HttpServletRequest request, HttpServletResponse response) throws AuthenticateException, NoOrdersException, PermissionException {
        //TODO DONE take id and read user if orderId->user ? next : exception
//        int loggedUserId = userService.getUserIdFromRequest(request);
        int orderId = Integer.valueOf(request.getRequestURI().split("orderId=")[1]);
        User user = new LoggedUserMock();

        getOrderService().confirmOrder(user, orderId);
        return "redirect:/orders";
    }

    @RequestMapping(value = "pay/{orderId}")
    public String payOrder(HttpServletRequest request, HttpServletResponse response) throws AuthenticateException, NoOrdersException, PermissionException {
        //TODO DONE take id and read user if orderId->user ? next : exception
//        int loggedUserId = userService.getUserIdFromRequest(request);
        int orderId = Integer.valueOf(request.getRequestURI().split("orderId=")[1]);
        User user = new LoggedUserMock();

        getOrderService().confirmOrder(user, orderId);
        return "redirect:/orders";
    }
}
