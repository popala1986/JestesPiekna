package pl.JestesPiekna.registration.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import pl.JestesPiekna.authorization.dto.AuthoritiesDto;
import pl.JestesPiekna.authorization.mapper.AuthoritiesMapper;
import pl.JestesPiekna.authorization.repository.AuthoritiesRepository;
import pl.JestesPiekna.model.Authorities;
import pl.JestesPiekna.model.User;
import pl.JestesPiekna.model.UserProfile;
import pl.JestesPiekna.registration.dto.RegisterUserDto;
import pl.JestesPiekna.registration.exception.EmailAlreadyExistsException;
import pl.JestesPiekna.registration.exception.UserAlreadyExistsException;
import pl.JestesPiekna.registration.repository.UserRepository;

@Service
public class RegistrationService {

    private final UserDetailsManager userDetailsManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthoritiesRepository authoritiesRepository;

    private final UserRepository userRepository;





    @Autowired
    public RegistrationService(UserDetailsManager userDetailsManager, BCryptPasswordEncoder bCryptPasswordEncoder, AuthoritiesRepository authoritiesRepository, UserRepository userRepository) {
        this.userDetailsManager = userDetailsManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authoritiesRepository = authoritiesRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void registerUser(RegisterUserDto registerUserDto) {


        String hashedPassword = bCryptPasswordEncoder.encode(registerUserDto.getPassword());

        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(hashedPassword);
        user.setEmail(registerUserDto.getEmail());
        user.setEnabled(1);

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(registerUserDto.getFirstName());
        userProfile.setLastName(registerUserDto.getLastName());
        userProfile.setPhone(registerUserDto.getPhone());
        userProfile.setHealth_condition(registerUserDto.getHealthCondition());

        AuthoritiesDto authoritiesDto = new AuthoritiesDto();
        authoritiesDto.setUsername(registerUserDto.getUsername());
        authoritiesDto.setAuthority("ROLE_USER");
        Authorities authorities = AuthoritiesMapper.mapToAuthorities(authoritiesDto);

        user.setUserProfile(userProfile);
        userProfile.setUser(user);
        authorities.setUser(user);

        authoritiesRepository.save(authorities);
        userRepository.save(user);


    }

    private void checkUserUniqueness(RegisterUserDto registerUserDto) {
        if (userDetailsManager.userExists(registerUserDto.getUsername())) {
            throw new UserAlreadyExistsException("This username is already taken");
        }

        if (userRepository.findByEmail(registerUserDto.getEmail()) != null) {
            throw new EmailAlreadyExistsException("This email is already taken ");
        }
    }

}