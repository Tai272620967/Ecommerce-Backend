package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.domain.Order;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.OrderService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;
import vn.hoidanit.jobhunter.util.RoleUtil;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    @ApiMessage("Get all orders with pagination")
    public ResponseEntity<ResultPaginationDTO> getAllOrders(
        @Filter Specification<Order> spec, Pageable pageable
    ) throws IdInvalidException {
        // Check if user is admin
        if (!RoleUtil.isAdmin()) {
            throw new IdInvalidException("Only admin users can access this endpoint");
        }
        return ResponseEntity.ok(this.orderService.handleGetAllOrders(spec, pageable));
    }
}

