package pl.JestesPiekna.configuration;

import jakarta.servlet.http.HttpSessionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
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
@EnableMethodSecurity(securedEnabled = true)
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
            adminUserProfile.setLastName("Opala");
            adminUserProfile.setPhone("505597277");
            adminUserProfile.setHealth_condition("Healthy, but allergic to bright light");
            adminUserProfile.setUser(adminEntity);
            adminEntity.setUserProfile(adminUserProfile);

            Authorities adminAuthority = new Authorities();
            adminAuthority.setUsername("admin");
            adminAuthority.setAuthority("ROLE_ADMIN");
            adminAuthority.setUser(adminEntity);

            Authorities ownerAuthority = new Authorities();
            ownerAuthority.setUsername("admin");
            ownerAuthority.setAuthority("ROLE_OWNER");
            ownerAuthority.setUser(adminEntity);

            adminEntity.setAuthoritiesList(Arrays.asList(adminAuthority, ownerAuthority));

            userRepository.save(adminEntity);

        }

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        SessionAuthenticationStrategy sessionAuthenticationStrategy = sessionAuthenticationStrategy();

        http
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation()
                        .migrateSession()
                        .invalidSessionUrl("/logout")
                        .maximumSessions(3)
                        .expiredUrl("/logout")
                        .sessionRegistry(sessionRegistry())
                )
                .addFilterBefore(concurrentSessionFilter(), ConcurrentSessionFilter.class)
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/submitReservation")
                )
                .headers(headers -> headers
                        .addHeaderWriter((request, response) -> response.setHeader("Content-Security-Policy", "script-src 'self'"))
                )
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/homePage").authenticated()
                        .requestMatchers(HttpMethod.GET, "/my/reservations").authenticated()
                        .requestMatchers(HttpMethod.GET, "/submitReservation").authenticated()
                        .requestMatchers(HttpMethod.GET, "/addReservation").authenticated()
                        .requestMatchers(HttpMethod.GET, "/reservations").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/homePage")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "CSRF-TOKEN", "XSRF-TOKEN")
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/")
                )
                .authenticationManager(authenticationManager);

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
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public ConcurrentSessionFilter concurrentSessionFilter() {
        return new ConcurrentSessionFilter(sessionRegistry());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher() {
            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                super.sessionDestroyed(event);
            }
        };
    }
}