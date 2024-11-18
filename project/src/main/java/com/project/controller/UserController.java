package com.project.controller;
import com.project.entity.User;
import com.project.service.UserService;
import com.project.util.Role;
import java.util.Set;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/signup")
  public String getSignUp() {
    return "signup";
  }

  @PostMapping("/signup/user")
  public String signup(@RequestParam String username,
      @RequestParam String email,
      @RequestParam String password,
      @RequestParam(defaultValue = "USER") String role,
      Model model) {
    try {
      // Convert role string to Role enum
      Role userRole = Role.valueOf(role.toUpperCase());
      userService.saveUser(username, email, password, userRole);
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

//  @PostMapping("/login")
//  public String login(@RequestParam String username, @RequestParam String password, @RequestParam Role role, Model model) {
//    User user = userService.findByUsername(username);
//    user.getPassword();
//    return "redirect:/logged-in";
//
//  }

//@PreAuthorize("isAuthenticated()")
//@Secured("USER")
  @GetMapping("/logged-in")
  public String homePage(Model model) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    model.addAttribute("username", username);
    return "logged-in"; // Show home page
  }






}




