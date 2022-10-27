package com.kumoh.tour.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumoh.tour.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class WebSecurityConfig {
    private final ObjectMapper objectMapper;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebSecurityConfig(ObjectMapper objectMapper,
                             JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.objectMapper = objectMapper;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .httpBasic()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/tour/**","/auth/**").permitAll()
                .anyRequest()
                .authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint((request, response,authException) -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("status", HttpServletResponse.SC_FORBIDDEN);
                    data.put("message", authException.getMessage());

                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    objectMapper.writeValue(response.getOutputStream(), data);
                });

        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }
}
