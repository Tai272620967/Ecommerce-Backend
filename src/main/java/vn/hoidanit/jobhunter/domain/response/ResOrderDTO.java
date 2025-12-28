package vn.hoidanit.jobhunter.domain.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.domain.OrderItem;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResOrderDTO {
    private long id;
    private long userId;
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
    private String paymentMethod;
    private String paymentStatus;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal tax;
    private BigDecimal total;
    private Instant createdAt;
    private Instant updatedAt;
    private List<OrderItem> orderItems;
}

