package vn.hoidanit.jobhunter.domain.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    // Shipping address information
    @NotBlank(message = "First name is required")
    private String shippingFirstName;

    @NotBlank(message = "Last name is required")
    private String shippingLastName;

    private String shippingKataFirstName;
    private String shippingKataLastName;

    @NotBlank(message = "Phone is required")
    private String shippingPhone;

    @NotBlank(message = "Postal code is required")
    private String shippingPostalCode;

    @NotBlank(message = "Prefecture is required")
    private String shippingPrefecture;

    @NotBlank(message = "City is required")
    private String shippingCity;

    @NotBlank(message = "Area is required")
    private String shippingArea;

    @NotBlank(message = "Street is required")
    private String shippingStreet;

    private String shippingBuilding;

    // Payment information
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    // Credit card information (optional, only when paymentMethod is credit_card)
    private String cardNumber;
    private String cardHolderName;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cardCvv;

    // Order items (product IDs and quantities from cart)
    @NotNull(message = "Cart ID is required")
    private Long cartId;

    // Optional: shipping fee and tax (can be calculated on backend)
    private BigDecimal shippingFee;
    private BigDecimal tax;
}

