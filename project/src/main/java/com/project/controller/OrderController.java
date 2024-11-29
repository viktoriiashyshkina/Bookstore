package com.project.controller;


import com.project.entity.AccountEntity;
import com.project.entity.OrderEntity;
import com.project.entity.PaymentEntity;
import com.project.entity.User;
import com.project.service.OrderService;
import com.project.service.UserService;
import com.project.util.SecurityUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class OrderController {

  @Autowired
  private final OrderService orderService;

  private final UserService userService;

  public OrderController(OrderService orderService, UserService userService) {
    this.orderService = orderService;
    this.userService = userService;
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/home/basket/checkout")
  public String showAddMoneyPage(Model model) {

    return "order-create"; // This is the Thymeleaf template for adding money
  }


////  @PostMapping("/create/{accountId}")
////  public ResponseEntity<OrderEntity> createOrder(@PathVariable Long accountId) {
////    OrderEntity order = orderService.createOrderFromBasket(accountId);
////    return ResponseEntity.status(HttpStatus.CREATED).body(order);
//  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderEntity> getOrderDetails(@PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.getOrderDetails(orderId));
  }

  @GetMapping("/account/{accountId}")
  public ResponseEntity<List<OrderEntity>> getOrdersByAccount(@PathVariable Long accountId) {
    return ResponseEntity.ok(orderService.getOrdersByAccount(accountId));
  }

  @PostMapping("/{orderId}/pay")
  public ResponseEntity<OrderEntity> processPayment(@PathVariable Long orderId,
      @RequestBody PaymentEntity paymentDetails) {
    OrderEntity order = orderService.processPayment(orderId, paymentDetails);
    return ResponseEntity.ok(order);
  }



}
