package am.developers.configserver.controller;

import am.developers.configserver.entity.DynamicConfiguration;
import am.developers.configserver.service.DynamicConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamic-configurations")
@RequiredArgsConstructor
public class DynamicConfigurationController {

    private final DynamicConfigurationService dynamicConfigurationService;

    @PostMapping
    public DynamicConfiguration saveConfiguration(@RequestBody DynamicConfiguration configuration) {
        return dynamicConfigurationService.saveConfiguration(configuration);
    }

    @GetMapping("/{key}")
    public DynamicConfiguration getConfiguration(@PathVariable String key) {
        return dynamicConfigurationService.getConfiguration(key);
    }

    @GetMapping
    public List<DynamicConfiguration> getAllConfigurations() {
        return dynamicConfigurationService.getAllConfigurations();
    }

    @DeleteMapping("/{key}")
    public void deleteConfiguration(@PathVariable String key) {
        dynamicConfigurationService.deleteConfiguration(key);
    }
}