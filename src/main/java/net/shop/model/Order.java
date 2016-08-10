package net.shop.model;


import lombok.*;

import javax.persistence.*;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@ToString(callSuper = true)
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Column(name = "STATUS", length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinTable(name = "USER_ORDERS",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private User owner;

    @ElementCollection
    @MapKeyJoinColumn(name = "PRODUCT_ID")
    @CollectionTable(name = "ORDER_PRODUCTS",
            joinColumns = @JoinColumn(name = "ORDER_ID")
    )
    @Column(name = "QUANTITY")
    private Map<Product, Integer> productList;//TODO see aggregation

}
