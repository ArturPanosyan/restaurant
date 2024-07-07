package am.developers.userservice.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class AuthRequest {

    private String username;
    private String password;
}
