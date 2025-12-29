package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Order;
import vn.hoidanit.jobhunter.domain.request.OrderDTO;
import vn.hoidanit.jobhunter.domain.response.ResOrderDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.OrderService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;
import vn.hoidanit.jobhunter.util.RoleUtil;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @ApiMessage("Create a new order")
    public ResponseEntity<ResOrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        ResOrderDTO createdOrder = this.orderService.handleCreateOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping(value = "/orders", params = {"page", "size"})
    @ApiMessage("Get all orders with pagination")
    public ResponseEntity<ResultPaginationDTO> getAllOrders(
        @Filter Specification<Order> spec, Pageable pageable
    ) throws IdInvalidException {
        // Check if user is admin - only admin can view all orders
        if (!RoleUtil.isAdmin()) {
            throw new IdInvalidException("Only admin users can access this endpoint");
        }
        return ResponseEntity.ok(this.orderService.handleGetAllOrders(spec, pageable));
    }

    @GetMapping("/orders")
    @ApiMessage("Get all orders for current user")
    public ResponseEntity<List<ResOrderDTO>> getOrders() {
        List<ResOrderDTO> orders = this.orderService.handleGetOrdersByUserId();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    @ApiMessage("Get order by id")
    public ResponseEntity<ResOrderDTO> getOrderById(@PathVariable("id") long id) {
        ResOrderDTO order = this.orderService.handleGetOrderById(id);
        return ResponseEntity.ok(order);
    }
}

