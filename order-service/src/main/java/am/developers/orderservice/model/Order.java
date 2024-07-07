package am.developers.orderservice.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int quantity;
    private double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private String customerName;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    private Long customerId;
    private Date createdDate;
    private Date updatedDate;
    private LocalDateTime orderTime;
}
