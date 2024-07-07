package am.developers.userservice.model;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String error;
    private String error_description;
    private String error_uri;
    private String username;
    private Role role;
}
