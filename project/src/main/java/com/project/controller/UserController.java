package com.project.controller;
import static com.project.util.SecurityUtils.userIsAuthenticated;

import com.project.entity.AccountEntity;
import com.project.entity.User;
import com.project.service.AccountService;
import com.project.service.UserService;
import com.project.util.Role;
import com.project.util.SecurityUtils;
import java.util.Set;

import javax.management.remote.JMXAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final AccountService accountService;
  private final AuthenticationManager authenticationManager;

  public UserController(UserService userService,
      PasswordEncoder passwordEncoder, AccountService accountService,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.accountService = accountService;

    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/signup")
  public String getSignUp(Model model) {
    model.addAttribute("_csrf",
        SecurityContextHolder.getContext().getAuthentication().getCredentials());
    return "signup";
  }


  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Handles user signup requests.
   *
   * @param username The username of the new user.
   * @param email    The email of the new user.
   * @param password The password of the new user.
   * @param role     The role of the new user (default: USER).
   * @param model    The Spring model to pass attributes to the view.
   * @return A redirect or view name depending on the outcome.
   */
  @PostMapping("/signup/user")
  public String signup(
      @RequestParam String username,
      @RequestParam String email,
      @RequestParam String password,
      @RequestParam(defaultValue = "USER") String role,
      Model model) {
    try {
      // Attempt to save the user
      userService.saveUser(username, email, password, Role.valueOf(role.toUpperCase()));
      model.addAttribute("message", "User registered successfully!");

      // Authenticate the user
      UserDetails user = userService.loadUserByUsername(username);
      Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
          username,
          password,
          user.getAuthorities()// Fetch roles as authorities
      );
      Authentication authentication = authenticationManager.authenticate(authenticationToken);

      SecurityContextHolder.getContext().setAuthentication(authentication);
      logger.info("New user signed up and authenticated: {}", username);
//      logger.info("Redirecting to: /logged-in");
      return "redirect:/logged-in"; // Redirect to login after successful signup
    } catch (IllegalArgumentException e) {
      model.addAttribute("error", "Invalid role or user already exists.");
      logger.error("Error during signup: {}", e.getMessage());
    } catch (Exception e) {
      model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
      logger.error("Unexpected error during signup", e);
    }
    return "signup"; // Return to signup page with error message
  }
}














