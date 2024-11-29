package com.project.controller;

import com.project.entity.User;
import com.project.service.AccountService;
import com.project.service.GiftCardService;
import com.project.service.UserService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GiftCardController {
  @Autowired
  private UserService userService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private GiftCardService giftCardService;

  // Method to display the add money page where the user enters gift card code
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/redeemGiftCard")
  public String showAddMoneyPage(Model model) {
    // Optionally, pass additional information to the model
    return "redeem"; // This is the Thymeleaf template for adding money
  }

  // Handle the redemption of the gift card, restricted to authenticated users
  @PreAuthorize("isAuthenticated()") // Only allow authenticated users to access this method
  @PostMapping("/redeemGiftCard")
  public String redeemGiftCard(@RequestParam String cardCode, Model model) {
    try {
      // Get the username of the currently authenticated user
      String username = userService.getAuthenticatedUsername(); // Get the authenticated user's username
      String message = giftCardService.redeemGiftCard(cardCode, username); // Redeem the gift card using the username

      // Get the updated balance after redeeming the gift card
      BigDecimal updatedBalance = userService.getUserBalance(username);

      // Add the message and updated balance to the model to display on the page
      model.addAttribute("message", message);
      model.addAttribute("updatedBalance", updatedBalance);
      return "logged-in"; // Return the logged-in page (or a page showing the result)
    } catch (Exception e) {
      // If an error occurs (e.g., invalid card or already redeemed), show the error
      model.addAttribute("error", e.getMessage());
      return "redeem"; // Show the error message on the add-money page
    }
  }


}
