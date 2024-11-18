package com.project.controller;

import static com.project.util.SecurityUtils.userIsAuthenticated;

import com.project.service.UserService;
import org.springframework.stereotype.Controller;
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
    if (userIsAuthenticated()) {
      return "redirect:/logged-in";
    }
   return "login";
    }

    }




