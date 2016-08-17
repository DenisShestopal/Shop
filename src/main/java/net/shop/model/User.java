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

    @Column(name = "LOGIN", unique = true)
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ADMIN")
    private Boolean admin;

    @Column(name = "BLOCKED")
    private Boolean blocked;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "owner")
    private Set<Order> orderList;

    public void setBlocked(Boolean isBlocked) {
        this.blocked = isBlocked;
    }

}
