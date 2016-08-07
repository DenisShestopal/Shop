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

    @RequestMapping(value = "/addtoorder/{productId}", method = RequestMethod.GET)
    public String addToOrder(HttpServletRequest req, HttpServletResponse resp) throws AuthException {
        //int loggedUserId = userService.getUserIdFromRequest(request);
        User user = new LoggedUserMock();
        int userId = user.getId();
//        int orderId = Integer.valueOf(req.getParameter("orderId")); //TODO remove param orderId from this aspect
        int productId = Integer.valueOf(req.getRequestURI().split("products/addtoorder/")[1]);

        getProductService().addToOrder(userId, productId);

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

    @RequestMapping(value= "/remove/{id}", method = RequestMethod.GET)
    public String remove(HttpServletRequest req, HttpServletResponse resp) {
        //TODO get user by id and user's authority. if admin ? next : exception
        int productId = Integer.valueOf(req.getRequestURI().split("products/remove/")[1]);;
        this.productService.remove(productId);

        return "redirect:/products";
    }

    @RequestMapping(value= "edit/{id}", method = RequestMethod.GET)
    //TODO get user by id and user's authority. if admin ? next : exception
    public String edit(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.valueOf(req.getRequestURI().split("products/edit/")[1]);
        req.setAttribute("product", this.productService.getById(productId));
        req.setAttribute("listProducts", this.productService.listProducts());

        return "products";
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
        //TODO divide operations to 'add' + 'update'. Relocate update operation invocation to "/edit" controller.
        if (strProductId == null) {
            this.productService.add(product);
        } else {
            product.setId(Integer.valueOf(strProductId));
            this.productService.update(product);
        }

        return "redirect:/products";
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String add(HttpServletRequest req, HttpServletResponse resp) {
//        String login = req.getParameter("login");
//        String password = req.getParameter("password");
//        Boolean admin = Boolean.parseBoolean(req.getParameter("admin"));
//        Boolean blocked = Boolean.parseBoolean(req.getParameter("blocked"));
//        Integer userId = Integer.parseInt(req.getParameter("userId");
//        if (userId == null) {
//            this.userService.add(new User(all params));
//        } else {
//            this.userService.update(new User(all params with ID);
//          TODO make appropriate contsructor and change id to Integer in BaseEntity
//        }
//
//        return "redirect:/users";
//    }
}
