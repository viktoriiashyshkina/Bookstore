package com.project.controller;


import com.project.entity.OrderEntity;
import com.project.entity.PaymentEntity;
import com.project.service.OrderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

//  @PostMapping("/create/{accountId}")
//  public ResponseEntity<OrderEntity> createOrder(@PathVariable Long accountId) {
//    OrderEntity order = orderService.createOrder(accountId);
//    return ResponseEntity.status(HttpStatus.CREATED).body(order);
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
