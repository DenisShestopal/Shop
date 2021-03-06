package net.shop.model;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Column(name = "STATUS", length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @MapKeyJoinColumn(name = "PRODUCT_ID")
    @CollectionTable(name = "ORDER_PRODUCTS",
            joinColumns = @JoinColumn(name = "ORDER_ID")
    )
    @Column(name = "QUANTITY")
    private Map<Product, Integer> productList;

}
