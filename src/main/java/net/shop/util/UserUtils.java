package net.shop.util;

import net.shop.model.User;
import org.springframework.beans.BeanUtils;

//A Java Bean is a normal Java class which implements the Serializable interface and have a parameterless
// constructor and have getters and setters for each field.
public abstract class UserUtils extends BeanUtils {

    /**
     *
     * @param target
     * @param result
     * @return userDTO without password for security thoughts
     */
    public static User getShallowCloneWithoutSecureData(User target, User result){
        result.setId(target.getId());
        result.setAdmin(target.getAdmin());
        result.setPassword(null);
        result.setBlocked(target.getBlocked());
        result.setLogin(target.getLogin());
        result.setOrderList(null);
        return result;
    }

}
