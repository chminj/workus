package com.example.workus.security;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .disable())
.authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/login", "/signup", "/findpw", "/resources/**", "/ajax/user/**", "/ajax/send-sms").permitAll()
                .requestMatchers("/").permitAll() // 루트 URL에 대한 요청을 허용
                .anyRequest().authenticated())
            .formLogin(login -> login
                .loginPage("/login")
                .usernameParameter("id")
                .passwordParameter("password")
                .failureUrl("/login?error=fail")
                .defaultSuccessUrl("/home", true)
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/login?error=expired")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect(request.getContextPath() + "/login");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
//                    response.sendRedirect(request.getContextPath() + "/login?error=access-denied");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                })
            );

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}