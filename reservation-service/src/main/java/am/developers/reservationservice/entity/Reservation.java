package am.developers.reservationservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String customerPhone;
    private LocalDateTime reservationTime;
    private int numberOfGuests;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private Long tableId;
    private Integer numberOfPeople;

}