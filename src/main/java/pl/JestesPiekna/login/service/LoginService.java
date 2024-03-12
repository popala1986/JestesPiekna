package pl.JestesPiekna.login.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.JestesPiekna.login.repository.LoginRepository;
import pl.JestesPiekna.model.User;

import java.util.Optional;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginService(LoginRepository loginRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.loginRepository = loginRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean isPasswordCorrect(String username, String password) {
        Optional<User> optionalUser = loginRepository.findByUsername(username);

        return optionalUser
                .filter(user -> bCryptPasswordEncoder.matches(password, user.getPassword()))
                .isPresent();
    }


}
