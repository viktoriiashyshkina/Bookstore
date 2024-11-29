package com.project.controller;

import com.project.entity.BookEntity;
import com.project.entity.User;
import com.project.repository.BookRepository;
import com.project.service.HomeService;
import com.project.service.UserService;
import java.security.Principal;
import java.util.Base64;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

  private final BookRepository bookRepository;

  private final HomeService homeService;
  private final UserService userService;

  public HomeController(BookRepository bookRepository, HomeService homeService,
      UserService userService) {
    this.bookRepository = bookRepository;
    this.homeService = homeService;
    this.userService = userService;
  }


  @GetMapping
  public String getHomeScreen(Model model, Principal principal, Pageable pageable) {

    if (principal != null) {
      String username = principal.getName();
      User user = userService.findByUsername(username);

      if (user != null) {
        model.addAttribute("user", user);  // Add the user object to the model
      }
    }

    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 10);

    Page<BookEntity> books = bookRepository.findAll(pageRequest);

    //List<BookEntity> books = bookRepository.findAll();
    model.addAttribute("books", books);

    // Retrieve and set the image data for each book
    for (BookEntity book : books.getContent()) {
      byte[] imageData = book.getImage();
      if (imageData != null) {
        String imageDataBase64 = Base64.getEncoder().encodeToString(imageData);
        book.setImageDataBase64(imageDataBase64);
      }
    }

    model.addAttribute("books", books.getContent());
    model.addAttribute("currentPage", books.getNumber());
    model.addAttribute("totalPages", books.getTotalPages());
    model.addAttribute("totalItems", books.getTotalElements());

    return "home_test";
  }
}
