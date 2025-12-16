package org.ejfa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {
    public Long productId;
    public Long userId;
    public Integer quantity;
    public Double totalPrice;
    public String status;
    public LocalDateTime createdAt;

    public Order() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }
}
