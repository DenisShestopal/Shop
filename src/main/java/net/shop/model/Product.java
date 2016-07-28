package net.shop.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column (name = "PRODUCT_NAME")
    private String name;

    @Column (name = "PRODUCT_PRICE")
    private double price;

    public String getProductName() {
        return name;
    }

    public void setProductName(String productName) {
        this.name = productName;
    }

    public double getProductPrice() {
        return price;
    }

    public void setProductPrice(double productPrice) {
        this.price = productPrice;
    }

}
