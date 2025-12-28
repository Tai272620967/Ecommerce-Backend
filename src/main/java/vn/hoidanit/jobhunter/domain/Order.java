package vn.hoidanit.jobhunter.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "orders")
@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    // Shipping address information
    private String shippingFirstName;
    private String shippingLastName;
    private String shippingKataFirstName;
    private String shippingKataLastName;
    private String shippingPhone;
    private String shippingPostalCode;
    private String shippingPrefecture;
    private String shippingCity;
    private String shippingArea;
    private String shippingStreet;
    private String shippingBuilding;

    // Payment information
    private String paymentMethod;
    private String paymentStatus = "pending"; // pending, paid, failed

    // Credit card information (optional, only when paymentMethod is credit_card)
    private String cardNumber;
    private String cardHolderName;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cardCvv;

    // Order status
    private String status = "pending"; // pending, processing, shipped, delivered, cancelled

    // Order totals
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal tax;
    private BigDecimal total;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItem> orderItems;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = Instant.now();
    }
}

