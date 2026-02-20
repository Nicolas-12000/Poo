package com.example.orders_api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Version;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    private UUID id;

    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @org.springframework.data.annotation.CreatedDate
    private OffsetDateTime createdAt;

    @org.springframework.data.annotation.LastModifiedDate
    private OffsetDateTime updatedAt;

    @Version
    private Long version;

    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal discountPercent = BigDecimal.ZERO; // percent, e.g. 10.0 means 10%
    private BigDecimal taxPercent = BigDecimal.ZERO; // percent
    private BigDecimal total = BigDecimal.ZERO;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (status == null) status = OrderStatus.CREATED;
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}
