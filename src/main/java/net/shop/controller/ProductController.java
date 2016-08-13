package net.shop.controller;


import lombok.Getter;
import net.shop.model.Product;
import net.shop.model.User;
import net.shop.model.mock.LoggedUserMock;
import net.shop.service.ProductService;
import net.shop.service.SecurityService;
import net.shop.service.UserService;
import net.shop.util.AuthenticateException;
import net.shop.util.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "products")
@Getter
public class ProductController {

    private ProductService productService;
    private UserService userService;
    private SecurityService securityService;

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

    //display our products on the page
    @RequestMapping(method = RequestMethod.GET)
    public String listProducts(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("product", new Product());
        req.setAttribute("listProducts", this.productService.listProducts());

        //return reference to the page "products"
        return "products";
    }

    @RequestMapping(value = "/addtoorder/{productId}", method = RequestMethod.GET)
    public String addToOrder(HttpServletRequest req, HttpServletResponse resp) {
        //int loggedUserId = userService.getUserIdFromRequest(request);
        User user = null;
        try {
            user = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }
        int userId = user.getId();

        int productId = Integer.valueOf(req.getRequestURI().split("products/addtoorder/")[1]);

        boolean result = getProductService().addToOrder(userId, productId);

        req.setAttribute("result", result);

        return "redirect:/products";
    }

    @RequestMapping("/{id}")
    public String productData(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.valueOf(req.getRequestURI().split("products/")[1]);
        req.setAttribute("product", this.productService.getById(productId));

        return "productdata";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        Product product = new Product(req.getParameter("name"), Long.parseLong(req.getParameter("price")),"USD");
        String strProductId = req.getParameter("id");
        //TODO get user by id and user's authority. if admin ? next : exception

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        this.productService.add(product);

        return "redirect:/products";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) {
        Product product = new Product(req.getParameter("name"), Long.parseLong(req.getParameter("price")),"USD");
        String strProductId = req.getParameter("id");
        //TODO get user by id and user's authority. if admin ? next : exception

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        product.setId(Integer.valueOf(strProductId));
        this.productService.update(product);

        return "redirect:/products";
    }

    @RequestMapping(value= "edit/{id}", method = RequestMethod.GET)
    //TODO get user by id and user's authority. if admin ? next : exception
    public String edit(HttpServletRequest req, HttpServletResponse resp) {

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        int productId = Integer.valueOf(req.getRequestURI().split("products/edit/")[1]);
        req.setAttribute("product", this.productService.getById(productId));
        req.setAttribute("listProducts", this.productService.listProducts());

        return "products";
    }

    @RequestMapping(value= "/remove/{id}", method = RequestMethod.GET)
    public String remove(HttpServletRequest req, HttpServletResponse resp) {
        //TODO get user by id and user's authority. if admin ? next : exception

        try {
            getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            return "authorization";
        }

        int productId = Integer.valueOf(req.getRequestURI().split("products/remove/")[1]);;
        this.productService.remove(productId);

        return "redirect:/products";
    }

}
