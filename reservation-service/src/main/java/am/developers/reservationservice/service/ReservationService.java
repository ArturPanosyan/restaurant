package am.developers.reservationservice.service;

import am.developers.reservationservice.entity.Reservation;
import am.developers.reservationservice.entity.ReservationStatus;
import am.developers.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka.topic.reservation}")
    private String reservationTopic;
    private static final String RESERVATION_CACHE = "ReservationCache";

    public Reservation addReservation(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        kafkaTemplate.send(reservationTopic, "Add", savedReservation);
        redisTemplate.opsForHash().put(RESERVATION_CACHE, savedReservation.getId(), savedReservation);
        redisTemplate.expire(RESERVATION_CACHE, 10, TimeUnit.MINUTES);
        return savedReservation;
    }

    public List<Reservation> getAllReservations() {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(RESERVATION_CACHE))) {
            Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(RESERVATION_CACHE);
            return hashEntries.values().stream()
                    .map(entry -> (Reservation) entry)
                    .collect(Collectors.toList());
        }
        List<Reservation> reservationList = reservationRepository.findAll();
        reservationList.forEach(item -> redisTemplate.opsForHash().put(RESERVATION_CACHE, item.getId(), item));
        return reservationList;
    }

    public Reservation updateReservation(Long reservationId, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.setTableId(reservationDetails.getTableId());
        reservation.setReservationTime(reservationDetails.getReservationTime());
        reservation.setCustomerId(reservationDetails.getCustomerId());
        reservation.setCustomerPhone(reservationDetails.getCustomerPhone());
        reservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
        reservation.setStatus(reservationDetails.getStatus());
        Reservation updatedReservation = reservationRepository.save(reservation);
        kafkaTemplate.send(reservationTopic, "Update", updatedReservation);
        redisTemplate.opsForHash().put(RESERVATION_CACHE, updatedReservation.getId(), updatedReservation);
        return updatedReservation;
    }

    public void deleteReservation(Long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        reservationRepository.deleteById(reservationId);
        optionalReservation.ifPresent(reservation -> kafkaTemplate.send(reservationTopic, "Delete", reservation));
        redisTemplate.opsForHash().delete(RESERVATION_CACHE, reservationId);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public Reservation createReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setReservationTime(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByCustomerId(Long customerId) {
        return reservationRepository.findByCustomerId(customerId);
    }

    public List<Reservation> getReservationsByTableId(Long tableId) {
        return reservationRepository.findByTableId(tableId);
    }

    public Reservation updateReservationStatus(Long reservationId, String status) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.setStatus(reservation.getStatus());
        return reservationRepository.save(reservation);
    }

}
