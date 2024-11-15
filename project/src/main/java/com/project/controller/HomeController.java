package com.project.controller;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

  private final BookRepository bookRepository;

  public HomeController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }


  @GetMapping
  public String getHomeScreen(Model model) {
    List<BookEntity> books = bookRepository.findAll();
    model.addAttribute("books", books);

    // Retrieve and set the image data for each book
    for (BookEntity book : books) {
      byte[] imageData = book.getImage();
      if (imageData != null) {
        String imageDataBase64 = Base64.getEncoder().encodeToString(imageData);
        book.setImageDataBase64(imageDataBase64);
      }
    }

    return "index";
  }
}
