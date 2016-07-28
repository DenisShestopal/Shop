package net.shop.service;


import net.shop.dao.BaseDaoImpl;
import net.shop.dao.ProductDao;
import net.shop.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public List<Product> listProducts() {
        return this.productDao.listProducts();
    }
}
