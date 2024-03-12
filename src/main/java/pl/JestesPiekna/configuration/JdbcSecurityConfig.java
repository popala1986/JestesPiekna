package pl.JestesPiekna.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import pl.JestesPiekna.activation.activationService.TokenService;
import pl.JestesPiekna.authorization.repository.AuthoritiesRepository;
import pl.JestesPiekna.model.Authorities;
import pl.JestesPiekna.model.User;
import pl.JestesPiekna.model.UserProfile;
import pl.JestesPiekna.registration.repository.UserRepository;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;


@Configuration
@EnableWebSecurity
@Profile("prod")
public class JdbcSecurityConfig {

    private final UserRepository userRepository;

    private final AuthoritiesRepository authoritiesRepository;



    public JdbcSecurityConfig(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setEnableAuthorities(true);
        userDetailsManager.setEnableGroups(false);

        Optional<User> existingAdmin = userRepository.findByUsername("admin");

        if (existingAdmin.isEmpty()) {
            User adminEntity = new User();
            adminEntity.setUsername("admin");
            adminEntity.setPassword(bCryptPasswordEncoder().encode("Password10!"));
            adminEntity.setEmail("pawel@wp.pl");
            adminEntity.setEnabled(1);

            String activationToken = TokenService.generateActivationToken();
            Date expirationDate = TokenService.calculateTokenExpirationDate();
            adminEntity.setActivationToken(activationToken);
            adminEntity.setTokenExpirationDate(expirationDate);

            UserProfile adminUserProfile = new UserProfile();
            adminUserProfile.setFirstName("Paweł");
            adminUserProfile.setLastName("Kowalski");
            adminUserProfile.setPhone("502456897");
            adminUserProfile.setHealth_condition("Healthy");
            adminUserProfile.setUser(adminEntity);
            adminEntity.setUserProfile(adminUserProfile);

            Authorities adminAuthority = new Authorities();
            adminAuthority.setUsername("admin");
            adminAuthority.setAuthority("ROLE_ADMIN");
            adminAuthority.setUser(adminEntity);

            Authorities userAuthority = new Authorities();
            userAuthority.setUsername("admin");
            userAuthority.setAuthority("ROLE_USER");
            userAuthority.setUser(adminEntity);


            adminEntity.setAuthoritiesList(Arrays.asList(adminAuthority, userAuthority));

            userRepository.save(adminEntity);

        }

        return userDetailsManager;
    }
}