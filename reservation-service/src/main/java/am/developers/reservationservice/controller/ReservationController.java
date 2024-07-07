package am.developers.reservationservice.controller;

import am.developers.reservationservice.entity.Reservation;
import am.developers.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;


    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @PutMapping("/{id}")
    public void updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservationService.updateReservation(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Reservation> getReservationsByCustomerId(@PathVariable Long customerId) {
        return reservationService.getReservationsByCustomerId(customerId);
    }

    @GetMapping("/table/{tableId}")
    public List<Reservation> getReservationsByTableId(@PathVariable Long tableId) {
        return reservationService.getReservationsByTableId(tableId);
    }

    @PutMapping("/{reservationId}/status")
    public Reservation updateReservationStatus(@PathVariable Long reservationId, @RequestParam String status) {
        return reservationService.updateReservationStatus(reservationId, status);
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }
}
