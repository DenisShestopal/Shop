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

    public static int getUserIdFromRequest(HttpServletRequest request) throws AuthException {
        Cookie[] cookies = request.getCookies();
        String sId = "J_SESSION_ID";
        String userSessionId = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(sId)){
                userSessionId = cookie.getValue();
            }
        }
        if(LoggedUserUtil.getSessionUserIdMap().containsKey(userSessionId)){
            return LoggedUserUtil.getSessionUserIdMap().get(userSessionId);
        }
        throw new AuthException();
    }

}
