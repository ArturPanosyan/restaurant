package am.developers.configserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DynamicConfiguration {
    @Id
    private String key;
    private String value;
    private String description;
}