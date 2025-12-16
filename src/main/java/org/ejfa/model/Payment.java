package org.ejfa.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends PanacheEntity {
    public Long orderId;
    public Double amount;
    public String status;
    public String providerResponse;
    public LocalDateTime createdAt;

    public Payment() {
        this.createdAt = LocalDateTime.now();
    }
}
