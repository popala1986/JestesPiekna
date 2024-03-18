package pl.JestesPiekna.login.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.JestesPiekna.login.repository.LoginRepository;
import pl.JestesPiekna.model.User;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = loginRepository.findByUsername(username);

        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                user.getAuthorities()
        );
    }
}
