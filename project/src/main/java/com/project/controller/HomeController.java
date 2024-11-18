package com.project.controller;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import com.project.service.HomeService;
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

  public HomeController(BookRepository bookRepository, HomeService homeService) {
    this.bookRepository = bookRepository;
    this.homeService = homeService;
  }


  @GetMapping
  public String getHomeScreen(Model model, Pageable pageable) {

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

    return "index";
  }
}
