package net.shop.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LoggedUserUtil {
    private static int id;
    private static Map<String, Integer> sessionUserIdMap = new HashMap<>();

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LoggedUserUtil.id = id;
    }

    public static Map<String, Integer> getSessionUserIdMap() {
        return sessionUserIdMap;
    }



}
