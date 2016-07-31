package net.shop.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Long price;

//    @Column(name = "CURRENCY")
//    private String currency;

    //TODO DONE REDO add currency value-column
}
