package net.shop.model.mock;

import net.shop.model.User;

import java.util.HashSet;

public class LoggedUserMock extends User {
    public LoggedUserMock() {
        this.setId(1);
        this.setLogin("UserTest");
        this.setPassword("PasswordTest");
        this.setAdmin(true);
        this.setBlocked(false);
        this.setOrderList(new HashSet<>());
    }
}
