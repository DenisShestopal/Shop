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
import net.shop.util.Hello;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;


@Controller
@RequestMapping(value = "products")
@Getter
public class ProductController {

    private ProductService productService;
    private UserService userService;
    private SecurityService securityService;

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
    @Qualifier(value = "productService")
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listProducts(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
            req.setAttribute("user", loggedUser);
            req.setAttribute("loggedUser", loggedUser.getLogin());
            Hello.userLogin = loggedUser.getLogin();
        } catch (AuthenticateException e) {
            req.setAttribute("user", new User());
            req.setAttribute("loggedUser", "Unsigned user");
        }

        req.setAttribute("product", new Product());
        req.setAttribute("listProducts", this.productService.listProducts());

        return "products";
    }

    @RequestMapping(value = "/addtoorder/{productId}", method = RequestMethod.GET)
    public String addToOrder(HttpServletRequest req, HttpServletResponse resp) {
        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            req.setAttribute("exception", "You need to get authorized first");
            return "authorization";
        }

        int userId = loggedUser.getId();
        int productId = Integer.valueOf(req.getRequestURI().split("products/addtoorder/")[1]);
        boolean result = getProductService().addToOrder(loggedUser, productId);
//        if (result)
//            req.setAttribute("result", "product added");
//        else
//            req.setAttribute("result", "product was not added");

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
        Product creatingProduct = new Product(req.getParameter("name"), Long.parseLong(req.getParameter("price")), "USD");
        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
            req.setAttribute("loggedUser", loggedUser.getLogin());
        } catch (AuthenticateException e) {
            req.setAttribute("exception", "Please, get authorized first");
            return "authorization";
        }

        if (!loggedUser.getAdmin()) {
            req.setAttribute("exception", "Only admin can manage products list");
            return "redirect:/products";
        }

//        this.productService.add(product);

//        Product creatingProduct = new Product(req.getParameter("name"), Long.parseLong(req.getParameter("price")), "USD");

        try {
            this.productService.add(creatingProduct);
        } catch (ConstraintViolationException e) {
            req.setAttribute("exception", "Product already exists");
            req.setAttribute("product", new Product());
            req.setAttribute("listUsers", this.productService.listProducts());
            return "products";
        }

        return "redirect:/products";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) {
        Product updatingProduct = new Product(req.getParameter("name"), Long.parseLong(req.getParameter("price")), "USD");
        String strProductId = req.getParameter("id");
        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
            req.setAttribute("loggedUser", loggedUser.getLogin());
        } catch (AuthenticateException e) {
            req.setAttribute("exception", "You need to get authorized first ");
            return "authorization";
        }

        if (!loggedUser.getAdmin()) {
            req.setAttribute("exception", "Only admin can manage products list");
            return "redirect:/products";
        }

//        Product updatingProduct = new Product(req.getParameter("name"),
//                Long.parseLong(req.getParameter("price")), "USD");

        updatingProduct.setId(Integer.valueOf(strProductId));
        this.productService.update(updatingProduct);

        return "redirect:/products";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(HttpServletRequest req, HttpServletResponse resp) {

        User loggedUser = null;

        try {
            loggedUser = getSecurityService().authenticate(req, resp);
            req.setAttribute("loggedUser", loggedUser.getLogin());
        } catch (AuthenticateException e) {
            req.setAttribute("exception", "Please, get authorized first ");
            return "authorization";
        }

        if (!loggedUser.getAdmin()) {
            req.setAttribute("exception", "Only admin can manage products list");
            return "redirect:/products";
        }

        int productId = Integer.valueOf(req.getRequestURI().split("products/edit/")[1]);
        req.setAttribute("product", this.productService.getById(productId));
        req.setAttribute("listProducts", this.productService.listProducts());

        return "products";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(HttpServletRequest req, HttpServletResponse resp) {

        User user = null;

        try {
            user = getSecurityService().authenticate(req, resp);
        } catch (AuthenticateException e) {
            req.setAttribute("exception", "Please, get authorized first");
            return "authorization";
        }

        if (!user.getAdmin()) {
            req.setAttribute("exception", "Only admin can manage products list");
            return "redirect:/products";
        }

        int productId = Integer.valueOf(req.getRequestURI().split("products/remove/")[1]);
        ;
        this.productService.remove(productId);

        return "redirect:/products";
    }

}
