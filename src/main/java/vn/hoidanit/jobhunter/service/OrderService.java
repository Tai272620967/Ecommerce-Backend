package vn.hoidanit.jobhunter.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import vn.hoidanit.jobhunter.domain.Cart;
import vn.hoidanit.jobhunter.domain.CartItem;
import vn.hoidanit.jobhunter.domain.Order;
import vn.hoidanit.jobhunter.domain.OrderItem;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.request.OrderDTO;
import vn.hoidanit.jobhunter.domain.response.ResOrderDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CartItemRepository;
import vn.hoidanit.jobhunter.repository.CartRepository;
import vn.hoidanit.jobhunter.repository.OrderItemRepository;
import vn.hoidanit.jobhunter.repository.OrderRepository;
import vn.hoidanit.jobhunter.util.SecurityUtil;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    // Default shipping fee and tax rate
    private static final BigDecimal DEFAULT_SHIPPING_FEE = BigDecimal.valueOf(500);
    private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.10); // 10% tax

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserService userService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
    }

    @Transactional
    public ResOrderDTO handleCreateOrder(OrderDTO orderDTO) {
        // Get current user
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        User currentUser = this.userService.handleGetUserByUsername(email);
        
        if (currentUser == null) {
            throw new EntityNotFoundException("User not found");
        }

        // Get cart and cart items
        Optional<Cart> cartOptional = this.cartRepository.findById(orderDTO.getCartId());
        if (cartOptional.isEmpty()) {
            throw new EntityNotFoundException("Cart not found");
        }

        Cart cart = cartOptional.get();
        
        // Verify cart belongs to current user
        if (cart.getUserId() != currentUser.getId()) {
            throw new RuntimeException("Cart does not belong to current user");
        }

        List<CartItem> cartItems = this.cartItemRepository.findAllByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create order
        Order order = new Order();
        order.setUserId(currentUser.getId());
        
        // Set shipping information
        order.setShippingFirstName(orderDTO.getShippingFirstName());
        order.setShippingLastName(orderDTO.getShippingLastName());
        order.setShippingKataFirstName(orderDTO.getShippingKataFirstName());
        order.setShippingKataLastName(orderDTO.getShippingKataLastName());
        order.setShippingPhone(orderDTO.getShippingPhone());
        order.setShippingPostalCode(orderDTO.getShippingPostalCode());
        order.setShippingPrefecture(orderDTO.getShippingPrefecture());
        order.setShippingCity(orderDTO.getShippingCity());
        order.setShippingArea(orderDTO.getShippingArea());
        order.setShippingStreet(orderDTO.getShippingStreet());
        order.setShippingBuilding(orderDTO.getShippingBuilding());
        
        // Set payment information
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setPaymentStatus("pending");
        order.setStatus("pending");
        
        // Set credit card information if payment method is credit_card
        if ("credit_card".equals(orderDTO.getPaymentMethod())) {
            order.setCardNumber(orderDTO.getCardNumber());
            order.setCardHolderName(orderDTO.getCardHolderName());
            order.setCardExpiryMonth(orderDTO.getCardExpiryMonth());
            order.setCardExpiryYear(orderDTO.getCardExpiryYear());
            order.setCardCvv(orderDTO.getCardCvv());
        }

        // Calculate totals
        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shippingFee = orderDTO.getShippingFee() != null ? 
                orderDTO.getShippingFee() : DEFAULT_SHIPPING_FEE;
        
        BigDecimal tax = orderDTO.getTax() != null ? 
                orderDTO.getTax() : subtotal.multiply(TAX_RATE);
        
        BigDecimal total = subtotal.add(shippingFee).add(tax);

        order.setSubtotal(subtotal);
        order.setShippingFee(shippingFee);
        order.setTax(tax);
        order.setTotal(total);

        // Save order
        Order savedOrder = this.orderRepository.save(order);

        // Create order items from cart items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            // Subtotal will be calculated in @PrePersist
            return this.orderItemRepository.save(orderItem);
        }).collect(Collectors.toList());

        // Update cart status to "completed"
        cart.setStatus("completed");
        this.cartRepository.save(cart);

        // Convert to response DTO
        return convertToResOrderDTO(savedOrder, orderItems);
    }

    @Transactional(readOnly = true)
    public List<ResOrderDTO> handleGetOrdersByUserId() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        User currentUser = this.userService.handleGetUserByUsername(email);
        
        if (currentUser == null) {
            throw new EntityNotFoundException("User not found");
        }

        List<Order> orders = this.orderRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
        return orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = this.orderItemRepository.findByOrderId(order.getId());
                    return convertToResOrderDTO(order, orderItems);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResOrderDTO handleGetOrderById(long orderId) {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        User currentUser = this.userService.handleGetUserByUsername(email);
        
        if (currentUser == null) {
            throw new EntityNotFoundException("User not found");
        }

        Optional<Order> orderOptional = this.orderRepository.findByIdAndUserId(orderId, currentUser.getId());
        if (orderOptional.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }

        Order order = orderOptional.get();
        List<OrderItem> orderItems = this.orderItemRepository.findByOrderId(order.getId());
        return convertToResOrderDTO(order, orderItems);
    }

    @Transactional(readOnly = true)
    public ResultPaginationDTO handleGetAllOrders(Specification<Order> spec, Pageable pageable) {
        Page<Order> pageOrder = this.orderRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageOrder.getNumber() + 1);
        mt.setPageSize(pageOrder.getSize());
        mt.setPages(pageOrder.getTotalPages());
        mt.setTotal(pageOrder.getTotalElements());

        rs.setMeta(mt);

        // Fetch all order items in one query to avoid N+1 problem
        List<Order> orders = pageOrder.getContent();
        List<Long> orderIds = orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());
        
        List<OrderItem> allOrderItems = orderIds.isEmpty() 
                ? List.of() 
                : this.orderItemRepository.findByOrderIdIn(orderIds);
        
        // Group order items by order ID
        java.util.Map<Long, List<OrderItem>> orderItemsMap = allOrderItems.stream()
                .collect(Collectors.groupingBy(item -> item.getOrder().getId()));

        // Convert orders to ResOrderDTO
        List<ResOrderDTO> listOrder = orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemsMap.getOrDefault(order.getId(), List.of());
                    return convertToResOrderDTO(order, orderItems);
                })
                .collect(Collectors.toList());

        rs.setResult(listOrder);

        return rs;
    }

    private ResOrderDTO convertToResOrderDTO(Order order, List<OrderItem> orderItems) {
        ResOrderDTO resOrderDTO = new ResOrderDTO();
        resOrderDTO.setId(order.getId());
        resOrderDTO.setUserId(order.getUserId());
        resOrderDTO.setShippingFirstName(order.getShippingFirstName());
        resOrderDTO.setShippingLastName(order.getShippingLastName());
        resOrderDTO.setShippingKataFirstName(order.getShippingKataFirstName());
        resOrderDTO.setShippingKataLastName(order.getShippingKataLastName());
        resOrderDTO.setShippingPhone(order.getShippingPhone());
        resOrderDTO.setShippingPostalCode(order.getShippingPostalCode());
        resOrderDTO.setShippingPrefecture(order.getShippingPrefecture());
        resOrderDTO.setShippingCity(order.getShippingCity());
        resOrderDTO.setShippingArea(order.getShippingArea());
        resOrderDTO.setShippingStreet(order.getShippingStreet());
        resOrderDTO.setShippingBuilding(order.getShippingBuilding());
        resOrderDTO.setPaymentMethod(order.getPaymentMethod());
        resOrderDTO.setPaymentStatus(order.getPaymentStatus());
        resOrderDTO.setStatus(order.getStatus());
        resOrderDTO.setSubtotal(order.getSubtotal());
        resOrderDTO.setShippingFee(order.getShippingFee());
        resOrderDTO.setTax(order.getTax());
        resOrderDTO.setTotal(order.getTotal());
        resOrderDTO.setCreatedAt(order.getCreatedAt());
        resOrderDTO.setUpdatedAt(order.getUpdatedAt());
        resOrderDTO.setOrderItems(orderItems);
        return resOrderDTO;
    }
}

