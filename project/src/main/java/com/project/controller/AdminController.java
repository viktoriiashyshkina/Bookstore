package com.project.controller;

import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

  private final UserService userService;


  @Autowired
  public AdminController(UserService userService) {
    this.userService = userService;
  }

  // Show Admin Dashboard
  @GetMapping("/admin/dashboard")
  public String showAdminDashboard(Model model) {
    return "admin_dashboard"; // Thymeleaf template for the admin dashboard
  }

}
