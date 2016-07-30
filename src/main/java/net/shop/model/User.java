package net.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

    @Column (name = "LOGIN")
    private String login;

    @Column (name = "PASSWORD")
    private String password;

    @Column (name = "IS_ADMIN")
    private Boolean isAdmin;

    @Column (name = "IS_BLOCKED")
    private Boolean isBlocked;

    @OneToMany()
    @JoinColumn(name = "ORDER_ID")
    private List<Order> orderList;

    public void setBlocked(Boolean isBlocked){
        this.isBlocked = isBlocked;
    }


}
