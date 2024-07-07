package am.developers.orderservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Order order;
    private Long orderId;
    private Long productId;
    private Long menuItemId;
    private Integer quantity;
    private Double price;
}
