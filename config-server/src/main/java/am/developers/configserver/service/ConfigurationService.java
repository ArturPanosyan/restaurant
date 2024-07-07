package am.developers.configserver.service;

import am.developers.configserver.entity.Configuration;
import am.developers.configserver.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public Configuration saveConfiguration(Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    public Configuration getConfiguration(String key) {
        return configurationRepository.findById(Long.valueOf(key)).orElse(null);
    }

    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    public void deleteConfiguration(String key) {
        configurationRepository.deleteById(Long.valueOf(key));
    }
}
