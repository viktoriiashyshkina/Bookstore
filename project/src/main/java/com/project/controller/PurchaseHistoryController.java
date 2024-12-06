package com.project.controller;

import com.project.entity.AccountEntity;
import com.project.entity.BookEntity;
import com.project.entity.OrderDetailsEntity;
import com.project.entity.OrderEntity;
import com.project.entity.User;
import com.project.repository.OrderDetailsRepository;
import com.project.repository.OrderRepository;
import com.project.service.OrderService;
import com.project.service.UserService;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/homeTest")
public class PurchaseHistoryController {

  private final UserService userService;
  private final OrderDetailsRepository orderDetailsRepository;
  private final OrderService orderService;
  private final OrderRepository orderRepository;

  public PurchaseHistoryController(UserService userService,
      OrderDetailsRepository orderDetailsRepository, OrderService orderService,
      OrderRepository orderRepository) {
    this.userService = userService;
    this.orderDetailsRepository = orderDetailsRepository;
    this.orderService = orderService;
    this.orderRepository = orderRepository;
  }


  @PreAuthorize("isAuthenticated()")
  @GetMapping("/homeTest/purchase-history")
  public String viewPurchaseHistory(Model model) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(username);
    if (user == null) {
      model.addAttribute("message", "User not found.");
      return "error"; // Redirect to an error page
    }

    AccountEntity account = user.getAccount();
    if (account == null) {
      throw new RuntimeException("Account not found for user.");
    }

    // Fetch the user's order history
    List<OrderEntity> orders = orderRepository.findByAccountEntityOrderByDateDesc(account);
    if (orders.isEmpty()) {
      model.addAttribute("message", "You have no previous orders.");
      return "error"; // Redirect to an error page
    }

    // Loop through each order and aggregate quantities by book
    List<Map<BookEntity, Integer>> aggregatedOrderDetails = new ArrayList<>();
    for (OrderEntity order : orders) {
      Map<BookEntity, Integer> bookQuantityMap = new LinkedHashMap<>();

      // Aggregate quantities of the same book
      for (OrderDetailsEntity detail : order.getOrderDetails()) {
        BookEntity book = detail.getBook();
        bookQuantityMap.put(book, bookQuantityMap.getOrDefault(book, 0) + detail.getQuantity());
      }

      // Add the aggregated data to the list
      aggregatedOrderDetails.add(bookQuantityMap);
    }

    // Add the aggregated data and the orders to the model
    model.addAttribute("balance", account.getBalance());
    model.addAttribute("orders", orders);
    model.addAttribute("aggregatedOrderDetails", aggregatedOrderDetails);
    model.addAttribute("name", account.getUsername());
    return "purchase-history";
  }
  //  @PreAuthorize("isAuthenticated()")
//  @GetMapping("/homeTest/purchase-history")
//  public String viewPurchaseHistory(Model model) {
//    // Fetch authenticated user
//    String username = SecurityContextHolder.getContext().getAuthentication().getName();
//    User user = userService.findByUsername(username);
//    if (user == null) {
//      model.addAttribute("message", "User not found.");
//      return "error"; // Redirect to an error page
//    }
//
//    AccountEntity account = user.getAccount();
//    if (account == null) {
//      throw new RuntimeException("Account not found for user.");
//    }
//
//    // Fetch the user's order history
//    List<OrderDetailsEntity> orders = orderDetailsRepository.findByAccountEntity(account);
//    if (orders.isEmpty()) {
//      model.addAttribute("message", "You have no previous orders.");
//      return "error"; // Redirect to an error page
//    }
//    // Add order data to the model
//    model.addAttribute("orders", orders);
//    model.addAttribute("name", account.getUsername());
//    return "purchase-history";
//  }

}

