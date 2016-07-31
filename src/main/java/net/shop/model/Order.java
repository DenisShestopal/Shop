package net.shop.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;


@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Column(name = "STATUS", length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "PRODUCT_ID")
    @CollectionTable(name = "ORDER_PRODUCTS",
            joinColumns = @JoinColumn(name = "ORDER_ID")
    )
    @Column(name = "QUANTITY")
    private Map<Product, Integer> productList;

}
