package com.project.controller;

import com.project.service.BookService;
import java.awt.print.Book;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class SearchController {

  private final BookService bookService;

  public SearchController(BookService bookService) {
    this.bookService = bookService;
  }

  // Get method for displaying the search page
  @GetMapping("/search")
  public String showSearchPage() {
    return "search";  // This is the Thymeleaf template for the search page
  }

  // Post method for handling the search query
//  @PostMapping("/search")
//  public String searchBooks(@RequestParam("search") String searchQuery, Model model) {
//    // Call the service layer to search for books by the query
////    List<Book> books = bookService.searchBooks(searchQuery);
//
//    // Add the search results to the model
//    model.addAttribute("books", books);
//    model.addAttribute("searchQuery", searchQuery);
//
//    return "searchResults";  // This is the Thymeleaf template that shows search results
//  }
}
