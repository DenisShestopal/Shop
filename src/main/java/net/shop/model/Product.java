package net.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "PRODUCTS")
public class Product extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Long price;
}
