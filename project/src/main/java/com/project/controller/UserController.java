package com.project.controller;
import static com.project.util.SecurityUtils.userIsAuthenticated;

import com.project.entity.AccountEntity;
import com.project.entity.User;
import com.project.service.AccountService;
import com.project.service.UserService;
import com.project.util.Role;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
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

  public UserController(UserService userService, AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder, AccountService accountService) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.accountService = accountService;
  }

  @GetMapping("/signup")
  public String getSignUp(Model model) {
    model.addAttribute("user", new User());
    return "signup";
  }


//  @PostMapping("/signup/user")
//  public String signup(@RequestParam String username,
//      @RequestParam String email,
//      @RequestParam String password,
//      @RequestParam(defaultValue = "USER") String role,
//      Model model) {
//    try {
//      // Convert role string to Role enum
//      Role userRole = Role.valueOf(role.toUpperCase());
//      userService.saveUser(username, email, password, userRole);
//      model.addAttribute("message", "User registered successfully!");
//      return "redirect:/logged-in";
//    } catch (
//        IllegalArgumentException e) {
//      model.addAttribute("error", "Invalid role selected.");
//      return "signup";
//    } catch (
//        Exception e) {
//      model.addAttribute("error", e.getMessage());
//      return "signup";
//    }
//  }

  @PostMapping("/signup/user")
  public String signup(@RequestParam String username,
      @RequestParam String email,
      @RequestParam String password,
      @RequestParam(defaultValue = "USER") String role,
      Model model) {
    try {
      // Convert role string to Role enum
      Role userRole = Role.valueOf(role.toUpperCase());
      // Create the User entity
      User newUser = new User();
      newUser.setUsername(username);
      newUser.setEmail(email);
      newUser.setPassword(passwordEncoder.encode(password)); // Encrypt the password
      newUser.setRoles(Set.of(userRole)); // Assign the role to the user

      // Create an associated AccountEntity for the user
      AccountEntity account = new AccountEntity();

      // Link the account to the user
      newUser.setAccount(account);
      // Save the user along with the account
      userService.saveUser(newUser);
     // Optionally, you can save the account here if necessary
      accountService.updateAccount(account);
      model.addAttribute("message", "User registered successfully!");
      return "redirect:/logged-in";
    } catch (IllegalArgumentException e) {
      model.addAttribute("error", "Invalid role selected.");
      return "signup";
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "signup";
    }
  }
}


//  @PostMapping("/login")
//  public String login(@RequestParam String username, @RequestParam String password, @RequestParam Role role, Model model) {
//    User user = userService.findByUsername(username);
//    user.getPassword();
//    return "redirect:/logged-in";
//
//  }

//  @PreAuthorize("isAuthenticated()")
//  @GetMapping("/logged-in")
//  public String homePage(Model model) {
//    String username = SecurityContextHolder.getContext().getAuthentication().getName();
//    model.addAttribute("username", username);
//    return "logged-in"; // Show home page
//  }











