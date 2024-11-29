package com.project.controller;
import com.project.entity.BookEntity;
import com.project.service.BookService;
import com.project.service.FilterService;
import java.util.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

  private final BookService bookService;
  private final FilterService filterService;

  public SearchController(BookService bookService, FilterService filterService) {
    this.bookService = bookService;
    this.filterService = filterService;
  }

  @GetMapping("/searchResults")
  public String searchBooks(@RequestParam("query") String query,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    Model model) {
      // Fetch paginated search results
      Page<BookEntity> bookPage = filterService.searchBooks(query, PageRequest.of(page, size));

      // Ensure image data is converted to Base64 for display
      bookPage.getContent().forEach(book -> {
        if (book.getImage() != null && book.getImageDataBase64() == null) {
          String base64Image = encodeImageToBase64(book.getImage());
          book.setImageDataBase64(base64Image); // Populate Base64 data for the image
        } else if (book.getImage() == null) {
          book.setImageDataBase64(null); // Set to null explicitly if no image is present
        }
      });

      // Add data to the model
      model.addAttribute("books", bookPage.getContent()); // Current page of books
      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", bookPage.getTotalPages());
      model.addAttribute("query", query); // To persist the search query for pagination links

      return "search-result";
  }

  // Helper method to encode image data to Base64
  private String encodeImageToBase64(byte[] imageData) {
    return Base64.getEncoder().encodeToString(imageData);
  }
}
