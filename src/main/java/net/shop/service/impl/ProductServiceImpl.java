package net.shop.service.impl;


import lombok.Getter;
import lombok.Setter;
import net.shop.dao.BaseDao;
import net.shop.dao.OrderDao;
import net.shop.dao.ProductDao;
import net.shop.model.Order;
import net.shop.model.Product;
import net.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    private ProductDao productDao;

    private OrderDao orderDao;

    @Autowired
    @Qualifier(value = "productDao")
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    @Qualifier(value = "orderDao")
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public BaseDao<Product> getDao() {
        return productDao;
    }

    @Override
    public List<Product> listProducts() {
        return this.productDao.listProducts();
    }

    @Override
    public boolean addToOrder(int userId, int orderId, int productId) {
        Order order = orderDao.getById(orderId);
        Product product = productDao.getById(productId);
        order.getProductList().put(product, 1);//should we put here a product to the orders table as a new "order's product"?

        return true;

    }
}
