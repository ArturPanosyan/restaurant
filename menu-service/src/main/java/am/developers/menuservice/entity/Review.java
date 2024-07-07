package am.developers.menuservice.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
public class Review {
    @Id
    private Long id;
    private String menuItemId;
    private String userId;
    private String comment;
    private int rating;
    private LocalDateTime date;
}
