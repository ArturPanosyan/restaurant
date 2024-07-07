package am.developers.searchservice.repository;

import am.developers.searchservice.entity.Search;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.naming.directory.SearchResult;
import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Search, Long> {
    List<SearchResult> findByQueryContaining(String query);
}
