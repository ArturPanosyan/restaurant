package am.developers.reportingservice.repository;

import am.developers.reportingservice.model.SaleData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SaleDataRepository extends JpaRepository<SaleData, Integer> {
    List<SaleData> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}
