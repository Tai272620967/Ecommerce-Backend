package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.request.ReviewDTO;
import vn.hoidanit.jobhunter.domain.response.ApiResponseDTO;
import vn.hoidanit.jobhunter.domain.response.ResReviewDTO;
import vn.hoidanit.jobhunter.service.ReviewService;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;

@RestController
@RequestMapping("/api/v1/products")
@org.springframework.web.bind.annotation.CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    private Long getCurrentUserId() {
        String email = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("User not authenticated"));
        User user = userService.handleGetUserByUsername(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getId();
    }

    @PostMapping("/{productId}/reviews")
    @ApiMessage("Create review")
    public ResponseEntity<ResReviewDTO> createReview(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        Long userId = getCurrentUserId();
        ResReviewDTO review = reviewService.handleCreateReview(productId, userId, reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping("/{productId}/reviews")
    @ApiMessage("Get reviews by product")
    public ResponseEntity<Page<ResReviewDTO>> getReviews(
            @PathVariable("productId") Long productId,
            Pageable pageable) {
        Page<ResReviewDTO> reviews = reviewService.handleGetReviewsByProductId(productId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{productId}/reviews/{reviewId}")
    @ApiMessage("Update review")
    public ResponseEntity<ResReviewDTO> updateReview(
            @PathVariable("productId") Long productId,
            @PathVariable("reviewId") Long reviewId,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        Long userId = getCurrentUserId();
        ResReviewDTO review = reviewService.handleUpdateReview(reviewId, userId, reviewDTO);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{productId}/reviews/{reviewId}")
    @ApiMessage("Delete review")
    public ResponseEntity<ApiResponseDTO> deleteReview(
            @PathVariable("productId") Long productId,
            @PathVariable("reviewId") Long reviewId) {
        Long userId = getCurrentUserId();
        reviewService.handleDeleteReview(reviewId, userId);
        return ResponseEntity.ok(new ApiResponseDTO("Review deleted successfully"));
    }

    @GetMapping("/{productId}/reviews/stats")
    @ApiMessage("Get review statistics")
    public ResponseEntity<ReviewStatsDTO> getReviewStats(@PathVariable("productId") Long productId) {
        Double averageRating = reviewService.getAverageRating(productId);
        Long reviewCount = reviewService.getReviewCount(productId);
        return ResponseEntity.ok(new ReviewStatsDTO(averageRating, reviewCount));
    }

    public static class ReviewStatsDTO {
        private Double averageRating;
        private Long reviewCount;

        public ReviewStatsDTO(Double averageRating, Long reviewCount) {
            this.averageRating = averageRating;
            this.reviewCount = reviewCount;
        }

        public Double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
        }

        public Long getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(Long reviewCount) {
            this.reviewCount = reviewCount;
        }
    }
}

