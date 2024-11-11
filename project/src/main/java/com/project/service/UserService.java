package com.project.service;

import com.project.entity.User;
import com.project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = getEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("No user with username " + email);
    }
    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .build();
  }

  public User getEmail(String email) {
    return userRepository.findByEmail(email);
  }
}





