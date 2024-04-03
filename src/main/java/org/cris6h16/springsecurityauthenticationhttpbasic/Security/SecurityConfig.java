package org.cris6h16.springsecurityauthenticationhttpbasic.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // if we use httpBasic auth
        // - make sure to use HTTPS ( avoid man-in-the-middle )
        return http
                .httpBasic(Customizer.withDefaults()) //when is logged uses JSESSIONID
//                .authorizeHttpRequests(authRequest -> authRequest
//                        .requestMatchers("/api").permitAll()
//                        .requestMatchers("/api/**").authenticated())
                .build();
    }

    /* InMemoryUserDetailsManager is a simple in-memory implementation of UserDetailsService
     and creates users easily.. for testing purposes

     you implemented it, then Spring Security will use it to authenticate users, also
     Spring Security won't generate a default login credential ( user, password-in-logs )
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder() // you can do your own impl for add more attributes
                .username("cri6h16")
                .password("cri6h16")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // implementations of UserDetailsService Example non-in-memory
    /*
    @Service
    public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0])) // Assuming roles are stored as a collection in User entity
                .build();
    }
}


     */
}
