package vn.hoidanit.jobhunter.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.Review;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.request.ReviewDTO;
import vn.hoidanit.jobhunter.domain.response.ResReviewDTO;
import vn.hoidanit.jobhunter.repository.ProductRepository;
import vn.hoidanit.jobhunter.repository.ReviewRepository;
import vn.hoidanit.jobhunter.repository.UserRepositoty;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepositoty userRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepositoty userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResReviewDTO handleCreateReview(Long productId, Long userId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Allow users to review the same product multiple times
        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        Review savedReview = reviewRepository.save(review);
        return convertToResReviewDTO(savedReview);
    }

    @Transactional(readOnly = true)
    public Page<ResReviewDTO> handleGetReviewsByProductId(Long productId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable);
        return reviews.map(this::convertToResReviewDTO);
    }

    @Transactional
    public ResReviewDTO handleUpdateReview(Long reviewId, Long userId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found or you don't have permission to update it"));

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        Review updatedReview = reviewRepository.save(review);
        return convertToResReviewDTO(updatedReview);
    }

    @Transactional
    public void handleDeleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found or you don't have permission to delete it"));

        reviewRepository.delete(review);
    }

    @Transactional(readOnly = true)
    public Double getAverageRating(Long productId) {
        Double avgRating = reviewRepository.findAverageRatingByProductId(productId);
        return avgRating != null ? avgRating : 0.0;
    }

    @Transactional(readOnly = true)
    public Long getReviewCount(Long productId) {
        return reviewRepository.countByProductId(productId);
    }

    private ResReviewDTO convertToResReviewDTO(Review review) {
        ResReviewDTO dto = new ResReviewDTO();
        dto.setId(review.getId());
        dto.setProductId(review.getProduct().getId());
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        dto.setUserEmail(review.getUser().getEmail());
        dto.setUserAvatarUrl(review.getUser().getAvatarUrl());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());
        return dto;
    }
}

