package net.shop.controller;


import net.shop.model.Product;
import net.shop.model.User;
import net.shop.service.ProductService;
import net.shop.service.UserService;
import net.shop.util.AuthException;
import net.shop.util.LoggedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "product")
public class ProductController {

    private ProductService productService;
    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired(required = true)
    @Qualifier(value = "productService")
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value="/basket/add", method = RequestMethod.POST)
    public boolean addToBasket(HttpServletRequest request, HttpServletResponse response) throws AuthException {
        int loggedUserId = userService.getUserIdFromRequest(request);
        User user = userService.getById(loggedUserId);

        return true;//TODO add to basket
    }

    //display our products on the page
    @RequestMapping(value = "products", method = RequestMethod.GET)
    public String listProducts(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("listProducts", this.productService.listProducts());

        //return reference to the page "products"
        return "products";
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("product") Product product){
        //TODO get user by id and user's authority. if admin ? next : exception
        if(product.getId() == 0){
            this.productService.add(product);
        }else{
            this.productService.update(product);
        }

        return "redirect:/products";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id){
        //TODO get user by id and user's authority. if admin ? next : exception
        this.productService.remove(id);

        return "redirect:/products";
    }

    @RequestMapping("edit/{id}")
    //TODO get user by id and user's authority. if admin ? next : exception
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("product", this.productService.getById(id));
        model.addAttribute("listProducts", this.productService.listProducts());

        return "products";
    }

    @RequestMapping("productdata/{id}")
    public String productData(@PathVariable("id") int id, Model model){
        model.addAttribute("product", this.productService.getById(id));

        return "productdata";
    }
}
