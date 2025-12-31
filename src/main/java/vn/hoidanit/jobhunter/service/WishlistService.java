package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.Wishlist;
import vn.hoidanit.jobhunter.repository.ProductRepository;
import vn.hoidanit.jobhunter.repository.WishlistRepository;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public String handleAddToWishlist(long userId, long productId) {
        // Check if product already in wishlist
        Optional<Wishlist> existingWishlist = wishlistRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingWishlist.isPresent()) {
            return "Product already in wishlist";
        }

        // Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        // Create new wishlist item
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProduct(product);
        wishlistRepository.save(wishlist);

        return "Product added to wishlist successfully";
    }

    @Transactional
    public String handleRemoveFromWishlist(long userId, long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
        return "Product removed from wishlist successfully";
    }

    @Transactional(readOnly = true)
    public List<Product> handleGetWishlist(long userId) {
        List<Wishlist> wishlists = wishlistRepository.findAllByUserId(userId);
        return wishlists.stream()
                .map(Wishlist::getProduct)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean handleCheckWishlist(long userId, long productId) {
        return wishlistRepository.findByUserIdAndProductId(userId, productId).isPresent();
    }

    @Transactional(readOnly = true)
    public long handleGetWishlistCount(long userId) {
        return wishlistRepository.countByUserId(userId);
    }
}

