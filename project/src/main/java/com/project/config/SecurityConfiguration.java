package com.project.config;


import com.project.service.UserService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//      return new BCryptPasswordEncoder();
//    }
//
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .authorizeRequests(authorizeRequests ->
//            authorizeRequests
//                .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                .requestMatchers("/signup", "/home", "/login", "/searchResults").permitAll()
//                .requestMatchers("/logged-in").authenticated()
//                .requestMatchers("/profile").authenticated()
//                .requestMatchers("/logged-in/updateProfile").authenticated()
//                .requestMatchers("/signup/**").permitAll()
//                .requestMatchers("/books/**").permitAll()
//        )
//        .formLogin(formLogin ->
//            formLogin
//                .loginPage("/login")
//                .defaultSuccessUrl("/logged-in")
//                .loginProcessingUrl("/process-login")
//
//
//        )
//        .logout(logout ->
//            logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//        );
//
//    return http.build();
//  }
//
//  @Bean
//  public DaoAuthenticationProvider daoAuthenticationProvider() {
//    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    provider.setUserDetailsService(userDetailsService); // Use your CustomUserDetailsService
//    provider.setPasswordEncoder(passwordEncoder());
//    return provider;
//  }
//
//  @Bean
//  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//    builder.authenticationProvider(daoAuthenticationProvider());
//    return builder.build();
//  }
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private UserDetailsService userDetailsService;  // Assuming you have a custom UserDetailsService

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/signup", "/home_test","/home", "/login", "/searchResults", "/filterByPrice").permitAll()
                .requestMatchers("/logged-in").authenticated()  // Restrict access to logged-in users
                .requestMatchers("/profile").authenticated()
                .requestMatchers("/logged-in/updateProfile").authenticated()
                .requestMatchers("/books/**").permitAll()
        )
        .formLogin(formLogin ->
            formLogin
                .loginPage("/login")  // Custom login page
                .defaultSuccessUrl("/logged-in")  // Redirect after successful login
                .loginProcessingUrl("/process-login")  // Form action for login
                .successHandler((request, response, authentication) -> {
                  // Redirect based on roles
                  authentication.getAuthorities().stream()
                      .map(Object::toString)
                      .forEach(authority -> {
                        try {
                          if ("ROLE_ADMIN".equals(authority)) {
                            response.sendRedirect("/admin/dashboard");
                          } else if ("ROLE_USER".equals(authority)) {
                            response.sendRedirect("/logged-in");
                          }
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      });
                })
                .permitAll()
        )
        .logout(logout ->
            logout
                .logoutUrl("/logout")  // URL for logging out
                .logoutSuccessUrl("/")  // Redirect after successful logout
        );

    return http.build();
  }

  // Define DaoAuthenticationProvider for authenticating users from the UserDetailsService
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);  // Link the UserDetailsService
    provider.setPasswordEncoder(passwordEncoder());  // Set password encoder (BCrypt)
    return provider;
  }

  // AuthenticationManager bean to integrate the DaoAuthenticationProvider
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.authenticationProvider(daoAuthenticationProvider());
    return builder.build();
  }
}













