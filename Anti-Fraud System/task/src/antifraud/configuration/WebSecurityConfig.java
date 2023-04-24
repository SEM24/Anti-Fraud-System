package antifraud.configuration;

import antifraud.antifraudMapper.AntiFraudMappers;
import antifraud.model.enums.UserRoles;
import antifraud.repository.UserEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@AllArgsConstructor
@Slf4j
class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(hc -> hc.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(c -> {
                });
        http.authorizeHttpRequests(req -> req
                .requestMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/auth/user/**")).hasAuthority(UserRoles.ADMINISTRATOR.toString())
                .requestMatchers(antMatcher(HttpMethod.PUT, "/api/auth/access/**")).hasAuthority(UserRoles.ADMINISTRATOR.toString())
                .requestMatchers(antMatcher(HttpMethod.PUT, "/api/auth/role/**")).hasAuthority(UserRoles.ADMINISTRATOR.toString())
                .requestMatchers(HttpMethod.GET, "/api/auth/list").hasAnyAuthority(
                        UserRoles.ADMINISTRATOR.toString(), UserRoles.SUPPORT.toString()
                )

//                .requestMatchers(POST, "/api/auth/list").hasAuthority(UserRoles.SUPPORT.toString())
                .requestMatchers(antMatcher(HttpMethod.POST, "/api/antifraud/transaction/**")).hasAuthority(UserRoles.MERCHANT.toString())
                .requestMatchers("/actuator/shutdown").permitAll()
                .requestMatchers(antMatcher("/h2/**")).permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated());

//        http.authorizeHttpRequests(c -> c
//                .requestMatchers(antMatcher("/h2/**")).permitAll()
//                .requestMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
//                //TODO change the access to roles and another endpoints
//                .requestMatchers("/actuator/shutdown").permitAll()
//                .requestMatchers(antMatcher(HttpMethod.POST, "/api/antifraud/transaction/**")).authenticated()
//                .requestMatchers(antMatcher("/api/**")).authenticated()
//                .requestMatchers(HttpMethod.GET, "/api/auth/list").hasAnyAuthority(
//                        UserRoles.ADMINISTRATOR.toString(), UserRoles.SUPPORT.toString())
//                .requestMatchers(antMatcher("/error")).permitAll()
//                .anyRequest().denyAll());

        http.exceptionHandling(c -> c
                .authenticationEntryPoint((request, response, authException) -> {
                    log.info("Authentication failed {}, message: {}",
                            describe(request)
                            , authException.getMessage());
                    response.sendError(HttpStatus.UNAUTHORIZED.value(),
                            authException.getMessage());
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.info("Authentication failed {}, message: {}",
                            describe(request)
                            , accessDeniedException.getMessage());
                    response.sendError(HttpStatus.FORBIDDEN.value(),
                            accessDeniedException.getMessage());
                })
        );
        return http.build();
    }

    private String describe(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @Bean
    UserDetailsService userDetailsService(UserEntityRepository repository, AntiFraudMappers mapper) {
        return username -> repository.findByUsernameIgnoreCase(username)
                .map(mapper::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}