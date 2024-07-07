package am.developers.userservice.service;

import org.springframework.stereotype.Service;

@Service
public class TwoFactorAuthenticationService {

    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    public String generateSecretKey() {
        return googleAuthenticator.createCredentials().getKey();
    }

    public boolean validateCode(String secretKey, int verificationCode) {
        return googleAuthenticator.authorize(secretKey, verificationCode);
    }
}
