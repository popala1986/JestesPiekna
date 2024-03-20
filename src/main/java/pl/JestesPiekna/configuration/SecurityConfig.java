package pl.JestesPiekna.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
public class SecurityConfig {

    private final UserRepository userRepository;

    private final AuthoritiesRepository authoritiesRepository;

    public SecurityConfig(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false));

         http
                 .httpBasic(Customizer.withDefaults())
                 .csrf(csrfConfigurer -> csrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                 .headers(headersConfigurer -> headersConfigurer
                         .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy", "script-src 'self'")));

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/YouAreBeautiful").permitAll()
                        .requestMatchers(HttpMethod.GET,"/homePage").authenticated()
                        .anyRequest().permitAll()

                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/homePage"))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "CSRF-TOKEN", "XSRF-TOKEN")
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/YouAreBeautiful"));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setEnableAuthorities(true);
        userDetailsManager.setEnableGroups(false);

        Optional<pl.JestesPiekna.model.User> existingAdmin = userRepository.findByUsername("admin");

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




