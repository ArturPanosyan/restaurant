package am.developers.reviewservice.service;

import am.developers.reviewservice.entity.Review;
import am.developers.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SimpMessagingTemplate template;
    @Value("${kafka.topic.review}")
    private String reviewTopic;


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Long reviewId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        Review updatedReview = reviewRepository.save(review);
        kafkaTemplate.send(reviewTopic, "Update", updatedReview);
        return updatedReview;
    }

    public void deleteReview(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        reviewRepository.deleteById(reviewId);
        optionalReview.ifPresent(review -> kafkaTemplate.send(reviewTopic, "Delete", review));
    }

    public Review createReview(Review review) {
        review.setReviewDate(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByRestaurantId(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public List<Review> getReviewsByCustomerId(Long customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }

    public List<Review> getReviewsForMenuItem(String menuItemId) {
        return reviewRepository.findByMenuItemIdAndApproved(menuItemId, true);
    }

    public Review addReview(Review review) {
        review.setReviewDate(LocalDateTime.now());
        review.setApproved(false); // Отзыв должен быть одобрен
        Review savedReview = reviewRepository.save(review);

        // Уведомление администраторов о новом отзыве
        template.convertAndSend("/topic/review-notifications", savedReview);
        return savedReview;
    }

    public Review approveReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setApproved(true);
        return reviewRepository.save(review);
    }
}
