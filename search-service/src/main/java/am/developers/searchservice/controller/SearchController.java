package am.developers.searchservice.controller;

import am.developers.searchservice.entity.Product;
import am.developers.searchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.SearchResult;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/index")
    public Product indexProduct(@RequestBody Product product) {
        return searchService.indexProduct(product);
    }

    @GetMapping
    public List<Product> searchProducts(@RequestParam String query) {
        return searchService.searchProducts(query);
    }

    @GetMapping
    public List<SearchResult> searchByQuery(@RequestParam String query) {
        return searchService.searchByQuery(query);
    }
}
