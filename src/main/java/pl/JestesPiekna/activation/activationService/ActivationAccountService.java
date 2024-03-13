package pl.JestesPiekna.activation.activationService;

import org.springframework.stereotype.Service;
import pl.JestesPiekna.model.User;
import pl.JestesPiekna.registration.repository.UserRepository;

@Service
public class ActivationAccountService {
    private final UserRepository userRepository;

    public ActivationAccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void activateAccount(String activationToken) {
        User user = userRepository.findUserByActivationToken(activationToken);

        if (user != null) {
            user.setEnabled(1);
            userRepository.save(user);
        }
    }

}
