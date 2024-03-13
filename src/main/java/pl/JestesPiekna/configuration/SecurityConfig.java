package pl.JestesPiekna.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserDetailsManager userDetailsManager;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsManager userDetailsManager) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))
                .httpBasic(Customizer.withDefaults())
                .csrf(csrfConfigurer -> csrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .headers(headersConfigurer -> headersConfigurer
                        .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy", "script-src 'self'"))
                )
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry
                        -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.GET, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/registration").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                        .requestMatchers(HttpMethod.GET, "/YouAreBeautiful").permitAll()
                        .requestMatchers(HttpMethod.GET, "/upload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploadPhoto").permitAll()
                        .requestMatchers(HttpMethod.GET, "/static/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/upload").permitAll()
                        .requestMatchers(HttpMethod.POST, "/uploadPhoto").permitAll()
                        .requestMatchers(HttpMethod.POST, "/static/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/securedMainPage").authenticated()
                        .anyRequest().permitAll())
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "CSRF-TOKEN", "XSRF-TOKEN")
                        .clearAuthentication(true)
                        .permitAll());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);


    }
}








