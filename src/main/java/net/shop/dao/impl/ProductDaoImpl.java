package net.shop.dao.impl;


import net.shop.dao.ProductDao;
import net.shop.model.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> listProducts() {
        Session session = getSessionFactory().getCurrentSession();
        List<Product> productList = session.createQuery("from Product").list();

        for (Product product : productList) {
            logger.info("Product list: " + product);
        }

        return productList;
    }

    @Override
    public Product getProductByCode(String code) {
        Session session = getSessionFactory().getCurrentSession();

        return (Product) session.createQuery("from net.shop.model.Product u where u.code = :code")
                .setParameter("code", code)
                .uniqueResult();

    }
}
