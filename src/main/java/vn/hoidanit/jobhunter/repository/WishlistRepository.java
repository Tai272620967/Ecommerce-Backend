package vn.hoidanit.jobhunter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.domain.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUserIdAndProductId(long userId, long productId);
    
    List<Wishlist> findAllByUserId(long userId);
    
    void deleteByUserIdAndProductId(long userId, long productId);
    
    long countByUserId(long userId);
}

