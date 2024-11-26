package com.project.service;

import com.project.config.PasswordEncoderConfig;
import com.project.entity.AccountEntity;
import com.project.entity.User;
import com.project.exception.UserAlreadyExistsException;
import com.project.repository.UserRepository;
import com.project.util.Role;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
//public class UserService implements UserDetailsService {
//
////private final UserRepository userRepository;
////private final PasswordEncoder passwordEncoder;
////
////  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
////    this.userRepository = userRepository;
////    this.passwordEncoder = passwordEncoder;
////  }
//  private final AccountService accountService;
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Autowired
//  private BCryptPasswordEncoder passwordEncoder;
//
//  public UserService(AccountService accountService) {
//    this.accountService = accountService;
//  }
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = userRepository.findByUsername(username);
//    if (user == null) {
//      throw new UsernameNotFoundException("User not found");
//    }
//    System.out.println("User retrieved: " + user.getUsername());
//    return new org.springframework.security.core.userdetails.User(
//        user.getUsername(),
//        user.getPassword(),
//        mapRolesToAuthorities(user.getRoles())
//    );
//  }
//
//  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
//    return roles.stream()
//        .map(role -> new SimpleGrantedAuthority(
//            "ROLE_" + role.name())) // Ensure the prefix "ROLE_" is added
//        .collect(Collectors.toList());
//  }
//
//
//  public User findByUsername(String username) {
//    return userRepository.findByUsername(username);
//  }
//
////  public void saveUser(User user) {
////    // Save user along with the associated AccountEntity
////    userRepository.save(user);
////    // Optionally, save the associated AccountEntity if needed
////    accountService.updateAccount(user.getAccount());
////  }
//
//
//  public void saveUser(String username, String email, String password, Role role) {
//    User newUser = new User();
//    newUser.setUsername(username);
//    newUser.setEmail(email);
//    newUser.setPassword(passwordEncoder.encode(password));
//    newUser.setRoles(Set.of(role));
//
//    // Create an AccountEntity and link it to the User
//    AccountEntity account = new AccountEntity();
//    newUser.setAccount(account);  // Link the account to the user
//
//    userRepository.save(newUser);
//
//    System.out.println(newUser.getUsername());
//  }
//}

//  public void saveUser(User user) {
//    user.setPassword(passwordEncoder.encode(user.getPassword()));
//    user.setRoles(Set.of(Role.USER));
//    userRepository.save(user);
//
//  }

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final AccountService accountService;

  @Autowired
  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AccountService accountService) {
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
    System.out.println("User retrieved: " + user.getUsername());
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        mapRolesToAuthorities(user.getRoles())
    );
  }

  // Helper method to map roles to authorities
  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))  // Ensure the "ROLE_" prefix
        .collect(Collectors.toList());
  }

  // Find a user by username (not necessarily for authentication)
  @Transactional
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
}








