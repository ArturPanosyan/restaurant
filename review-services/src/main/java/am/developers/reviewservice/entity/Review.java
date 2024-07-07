package am.developers.reviewservice.entity;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String userId;
    private String menuItemId;
    private Long restaurantId;
    private String comment;
    private Boolean approved;
    private Integer rating;
    private LocalDateTime reviewDate;
}
