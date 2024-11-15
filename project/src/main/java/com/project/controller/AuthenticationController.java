package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

  @GetMapping("/login")
  public String showLoginPage() {
//    if (userIsAuthenticated()) {
//      return "redirect:/user/home";  // Redirect authenticated users to home
//    }
    return "login";
  }


}
