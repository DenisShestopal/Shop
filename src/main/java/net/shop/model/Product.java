package net.shop.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Id
    @Column (name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "PRODUCT_NAME")
    private String productName;

    @Column (name = "PRODUCT_PRICE")
    private double productPrice;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}
