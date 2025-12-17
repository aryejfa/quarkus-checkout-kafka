package org.ejfa.domain;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {

    public String userId;
    public String productId;
    public Integer quantity;
    public Double totalPrice;
    public String status;
    public LocalDateTime createdAt;
}
