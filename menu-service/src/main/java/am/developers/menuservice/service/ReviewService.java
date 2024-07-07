package am.developers.menuservice.service;

import am.developers.menuservice.entity.Review;
import am.developers.menuservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getReviewsForMenuItem(String menuItemId) {
        return reviewRepository.findByMenuItemId(Long.valueOf(menuItemId));
    }

    public Review addReview(Review review) {
        review.setDate(LocalDateTime.now());
        return reviewRepository.save(review);
    }
}
