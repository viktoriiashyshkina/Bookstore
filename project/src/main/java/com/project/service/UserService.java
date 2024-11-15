package com.project.service;

import com.project.entity.User;
import com.project.exception.UserAlreadyExistsException;
import com.project.repository.UserRepository;
import com.project.util.Role;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {


  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("No user with username " + username);
    }
    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .build();
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    Optional<User> user = userRepository.findByUsername(username);
//
//    if (user.isEmpty()) {
//      throw new UsernameNotFoundException("No ser with username" + username);
//    }
//    return org.springframework.security.core.userdetails.User
//        .withUsername(user.get().getUsername())
//        .password(user.get().getPassword()).build();
//  }
//
//  public Optional<User> findByUsername(String username) {
//    return userRepository.findByUsername(username);
//  }
//
//
//
  public void saveUser(String username, String email, String password, Role role) {
    if (findByUsername(username) != null) {
      throw new UserAlreadyExistsException("Username '" + username + "' is already taken");
    }

    User newUser = new User();
    newUser.setUsername(username);
    newUser.setEmail(email);
    newUser.setPassword(passwordEncoder.encode(password));  // Encrypt password
    newUser.setRole(Role.USER);  // Use the passed role
    userRepository.save(newUser);

    System.out.println(newUser.getUsername());

  }



//  // Register new user with roles and password encoding
//  private User registerNewUser(User user) {
//    if (isUsernameTaken(user.getUsername())) {
//      throw new UserAlreadyExistsException("Username '" + user.getUsername() + "' is already taken");
//    }
//
//    user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password
//    return userRepository.save(user);
//  }
}




