package net.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

    @Column (name = "LOGIN")
    private String login;

    @Column (name = "PASSWORD")
    private String password;

    @Column (name = "IS_ADMIN")
    private boolean isAdmin;

    @Column (name = "IS_BLOCKED")
    private boolean isBlocked;

    @OneToMany()
    @JoinColumn(name = "ORDER_ID")
    private List<Order> orderList;

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

}
