package com.project.controller;

import com.project.entity.BookEntity;
import com.project.service.BookService;
import java.awt.print.Book;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

  private final BookService bookService;

  public SearchController(BookService bookService) {
    this.bookService = bookService;
  }


  @GetMapping("/searchResults")
  public String searchBooks(@RequestParam("query") String query, Model model) {
    List<BookEntity> books = bookService.searchBooks(query);

    // Ensure image data is converted to Base64 for display
    books.forEach(book -> {
      if (book.getImage() != null && book.getImageDataBase64() == null) {
        String base64Image = encodeImageToBase64(book.getImage());
        book.setImageDataBase64(base64Image); // Populate Base64 data for the image
      } else if (book.getImage() == null) {
        book.setImageDataBase64(null); // Set to null explicitly if no image is present
      }
    });

    model.addAttribute("books", books);
    return "search-result";
  }

  // Helper method to encode image data to Base64
  private String encodeImageToBase64(byte[] imageData) {
    return Base64.getEncoder().encodeToString(imageData);
  }
}
