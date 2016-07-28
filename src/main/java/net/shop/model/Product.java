package net.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column (name = "PRODUCT_NAME")
    private String name;

    @Column (name = "PRODUCT_PRICE")
    private double price;


}
