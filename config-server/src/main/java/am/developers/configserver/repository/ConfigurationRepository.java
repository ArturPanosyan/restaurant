package am.developers.configserver.repository;

import am.developers.configserver.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}
