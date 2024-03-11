package pl.JestesPiekna.activation.activationService;

import org.springframework.stereotype.Service;
import pl.JestesPiekna.model.User;
import pl.JestesPiekna.registration.repository.UserRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    private final UserRepository userRepository;

    public static final long TOKEN_EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private boolean isTokenExpired(Date expirationDate) {
        if (expirationDate == null) {
            return true;
        }

        Date currentDate = new Date();
        return expirationDate.before(currentDate);
    }

    public boolean isActivationTokenValid(String activationToken) {
        User user = userRepository.findUserByActivationToken(activationToken);
        return user != null && !isTokenExpired(user.getTokenExpirationDate());
    }

    public static String generateActivationToken() {
        return UUID.randomUUID().toString();
    }

    public static Date calculateTokenExpirationDate() {
        long expirationTimeInMillis = System.currentTimeMillis() + TOKEN_EXPIRATION_TIME_MS;
        return new Date(expirationTimeInMillis);
    }
}
