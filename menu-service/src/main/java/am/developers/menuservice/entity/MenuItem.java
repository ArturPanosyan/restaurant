package am.developers.menuservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
