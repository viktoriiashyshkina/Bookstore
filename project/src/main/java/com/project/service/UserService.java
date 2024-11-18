package com.project.service;

import com.project.entity.User;
import com.project.exception.UserAlreadyExistsException;
import com.project.repository.UserRepository;
import com.project.util.Role;
import java.util.Collection;
import java.util.Optional;
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
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
      return roles.stream()
          .map(role -> new SimpleGrantedAuthority(role.name())) // Ensure the prefix "ROLE_" is added
          .collect(Collectors.toList());
    }


    public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }


  public void saveUser(String username, String email, String password, Role role) {

    User newUser = new User();
    newUser.setUsername(username);
    newUser.setEmail(email);
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setRoles(Set.of(role));
    userRepository.save(newUser);

    System.out.println(newUser.getUsername());

  }


}




