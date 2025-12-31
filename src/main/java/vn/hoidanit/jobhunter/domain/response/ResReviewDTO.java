package vn.hoidanit.jobhunter.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResReviewDTO {
    private Long id;
    private Long productId;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userAvatarUrl;
    private Integer rating;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
}

