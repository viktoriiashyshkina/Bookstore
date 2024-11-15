package com.project.controller;
import com.project.service.UserService;
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
     Model model) {

      userService.saveUser(username, email, password);
      return "redirect:/login?success=true";  // Redirect to login page on success

  }


  // Redirect users based on roles
  @GetMapping("/home")
  public String homePage() {
    return "home"; // Show home page
  }


}




