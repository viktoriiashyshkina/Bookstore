//package com.project.controller;
//
//
//import com.project.entity.AccountEntity;
//import com.project.entity.Basket;
//import com.project.entity.BasketDetails;
//import com.project.entity.OrderDetailsEntity;
//import com.project.entity.OrderEntity;
//import com.project.entity.PaymentEntity;
//import com.project.entity.User;
//import com.project.service.AccountService;
//import com.project.service.BasketService;
//
//import com.project.service.OrderService;
//import com.project.service.UserService;
//import com.project.util.Role;
//import jakarta.persistence.criteria.Order;
//import jakarta.servlet.http.HttpServletRequest;
//import java.math.BigDecimal;
//import java.util.Base64;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Controller
//@RequestMapping("/homeTest")
//public class OrderController {
//
//  @Autowired
//  private OrderService orderService;
//  @Autowired
//  private AccountService accountService;
//  @Autowired
//  private BasketService basketService;
//  @Autowired
//  private UserService userService;
//  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
//
//
//  @PreAuthorize("isAuthenticated()")
//  @GetMapping("/basket/checkout")
//  public String showAddMoneyPage(Model model) {
//    // Get the authenticated username
////    String username = SecurityContextHolder.getContext().getAuthentication().getName();
////
////    // Fetch the user details
////    User user = userService.findByUsername(username);
////    if (user == null) {
////      throw new UsernameNotFoundException("User with username '" + username + "' not found.");
////    }
////
////    // Fetch the basket details for the user
////    Basket basket = basketService.getBasketFromLoggedInUser();
////    if (basket == null) {
////      throw new IllegalStateException("No basket found for user '" + username + "'.");
////    }
////
////    // Fetch or calculate additional details
////    BigDecimal totalAmount = basket.getTotalAmount();
////    BigDecimal shippingFee = BigDecimal.valueOf(5.99); // Example of a fixed fee
////    BigDecimal finalAmount = totalAmount.subtract(shippingFee);
////
////    // Add account details to the model
////    model.addAttribute("username", user.getUsername());
////    model.addAttribute("account", user.getAccount()); // Assuming `Account` is a property of `User`
//
//    if (!Objects.equals(SecurityContextHolder.getContext().getAuthentication().getName(),
//        "anonymousUser")) {
//      if (basketService.getBasketFromLoggedInUser().getBasketDetails().size() != 0) {
//
//        System.out.println(basketService.getBasketFromLoggedInUser().getBasketDetails().size());
//
//        Basket basket = basketService.getBasketFromLoggedInUser();
//        List<BasketDetails> basketDetails = basket.getBasketDetails();
//
//        // Add basket details to the model
//        model.addAttribute("basketDetails",
//            basket.getBasketDetails()); // Assuming `getDetails()` returns items in the basket
////    model.addAttribute("totalAmount", basket.getTotalAmount());
////    model.addAttribute("shippingFee", shippingFee);
////    model.addAttribute("finalAmount", finalAmount);
//
//        return "error";
//      }
//    }
//    return "payment";
//  }
//}


//////
//  @PreAuthorize("isAuthenticated()")
//  @PostMapping("/basket/checkout")
//  public String checkout(HttpServletRequest request, Model model) {
//    try {
//      // Fetch the logged-in user's account details
//      AccountEntity account = accountService.getLoggedInAccount();
//
//      // Check if the account is found, redirect to login if not
//      if (account == null) {
//        model.addAttribute("error", "Account not found.");
//        return "redirect:/login"; // Redirect to login if account is not found
//      }
//
//      // Check if the account details are incomplete (e.g., missing first name or address)
//      if (account.getFirstName() == null || account.getAddress() == null) {
//        model.addAttribute("warning", "Please complete your profile before proceeding.");
//        return "redirect:/logged-in/profile"; // Redirect to profile page if account is incomplete
//      }
//
//      // Create the order from the user's basket
//      OrderEntity order = orderService.createOrder();
//      if (order == null) {
//        model.addAttribute("error", "An error occurred while creating the order.");
//        return "ShowBasket"; // Return to the basket page if order creation fails
//      }
//
//      // Add the order and user information to the model for the view
//      model.addAttribute("order", order);
//      model.addAttribute("username", account.getUsername());
//      model.addAttribute("balance", account.getBalance());
//
//      // Log the order creation and total amount
//      logger.info("Order created successfully: " + order.getId());
//      logger.info("Order total amount: " + order.getTotalAmount());
//
//      return "order"; // Return the order confirmation view
//    } catch (Exception e) {
//      // Log the error and show a generic error message
//      logger.error("Error during checkout: ", e);
//      model.addAttribute("error", "An error occurred during checkout. Please try again.");
//      return "ShowBasket"; // Return to the basket page if there is an exception
//    }
//  }
//@PreAuthorize("isAuthenticated()")  // Only authenticated users can access this method
//@PostMapping("/basket/checkout")
//public String checkout(HttpServletRequest request, Model model) {
//  try {
//    if (!Objects.equals(SecurityContextHolder.getContext().getAuthentication().getName(), "anonymousUser")) {
//      if (basketService.getBasketFromLoggedInUser().getBasketDetails().size() != 0) {
//        // Fetch the basket and username
//        Basket basket = basketService.getBasketFromLoggedInUser();
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // Create the order
//        OrderEntity order = orderService.createOrder(basket, username);
//
//        // Redirect to the order confirmation page
//        return "redirect:/home/order/" + order.getId();  // Redirect to the order page
//      }
//    }
//    return "redirect:/home/basket";  // Redirect back to basket page if empty
//  } catch (Exception e) {
//    // Log the exception
//    logger.error("Error during checkout: ", e);
//    model.addAttribute("errorMessage", "An error occurred during checkout: " + e.getMessage());
//    return "errorPage";  // Redirect to a custom error page
//  }

//  // Get order details by order ID
//  @GetMapping("/order/{orderId}")
//  public String confirmOrder(@PathVariable Long orderId, Model model) {
//    try {
//      // Retrieve the order details by ID
//      OrderEntity order = orderService.getOrderDetails(orderId);
//      model.addAttribute("order", order); // Add order details to the model for rendering
//      return "order"; // Return the order details page
//    } catch (RuntimeException e) {
//      model.addAttribute("error", "Order not found");
//      return "error"; // Show an error page if the order is not found
//    }
//  }
//
//  // Process payment for the order
//  @PostMapping("/order/{orderId}/pay")
//  public ResponseEntity<OrderEntity> processPayment(@PathVariable Long orderId, @RequestBody PaymentEntity paymentDetails) {
//    try {
//      // Process the payment for the order and update the order status
//      OrderEntity updatedOrder = orderService.processPayment(orderId, paymentDetails);
//      return ResponseEntity.ok(updatedOrder); // Return the updated order as a response
//    } catch (RuntimeException e) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Return an error response if payment fails
//    }

