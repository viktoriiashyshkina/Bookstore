package com.project.controller;
import com.project.entity.AccountEntity;
import com.project.entity.User;
import com.project.service.UserService;
import com.project.util.SecurityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthenticationController {

  private final UserService userService;

  public AuthenticationController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String showLoginPage() {
    return "login";
  }

  @PostMapping("/login")
  public String showLoginPage(@RequestParam String username, @RequestParam String password) {
    return "login";
  }

  @GetMapping("/logged-in")
  public String loggedInPage(Model model) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(username);


    if (user != null) {
      model.addAttribute("user", user);  // Add the user object to the model
    }
    AccountEntity accountEntity = new AccountEntity();
    if (accountEntity == null) {
      throw new RuntimeException("Account is null");
    }
    model.addAttribute("name", username);
    model.addAttribute("balance", user.getAccount().getBalance());
    return "logged-in"; // The Thymeleaf template to render
  }

    }




