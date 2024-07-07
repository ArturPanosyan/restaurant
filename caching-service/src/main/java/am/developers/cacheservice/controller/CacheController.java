package am.developers.cacheservice.controller;

import am.developers.cacheservice.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;

    @PostMapping
    public void cacheData(@RequestParam String key, @RequestBody Object data, @RequestParam long ttl) {
        cacheService.cacheData(key, data, ttl);
    }

    @GetMapping
    public Object getData(@RequestParam String key) {
        return cacheService.getData(key);
    }

    @DeleteMapping
    public void deleteData(@RequestParam String key) {
        cacheService.deleteData(key);
    }
}
