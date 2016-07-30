package net.shop.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity{

    @Column (name = "status")
    private OrderStatus status;

    @OneToMany()
    @JoinColumn(name = "PRODUCT_ID")
    private List<Product> productList;

}
