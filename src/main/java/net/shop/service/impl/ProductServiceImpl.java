package net.shop.service.impl;


import lombok.Getter;
import lombok.Setter;
import net.shop.dao.OrderDao;
import net.shop.dao.ProductDao;
import net.shop.model.Order;
import net.shop.model.Product;
import net.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Getter
@Setter
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductDao productDao;

    private OrderDao orderDao;

    @Override
    @Transactional
    public List<Product> listProducts() {
        return this.productDao.listProducts();
    }

    public boolean addToBasket(int userId, int orderId, int productId){
        Order order = orderDao.getById(orderId);
        Product product = productDao.getById(productId);
        order.getProductList().put(product, 1);

        return true;

    }
}
