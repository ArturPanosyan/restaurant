package am.developers.configserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Configuration {
    @Id
    private String key;
    private String value;}
