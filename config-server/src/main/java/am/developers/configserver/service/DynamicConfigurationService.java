package am.developers.configserver.service;

import am.developers.configserver.entity.DynamicConfiguration;
import am.developers.configserver.repository.DynamicConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicConfigurationService {

    private final DynamicConfigurationRepository dynamicConfigurationRepository;

    public DynamicConfiguration saveConfiguration(DynamicConfiguration configuration) {
        return dynamicConfigurationRepository.save(configuration);
    }

    public DynamicConfiguration getConfiguration(String key) {
        return dynamicConfigurationRepository.findById(key).orElse(null);
    }

    public List<DynamicConfiguration> getAllConfigurations() {
        return dynamicConfigurationRepository.findAll();
    }

    public void deleteConfiguration(String key) {
        dynamicConfigurationRepository.deleteById(Long.valueOf(key));
    }
}
