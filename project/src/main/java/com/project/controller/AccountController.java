package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

  @GetMapping("/login/user")
  public String showLoginPage() {
//    if (userIsAuthenticated()) {
//      return "redirect:/user/home";  // Redirect authenticated users to home
//    }
    return "login-user";  // Show login page for unauthenticated users
  }


}
