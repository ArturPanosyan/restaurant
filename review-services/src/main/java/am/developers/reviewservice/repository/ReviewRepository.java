package am.developers.reviewservice.repository;

import am.developers.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRestaurantId(Long restaurantId);
    List<Review> findByCustomerId(Long customerId);
    List<Review> findByMenuItemIdAndApproved(String menuItemId, boolean approved);
}
