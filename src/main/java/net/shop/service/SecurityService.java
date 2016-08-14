package net.shop.service;


import net.shop.model.User;
import net.shop.util.AuthenticateException;
import net.shop.util.AuthorizationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {

    String TOKEN = "token";

    User authenticate(HttpServletRequest req, HttpServletResponse resp) throws AuthenticateException;

    User authorization(HttpServletRequest req, HttpServletResponse resp) throws AuthorizationException;

    boolean logout(HttpServletRequest req, HttpServletResponse resp, User user) throws AuthorizationException;
}
