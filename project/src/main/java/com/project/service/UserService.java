package com.project.service;

import com.project.entity.AccountEntity;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.util.Role;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountService accountService;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccountService accountService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.accountService = accountService;
  }

  // Method to load user by username for authentication
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    Set<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())) // Prefix "ROLE_"
        .collect(Collectors.toSet());

    logger.info("Loading user: {}", user.getUsername());
    user.getRoles().forEach(role -> logger.info("Role: {}", role));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        authorities
    );
  }


  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }


  // Find a user by username (not necessarily for authentication)
  @Transactional
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }


  public void saveUser(String username, String email, String password, Role role) {
    // Validate inputs
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already exists.");
    }
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already exists.");
    }
    if (role == null) {
      throw new IllegalArgumentException("Role must not be null.");
    }
    // Create the user and associated account
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setEmail(email);
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setRoles(Set.of(role));

    AccountEntity account = createAccountForUser(); // Decoupled account creation
    newUser.setAccount(account);

    // Save the user
    userRepository.save(newUser);

    // Log the action
    logger.info("New user registered: {}", username);
  }

  private AccountEntity createAccountForUser() {
    return new AccountEntity();
  }

  public long getUserCount() {
    return userRepository.count();
  }

  // Get the user's account balance
  public BigDecimal getUserBalance(String username) {
    User user = userRepository.findByUsername(username);
    return user.getAccount().getBalance();
  }

  // Get the authenticated user's username
  public String getAuthenticatedUsername() {
    String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    return username;
  }

}








