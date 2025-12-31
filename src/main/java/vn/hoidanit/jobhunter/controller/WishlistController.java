package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ApiResponseDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.WishlistService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;

@RestController
@RequestMapping("/api/v1/wishlist")
@org.springframework.web.bind.annotation.CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    private long getCurrentUserId() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() 
            ? SecurityUtil.getCurrentUserLogin().get() 
            : "";
        if (email.isEmpty()) {
            throw new EntityNotFoundException("User not authenticated");
        }
        User currentUser = this.userService.handleGetUserByUsername(email);
        if (currentUser == null) {
            throw new EntityNotFoundException("User not found");
        }
        return currentUser.getId();
    }

    @PostMapping("/{productId}")
    @ApiMessage("Add product to wishlist")
    public ResponseEntity<ApiResponseDTO> addToWishlist(@PathVariable("productId") long productId) {
        long userId = getCurrentUserId();
        String message = wishlistService.handleAddToWishlist(userId, productId);
        return ResponseEntity.ok(new ApiResponseDTO(message));
    }

    @DeleteMapping("/{productId}")
    @ApiMessage("Remove product from wishlist")
    public ResponseEntity<ApiResponseDTO> removeFromWishlist(@PathVariable("productId") long productId) {
        long userId = getCurrentUserId();
        String message = wishlistService.handleRemoveFromWishlist(userId, productId);
        return ResponseEntity.ok(new ApiResponseDTO(message));
    }

    @GetMapping
    @ApiMessage("Get wishlist")
    public ResponseEntity<List<Product>> getWishlist() {
        long userId = getCurrentUserId();
        List<Product> products = wishlistService.handleGetWishlist(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/check/{productId}")
    @ApiMessage("Check if product is in wishlist")
    public ResponseEntity<Boolean> checkWishlist(@PathVariable("productId") long productId) {
        long userId = getCurrentUserId();
        boolean isInWishlist = wishlistService.handleCheckWishlist(userId, productId);
        return ResponseEntity.ok(isInWishlist);
    }

    @GetMapping("/count")
    @ApiMessage("Get wishlist count")
    public ResponseEntity<Long> getWishlistCount() {
        long userId = getCurrentUserId();
        long count = wishlistService.handleGetWishlistCount(userId);
        return ResponseEntity.ok(count);
    }
}

