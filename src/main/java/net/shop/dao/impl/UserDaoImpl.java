package net.shop.dao.impl;

import net.shop.dao.UserDao;
import net.shop.model.User;
import net.shop.util.AuthorizationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Session session = getSessionFactory().getCurrentSession();
        List<User> userList = session.createQuery("from User").list();

        for (User user : userList) {
            logger.info("Users list: " + user);
        }

        return userList;
    }

    @Override
    public User getUserByLogin(String login) {
        Session session = getSessionFactory().getCurrentSession();

        return (User) session.createQuery("from net.shop.model.User u where u.login = :login")
                .setParameter("login", login)
                .uniqueResult();

    }
}
