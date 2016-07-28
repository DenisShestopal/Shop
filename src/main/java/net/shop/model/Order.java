package net.shop.model;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "ORDER_STATUS")
    private String status;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
