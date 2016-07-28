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

    //display our orders on the page
    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public String listOrders(Model model){
        model.addAttribute("order", new Order());
        model.addAttribute("listProducts", this.orderService.listOrders());

        //return reference to the page "products"
        return "orders";
    }

    @RequestMapping(value = "/orders/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("order") Order order){
        if(order.getId() == 0){
            this.orderService.add(order);
        }else{
            this.orderService.update(order);
        }

        return "redirect:/order";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id){
        this.orderService.remove(id);

        return "redirect:/orders";
    }

    @RequestMapping("edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("order", this.orderService.getById(id));
        model.addAttribute("listOrders", this.orderService.listOrders());

        return "orders";
    }

    @RequestMapping("productdata/{id}")
    public String orderData(@PathVariable("id") int id, Model model){
        model.addAttribute("product", this.orderService.getById(id));

        return "orderdata";
    }
}
