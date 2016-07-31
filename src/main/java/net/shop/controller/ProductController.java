package net.shop.controller;


import lombok.Getter;
import lombok.Setter;
import net.shop.model.Product;
import net.shop.model.User;
import net.shop.model.mock.LoggedUserMock;
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
@RequestMapping(value = "products")
@Getter
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

    @RequestMapping(value = "/addtoorder/{productId}", method = RequestMethod.POST)
    public String addToBasket(HttpServletRequest req, HttpServletResponse resp) throws AuthException {
        //int loggedUserId = userService.getUserIdFromRequest(request);
        User user = new LoggedUserMock();
        int userId = user.getId();
        int orderId = Integer.valueOf(req.getRequestURI().split("orderId=")[1]);
        int productId = Integer.valueOf(req.getRequestURI().split("productId=")[1]);
        //int userId = Integer.valueOf(request.getRequestURI().split("userId=")[1]);

        getProductService().addToBasket(userId, orderId, productId);

        return "redirect:/products";//TODO DONE? add to order
    }

    //display our products on the page
    @RequestMapping(method = RequestMethod.GET)
    public String listProducts(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("product", new Product());
        req.setAttribute("listProducts", this.productService.listProducts());

        //return reference to the page "products"
        return "products";
    }

    @RequestMapping("/remove/{id}")
    public String remove(HttpServletRequest req, HttpServletResponse resp) {
        //TODO get user by id and user's authority. if admin ? next : exception
        int productId = Integer.parseInt(req.getParameter("id"));
        this.productService.remove(productId);

        return "redirect:/products";
    }

    @RequestMapping("edit/{id}")
    //TODO get user by id and user's authority. if admin ? next : exception
    public String edit(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("product", this.productService.getById(productId));
        req.setAttribute("listProducts", this.productService.listProducts());

        return "products";
    }

    @RequestMapping("/{id}")
    public String productData(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("product", this.productService.getById(productId));

        return "productdata";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        Product product = new Product(req.getParameter("name"), Long.parseLong(req.getParameter("price")));
        //TODO get user by id and user's authority. if admin ? next : exception
        if (product.getId() == 0) {
            this.productService.add(product);
        } else {
            this.productService.update(product);
        }

        return "redirect:/products";
    }
}
