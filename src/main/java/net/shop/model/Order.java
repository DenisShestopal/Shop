package net.shop.model;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Column (name = "ORDER_STATUS")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
