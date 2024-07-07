package am.developers.searchservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "menuitem")
@Data
public class MenuItemIndex {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
}
