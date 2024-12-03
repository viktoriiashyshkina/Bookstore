package com.project.controller;

import com.project.entity.OrderEntity;
import com.project.service.BookService;
import com.project.service.OrderService;
import com.project.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

  private final UserService userService;
  private final BookService bookService;
  private final OrderService orderService;

  public AdminController(UserService userService, BookService bookService,
      OrderService orderService) {
    this.userService = userService;
    this.bookService = bookService;

    this.orderService = orderService;
  }

  // Show Admin Dashboard
  @GetMapping("/admin/dashboard")
  public String showAdminDashboard(Model model) {

    // Fetch counts for users, books, and orders
    long userCount = userService.getUserCount();
    long bookCount = bookService.getBookCount();
    long orderCount = orderService.getOrderCount();

//    // Fetch recent orders, e.g., the last 10 orders
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

}
