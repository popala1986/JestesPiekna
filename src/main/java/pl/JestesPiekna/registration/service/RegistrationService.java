package pl.JestesPiekna.registration.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.JestesPiekna.authorization.dto.AuthoritiesDto;
import pl.JestesPiekna.authorization.mapper.AuthoritiesMapper;
import pl.JestesPiekna.authorization.repository.AuthoritiesRepository;
import pl.JestesPiekna.model.Authorities;
import pl.JestesPiekna.model.User;
import pl.JestesPiekna.model.UserProfile;
import pl.JestesPiekna.registration.dto.RegisterUserDto;
import pl.JestesPiekna.registration.repository.UserRepository;

@Service
public class RegistrationService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthoritiesMapper authoritiesMapper;

    private final AuthoritiesRepository authoritiesRepository;

    private final UserRepository userRepository;

    public RegistrationService(BCryptPasswordEncoder bCryptPasswordEncoder, AuthoritiesMapper authoritiesMapper, AuthoritiesRepository authoritiesRepository, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authoritiesMapper = authoritiesMapper;
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

}