package pl.JestesPiekna.registration.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import pl.JestesPiekna.activation.activationService.TokenService;
import pl.JestesPiekna.authorization.repository.AuthoritiesRepository;
import pl.JestesPiekna.model.Authorities;
import pl.JestesPiekna.model.User;
import pl.JestesPiekna.model.UserProfile;
import pl.JestesPiekna.registration.dto.RegisterUserDto;
import pl.JestesPiekna.registration.exception.*;
import pl.JestesPiekna.registration.repository.UserProfileRepository;
import pl.JestesPiekna.registration.repository.UserRepository;

import java.util.Date;

@Service
public class RegistrationService {



    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthoritiesRepository authoritiesRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public RegistrationService(BCryptPasswordEncoder bCryptPasswordEncoder, AuthoritiesRepository authoritiesRepository, UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authoritiesRepository = authoritiesRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }


    @Transactional
    public void registerUser(RegisterUserDto registerUserDto) {

        checkUserUniqueness(registerUserDto);

        validateRegistrationData(registerUserDto);


        String hashedPassword = bCryptPasswordEncoder.encode(registerUserDto.getPassword());

        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(hashedPassword);
        user.setEmail(registerUserDto.getEmail());
        user.setEnabled(1);

        String activationToken = TokenService.generateActivationToken();
        Date expirationDate = TokenService.calculateTokenExpirationDate();
        user.setActivationToken(activationToken);
        user.setTokenExpirationDate(expirationDate);

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(registerUserDto.getFirstName());
        userProfile.setLastName(registerUserDto.getLastName());
        userProfile.setPhone(registerUserDto.getPhone());
        userProfile.setHealth_condition(registerUserDto.getHealthCondition());

        Authorities authorities = new Authorities();
        authorities.setUsername(registerUserDto.getUsername());
        authorities.setAuthority("ROLE_USER");

        user.setUserProfile(userProfile);
        userProfile.setUser(user);
        authorities.setUser(user);

        authoritiesRepository.save(authorities);
        userRepository.save(user);


    }

    private void checkUserUniqueness(RegisterUserDto registerUserDto) {
        if (userRepository.findByUsername(registerUserDto.getUsername()) != null) {
            throw new UserAlreadyExistsException("This username is already taken");
        }

        if (userRepository.findByEmail(registerUserDto.getEmail()) != null) {
            throw new EmailAlreadyExistsException("This email is already taken ");
        }

        if (userProfileRepository.findByPhone(registerUserDto.getPhone()) != null) {
            throw new PhoneNumberAlreadyExistsException("This phone number is already use");
        }
    }

    private void validateRegistrationData(RegisterUserDto registerUserDto) {
        if (registerUserDto.getUsername().length() < 5) {
            throw new InvalidUsernameException("The username must have at least 5 character");
        }

        if (registerUserDto.getPassword().length() < 8 || registerUserDto.getPassword().length() > 20) {
            throw new InvalidPasswordException("Password must be between 8 and 20 characters");
        }

        if (!registerUserDto.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new InvalidEmailException("please enter a valid email address");
        }

        if (registerUserDto.getFirstName() != null && registerUserDto.getFirstName().length() < 1) {
            throw  new InvalidFirstNameLenghtException("First name must have at least 1 character");
        }

        if (registerUserDto.getLastName() != null && registerUserDto.getLastName().length() < 1) {
            throw new InvalidLastNameLenghtException("Last name must have at least 1 character");
        }

        if (registerUserDto.getPhone().length() != 9) {
            throw new InvalidPhoneNumberLenghtException("The phone number can only contain 9 digits");
        }

    }

}