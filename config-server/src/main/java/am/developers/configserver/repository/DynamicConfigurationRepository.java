package am.developers.configserver.repository;

import am.developers.configserver.entity.DynamicConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicConfigurationRepository extends JpaRepository<DynamicConfiguration, Long> {
}
