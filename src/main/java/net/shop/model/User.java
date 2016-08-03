package net.shop.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ADMIN")
    private Boolean admin;

    @Column(name = "BLOCKED")
    private Boolean blocked;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "owner") //TODO add to OrderDao getOrdersByUserId
//    @JoinTable(name = "USER_ORDERS",
//            joinColumns = @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns = @JoinColumn(name = "ORDER_ID"))
    private Set<Order> orderList;//TODO see composition

    public void setBlocked(Boolean isBlocked) {
        this.blocked = isBlocked;
    }

}
