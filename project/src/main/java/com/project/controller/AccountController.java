//package com.project.controller;
//
//import com.project.entity.AccountEntity;
//import com.project.entity.User;
//import com.project.service.AccountService;
//import com.project.service.UserService;
//import com.project.util.Role;
//import java.security.Principal;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
////@Controller("/logged-in")
////public class AccountController {
//// private final UserService userService;
////
////  public AccountController(UserService userService) {
////    this.userService = userService;
////  }
////
////
////  @GetMapping("/profile")
////  public String viewProfile(Model model) {
////    return "redirect:/profile";
////  }
////
////  @PostMapping("/profile")
////  public String viewProfile(@RequestParam String username, Model model) {
////    User user = userService.findByUsername(username);
////
////    return "redirect:/profile";
////  }
////
////  @Controller("/logged-in")
////  public class AccountController {
////
////    private final UserService userService;
////    private final AccountService accountService;
////
////    public AccountController(UserService userService, AccountService accountService) {
////      this.userService = userService;
////      this.accountService = accountService;
////    }
////
////    @PreAuthorize("isAuthenticated()")
////    @GetMapping("/profile")
////    public String viewProfile(Model model, Principal principal) {
////      if (principal == null) {
////        return "redirect:/profile"; // If user is not authenticated, redirect to login page
////      }
////      // Get the logged-in user's username
////      String username = principal.getName(); // This is Spring Security's way of getting the username
////      // Fetch the user from the database
////      User user = userService.findByUsername(username);
////      // Add both the user and account details to the model
////      model.addAttribute("user", user);
////      return "redirect:profile";
////    }
////
////    @PreAuthorize("isAuthenticated()")
////    @Secured("USER")
////    @PostMapping("/updateProfile")
////    public String updateProfile(@RequestParam String username,
////        @RequestParam String email,
////        @RequestParam String firstName,
////        @RequestParam String lastName,
////        @RequestParam String phoneNumber,
////        @RequestParam String address,
////        @RequestParam int zipCode,
////        @RequestParam String birthday) {
////
////      // Get the logged-in user's username
////      String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
////      User user = userService.findByUsername(loggedInUsername);
////
////      // Update the user's details
////////      AccountEntity accountEntity = user.getAccount();
//////      accountEntity.setFirstName(firstName);
//////      accountEntity.setLastName(lastName);
//////      accountEntity.setEmail(email);
//////      accountEntity.setPhoneNumber(phoneNumber);
//////      accountEntity.setAddress(address);
//////      accountEntity.setZipCode(zipCode);
//////      accountEntity.setBirthday(birthday);
////
////      // Save the updated account
//////      accountService.updateAccount(accountEntity);
////      userService.saveUser(user.getUsername(), user.getEmail(), user.getPassword(), Role.USER);  // Assuming you also want to update the user entity.
////
////      return "redirect:/profile";  // Redirect to profile after updating
////    }
////  }
////
////
////
//
