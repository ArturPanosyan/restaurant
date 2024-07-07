package am.developers.customerservice.repository;

import am.developers.customerservice.entity.CustomerPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerPreferencesRepository extends JpaRepository<CustomerPreferences, Long> {
    Optional<CustomerPreferences> findByCustomerId(String customerId);

}
