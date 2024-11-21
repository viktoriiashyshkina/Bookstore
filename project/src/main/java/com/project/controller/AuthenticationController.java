package com.project.controller;
import com.project.service.UserService;
import com.project.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthenticationController {

  private final UserService userService;

  public AuthenticationController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String showLoginPage() {
    return "login";
  }

  @PostMapping("/login")
  public String showLoginPage(@RequestParam String username, @RequestParam String password) {
    return "login";
  }

  @GetMapping("/logged-in")
  public String loggedInPage(Model model) {
    String username = SecurityUtils.userIsAuthenticated();
    model.addAttribute("username", username);
    return "logged-in"; // The Thymeleaf template to render
  }

    }




