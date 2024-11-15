package com.project.config;


import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  // Define PasswordEncoder bean
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder(); // Or use a custom PasswordEncoder
//  }

//   Bean for AuthenticationManager
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authManagerBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class);
    authManagerBuilder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    return authManagerBuilder.build();
  }

  // Security configuration setup
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
      http
          .authorizeHttpRequests(authorize -> authorize
              .requestMatchers("/admin/**").hasRole("ADMIN")
              .requestMatchers("/user/**").hasRole("USER")
              .requestMatchers("/login", "/signup", "/").permitAll()
              .requestMatchers("signup/**").permitAll()
              .anyRequest().authenticated()
          )
          .formLogin(login -> login
              .loginPage("/login")
              .defaultSuccessUrl("/home")
              .loginProcessingUrl("/process-login")
              .permitAll()
          )
          .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/")
              .permitAll()
          );

      return http.build();
    }

  }


