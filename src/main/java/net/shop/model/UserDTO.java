package net.shop.model;


import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class UserDTO {

    private Integer id;

    private String login;

    private Boolean admin;

    private Boolean blocked;

//    private Set<Order> orderList;
}

