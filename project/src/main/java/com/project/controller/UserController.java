package com.project.controller;
import com.project.service.UserService;
import com.project.util.Role;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
      @RequestParam Role role,  // Role enum
      Model model) {
    try {
      // Register the user with the selected role
      userService.saveUser(username, email, password, role);
      model.addAttribute("message", "User registered successfully!");
      return "redirect:/login?success=true";  // Redirect to login page on success
    } catch (IllegalArgumentException e) {
      model.addAttribute("error", "Invalid role selected.");
      return "signup";  // Stay on signup page if the role is invalid
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "signup";  // Stay on signup page if other errors occur
    }
  }


  // Redirect users based on roles
  @GetMapping("/home")
  public String homePage() {
    return "home"; // Show home page
  }


}




