package com.project.controller;

import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.entity.OrderDetailsEntity;
import com.project.entity.OrderEntity;
import com.project.entity.PaymentEntity;
import com.project.entity.User;
import com.project.repository.BasketRepository;
import com.project.repository.OrderDetailsRepository;
import com.project.repository.OrderRepository;
import com.project.service.AccountService;
import com.project.service.BasketService;
import com.project.service.OrderService;
import com.project.service.PaymentService;
import com.project.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PaymentController {

    private final UserService userService;
    private final BasketService basketService;
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private static final BigDecimal DELIVERY_COST = new BigDecimal("5.99");
    private final OrderDetailsRepository orderDetailsRepository;

    public PaymentController(UserService userService, BasketService basketService,
        OrderService orderService,
        OrderDetailsRepository orderDetailsRepository) {
        this.userService = userService;
        this.basketService = basketService;
        this.orderService = orderService;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home/basket/checkout")
    public String showPaymentPage(Model model) {
        // Fetch authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("message", "User not found.");
            return "error"; // Redirect to an error page
        }

        // Fetch user's basket
        Basket basket = basketService.getBasketFromLoggedInUser();
        if (basket == null || basket.getBasketDetails().isEmpty()) {
            model.addAttribute("message", "Your basket is empty.");
            return "error"; // Redirect to an error page
        }
        // Calculate the total amount including the delivery cost
        //BigDecimal totalAmount = basket.getTotalAmount().add(DELIVERY_COST);

        // Add necessary attributes to the model
        model.addAttribute("name", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstName", user.getAccount().getFirstName());
        model.addAttribute("lastName", user.getAccount().getLastName());
        model.addAttribute("address", user.getAccount().getAddress());
        model.addAttribute("phoneNumber", user.getAccount().getPhoneNumber());
        model.addAttribute("zipCode", user.getAccount().getZipCode());
        model.addAttribute("birthday", user.getAccount().getBirthday());
        model.addAttribute("balance", user.getAccount().getBalance());
        //model.addAttribute("basket", basket);
        //model.addAttribute("basketDetails", basket.getBasketDetails()); // Pass basket details
        //model.addAttribute("totalAmount", totalAmount);

        return "paymentCheckout";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home/basket/checkout/confirm")
    public String showPaymentConfirmPage(Model model) {
        // Fetch authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("message", "User not found.");
            return "error"; // Redirect to an error page
        }

        // Fetch user's basket
        Basket basket = basketService.getBasketFromLoggedInUser();
        if (basket == null || basket.getBasketDetails().isEmpty()) {
            model.addAttribute("message", "Your basket is empty.");
            return "error"; // Redirect to an error page
        }
        // Calculate the total amount including the delivery cost
        BigDecimal totalAmount = basket.getTotalAmount().add(DELIVERY_COST);

        // Add necessary attributes to the model
        model.addAttribute("name", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("balance", user.getAccount().getBalance());
        model.addAttribute("basket", basket);
        model.addAttribute("basketDetails", basket.getBasketDetails()); // Pass basket details
        model.addAttribute("totalAmount", totalAmount);

        return "paymentConfirm";
    }







    @PostMapping("/home/basket/pay")
    public String payForOrder(Model model) {
        // Get authenticated user's details
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username '" + username + "' not found.");
        }
        AccountEntity account = user.getAccount();
        if (account == null) {
            throw new RuntimeException("Account not found for user");
        }

        // Fetch the basket and validate
        Basket basket = basketService.getBasketFromLoggedInUser();
        if (basket == null || basket.getBasketDetails().isEmpty()) {
            model.addAttribute("error", "Your basket is empty!");
            return "home_test";
        }

        // Calculate total amount from the basket
        BigDecimal totalAmount = basket.getTotalAmount().add(DELIVERY_COST);
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("error", "Your basket total is invalid. Please check your basket.");
            return "ShowBasket"; // Return an error page
        }

        // Check if the user's profile is complete
        if (account.getFirstName()==null || account.getLastName()==null || account.getPhoneNumber()==null
            ||account.getAddress()==null) {
            model.addAttribute("error", "Please complete your profile before paying.");
            return "paymentCheckout"; // Return an error page
        }

        if (user.getAccount().getBalance().compareTo(basket.getTotalAmount()) < 0) {
            logger.error("Insufficient balance for user: " + user.getUsername() +
                ", Balance: " + user.getAccount().getBalance() +
                ", Required: " + basket.getTotalAmount());
            model.addAttribute("error", "Insufficient balance. Please add money.");
            return "paymentConfirm";
        }

        // Deduct the total amount from the user's balance
        userService.deductBalance(user, totalAmount);
        OrderEntity order = orderService.createOrder(account, basket);

        // Add success message
        model.addAttribute("message", "Payment successful! Your order has been placed.");
        // Add order data to the model
        model.addAttribute("order", order);
        model.addAttribute("name", account.getUsername());
        return "redirect:/home/purchase-history"; // Return a success page
    }


    //@Controller
//@RequestMapping("/homeTest")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//    @Autowired
//    private OrderService orderService;
//
//    private AccountService accountService;
//    @Autowired
//    private BasketService basketService;
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/payment")
//    public String showPaymentPage(Model model) {
//        AccountEntity account = accountService.getLoggedInAccount(); // Assuming you have a method to get logged-in user's account
//
//        if (account == null) {
//            model.addAttribute("error", "Account not found.");
//            return "redirect:/login"; // Redirect to login if account not found
//        }
//
//        Basket basket = basketService.getBasketFromLoggedInUser();// Assume basketService gets the basket details
//        model.addAttribute("basket", basket);
//
//        return "payment"; // Ensure that this is the correct view name
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/payment")
//    public String processPayment(Model model, Long userId) {
//        try {
//            // Fetching username from the security context
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//            logger.debug("Fetching account for username: {}", username);
//            AccountEntity account = accountService.getAccount(username);
//            logger.debug("Fetched account: {}", account);
//
//
//            // Get the basket for the logged-in user
//            Basket basket = basketService.getBasketFromLoggedInUser();
//
//            if (basket == null || basket.getBasketDetails().isEmpty()) {
//                throw new RuntimeException("Basket is empty, cannot create order");
//            }
//
//            // Check if account information is complete
//            if (account.getFirstName() == null || account.getAddress() == null) {
//                model.addAttribute("error", "Your account information is incomplete. Please complete your profile.");
//                return "redirect:/logged-in/updateProfile"; // Redirect to profile update page
//            }
//
//            // Process the payment using the service layer
//            String paymentResult = paymentService.processPayment(userId);
//
//            if (paymentResult.contains("success")) {
//                model.addAttribute("success", "Payment successful! Your order has been placed.");
//                return "paymentSuccess"; // Redirect to payment success page
//            } else {
//                model.addAttribute("error", paymentResult);  // Show the error message returned by service
//                return "redirect:/redeemGiftCard"; // Redirect to add money page
//            }
//
//        } catch (Exception e) {
//            model.addAttribute("error", "An error occurred while processing the payment. Please try again.");
//            logger.error("Error during payment processing: ", e);
//            return "ShowBasket"; // Redirect to basket page
//        }
//        }
//
//@Controller
//@RequestMapping("/homeTest")
//public class PaymentController {
//
//    @Autowired
//    private AccountService accountService;
//    @Autowired
//    private BasketService basketService;
//    @Autowired
//    private OrderService orderService;
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/payment")
//    public String processPayment(Model model) {
//        try {
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            AccountEntity account = accountService.getAccount(username);
//
//            // Fetch basket for the logged-in user
//            Basket basket = basketService.getBasketFromLoggedInUser();
//            if (basket == null || basket.getBasketDetails().isEmpty()) {
//                throw new RuntimeException("Basket is empty, cannot create order");
//            }
//
//            // Check if account information is complete
//            if (account.getFirstName() == null || account.getAddress() == null) {
//                model.addAttribute("error", "Your account information is incomplete. Please complete your profile.");
//                return "redirect:/logged-in/updateProfile"; // Redirect to profile update page
//            }
//
//            // Process payment (check user balance and proceed)
//            boolean paymentSuccessful = processPayment(account, basket);
//            if (!paymentSuccessful) {
//                model.addAttribute("error", "Insufficient funds. Please add money to your account.");
//                return "redirect:/redeemGiftCard"; // Redirect to add money page
//            }
//
//            model.addAttribute("success", "Payment successful! Your order has been placed.");
//            return "paymentSuccess"; // Redirect to payment success page
//
//        } catch (Exception e) {
//            model.addAttribute("error", "An error occurred while processing the payment. Please try again.");
//            return "ShowBasket"; // Redirect to basket page
//        }
//    }
//
//    private boolean processPayment(AccountEntity account, Basket basket) {
//        if (account.getBalance().compareTo(basket.getTotalAmount()) < 0) {
//            return false; // Insufficient funds
//        }
//
//        // Deduct the basket total from the user's account balance
//        account.setBalance(account.getBalance().subtract(basket.getTotalAmount()));
//        accountService.saveAccount(account); // Save updated account
//
//        // Create the order from the basket
//        orderService.createOrder(account, basket);
//
//        return true; // Payment processed successfully
//    }
//}
}






