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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "owner") //TODO add to OrderDao getOrderByUserId
    private Set<Order> orderList;//TODO see composition

    /**
     * Some secure cloning of user, giving a "shallow clone" of user without secure data.
     *
     */
    public User(User user) {
        setId(user.getId());
        login = user.login;
        admin = user.admin;
        blocked = user.blocked;
    }

    public void setBlocked(Boolean isBlocked) {
        this.blocked = isBlocked;
    }

}
