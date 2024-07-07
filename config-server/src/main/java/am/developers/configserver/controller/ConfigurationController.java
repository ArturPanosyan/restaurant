package am.developers.configserver.controller;

import am.developers.configserver.entity.Configuration;
import am.developers.configserver.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configurations")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @PostMapping
    public Configuration saveConfiguration(@RequestBody Configuration configuration) {
        return configurationService.saveConfiguration(configuration);
    }

    @GetMapping("/{key}")
    public Configuration getConfiguration(@PathVariable String key) {
        return configurationService.getConfiguration(key);
    }

    @GetMapping
    public List<Configuration> getAllConfigurations() {
        return configurationService.getAllConfigurations();
    }

    @DeleteMapping("/{key}")
    public void deleteConfiguration(@PathVariable String key) {
        configurationService.deleteConfiguration(key);
    }
}
