package com.project.controller;

import com.project.entity.OrderEntity;
import com.project.service.BookService;
import com.project.service.OrderService;
import com.project.service.UserService;

import com.project.util.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

  private final UserService userService;
  private final BookService bookService;
  private final OrderService orderService;


  private final AuthenticationManager authenticationManager;

  public AdminController(UserService userService, BookService bookService,
      OrderService orderService, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.bookService = bookService;

    this.orderService = orderService;
    this.authenticationManager = authenticationManager;
  }

  // Show Admin Dashboard
  @GetMapping("/admin/dashboard")
  public String showAdminDashboard(Model model) {

    // Fetch counts for users, books, and orders
    long userCount = userService.getUserCount();
    long bookCount = bookService.getBookCount();
    long orderCount = orderService.getOrderCount();

////    // Fetch recent orders, e.g., the last 10 orders
    List<OrderEntity> recentOrders = orderService.getRecentOrders();

    // Add recent orders to the model
    model.addAttribute("recentOrders", recentOrders);

    // Add attributes to the model
    model.addAttribute("userCount", userCount);
    model.addAttribute("bookCount", bookCount);
    model.addAttribute("orderCount", orderCount);
    model.addAttribute("recentOrders", recentOrders);
    return "admin_dashboard"; // Thymeleaf template for the admin dashboard
  }


  private static final Logger logger = LoggerFactory.getLogger(UserController.class);


  @PostMapping("/admin/signup")
  public String signup(
      @RequestParam String username,
      @RequestParam String email,
      @RequestParam String password,
      @RequestParam(defaultValue = "ADMIN") String role,
      Model model) {
    try {
      // Attempt to save the user
      userService.saveUser(username, email, password, Role.ADMIN);
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
      logger.info("New admin user signed up and authenticated: {}", username);


//      logger.info("Redirecting to: /logged-in");

      return "redirect:/admin/home?signupSuccess"; // Redirect to login after successful signup

    } catch (IllegalArgumentException e) {
      model.addAttribute("error", "Invalid role or user already exists.");
      logger.error("Error during signup: {}", e.getMessage());
      return "redirect:/admin/home?signupError";
    } catch (Exception e) {
      model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
      logger.error("Unexpected error during signup", e);
      return "redirect:/admin/home?unexpectedError";
    }
    //return "signup"; // Return to signup page with error message
  }


}
