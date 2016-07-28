package net.shop.controller;

import net.shop.model.Order;
import net.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "order")
public class OrderController {

    private OrderService orderService;

    @Autowired(required = true)
    @Qualifier(value = "orderService")
    public void setProductService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "confirm/{orderId}")
    public boolean confirmOrder(@PathVariable("orderId") int orderId){
        //TODO take id and read user if orderId->user ? next : exception
        return orderService.confirmOrder(orderId);
    }

    @RequestMapping(value = "pay/{orderId}")
    public boolean payOrder(@PathVariable("orderId")int orderId){
        //TODO take id and read user if orderId->user ? next : exception
        return orderService.payOrder(orderId);
    }
}
