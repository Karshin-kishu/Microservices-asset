package com.example.assetservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.assetservice.security.AuthEntryPoint;
import com.example.assetservice.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtFilter jwtFilter;
    private final AuthEntryPoint authEntryPoint;

    public SecurityConfig(JwtFilter jwtFilter, AuthEntryPoint authEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))

            .authorizeHttpRequests(auth -> auth
                // health endpoints optional; keep if you want
                .requestMatchers("/actuator/health").permitAll()
                // everything else must be authenticated via introspection
                .anyRequest().authenticated()
            )

            // Our introspection-based filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
