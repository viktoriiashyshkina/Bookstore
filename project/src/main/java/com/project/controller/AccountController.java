package com.project.controller;

import com.project.entity.AccountEntity;
import com.project.entity.User;
import com.project.service.AccountService;
import com.project.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
public class AccountController {

  private final UserService userService;
  private final AccountService accountService;

  public AccountController(UserService userService, AccountService accountService) {
    this.userService = userService;
    this.accountService = accountService;
  }


  @GetMapping("/logged-in/profile")
  public String getProfileUpdatePage(Model model) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(username);

    // Check if the user exists and if the account is null
    if (user != null && user.getAccount() != null) {
      AccountEntity account = user.getAccount();

      model.addAttribute("username", user.getUsername());
      model.addAttribute("email", user.getEmail());
      model.addAttribute("firstName", account.getFirstName());
      model.addAttribute("lastName", account.getLastName());
      model.addAttribute("phoneNumber", account.getPhoneNumber());
      model.addAttribute("address", account.getAddress());
      model.addAttribute("zipCode", account.getZipCode());
      model.addAttribute("birthday", account.getBirthday());
    } else {
      // Handle case where the account is null (e.g., no account linked to the user)
      System.out.println("User or Account details are null for username: " + username);
      // Optionally, you can set default values or leave fields empty
    }

    return "profile"; // This should return the updated profile page
  }


  @PreAuthorize("isAuthenticated()")

  @PostMapping("/logged-in/updateProfile")
  public String updateProfile(
      @RequestParam String firstName,
      @RequestParam String lastName,
      @RequestParam String phoneNumber,
      @RequestParam String address,
      @RequestParam int zipCode,
      @RequestParam String birthday) {

    String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(loggedInUsername);
    // Create or update AccountEntity for the user
    AccountEntity accountEntity = user.getAccount();
    if (accountEntity == null) {
      // If no AccountEntity exists, create a new one
      accountEntity = new AccountEntity();
    }
    // Set account details
    accountEntity.setUsername(user.getUsername());  // Use the username from the user
    accountEntity.setFirstName(firstName);
    accountEntity.setLastName(lastName);
    accountEntity.setEmail(user.getEmail());
    accountEntity.setPassword(user.getPassword());
    accountEntity.setPhoneNumber(phoneNumber);
    accountEntity.setAddress(address);
    accountEntity.setZipCode(zipCode);
    accountEntity.setBirthday(birthday);
    // Save the account information
    accountService.updateAccount(accountEntity);
    // Optionally, save any additional user details if required
    return "redirect:/logged-in/profile";  // Redirect to the profile page after saving
  }


  @PreAuthorize("isAuthenticated()")

  @PostMapping("/home/basket/checkout/updateProfile")
  public String updateProfileInPayment(
      @RequestParam String firstName,
      @RequestParam String lastName,
      @RequestParam String phoneNumber,
      @RequestParam String address,
      @RequestParam int zipCode,
      @RequestParam String birthday) {

    String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(loggedInUsername);
    // Create or update AccountEntity for the user
    AccountEntity accountEntity = user.getAccount();
    if (accountEntity == null) {
      // If no AccountEntity exists, create a new one
      accountEntity = new AccountEntity();
    }
    // Set account details
    accountEntity.setUsername(user.getUsername());  // Use the username from the user
    accountEntity.setFirstName(firstName);
    accountEntity.setLastName(lastName);
    accountEntity.setEmail(user.getEmail());
    accountEntity.setPassword(user.getPassword());
    accountEntity.setPhoneNumber(phoneNumber);
    accountEntity.setAddress(address);
    accountEntity.setZipCode(zipCode);
    accountEntity.setBirthday(birthday);
    // Save the account information
    accountService.updateAccount(accountEntity);
    // Optionally, save any additional user details if required
    return "redirect:/home/basket/checkout/confirm";  // Redirect to the profile page after saving
  }


//  @GetMapping("homeTest/basket/checkout/confirm")
//  public String getConfirm () {
//    return "paymentConfirm";
//  }




}





