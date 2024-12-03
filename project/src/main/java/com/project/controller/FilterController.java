package com.project.controller;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;

import com.project.service.FilterService;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FilterController {

  @Autowired
  private FilterService filterService;

  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/filterByPriceRange")
  public String filterBookByPriceRange(
      @RequestParam(defaultValue = "0") Double minPrice,
      @RequestParam(defaultValue = "1000000") Double maxPrice,
      Pageable pageable,
      Model model) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 10);

    Page<BookEntity> books = filterService.filterBooksByPriceRange(minPrice, maxPrice, pageRequest);
    model.addAttribute("books", books);
    books.forEach(book -> {
      if (book.getImage() != null && book.getImageDataBase64() == null) {
        String base64Image = encodeImageToBase64(book.getImage());
        book.setImageDataBase64(base64Image); // Populate Base64 data for the image
      } else if (book.getImage() == null) {
        book.setImageDataBase64(null); // Set to null explicitly if no image is present
      }
    });
    model.addAttribute("books", books.getContent());
    model.addAttribute("minPrice", minPrice);
    model.addAttribute("maxPrice", maxPrice);
    model.addAttribute("currentPage", books.getNumber());
    model.addAttribute("totalPages", books.getTotalPages());
    model.addAttribute("totalItems", books.getTotalElements());
    return "home_test";

  }

  @GetMapping("/filterByPrice")
  public String filterBooksByPrice(
      @RequestParam(defaultValue = "1") Double minPrice,
      @RequestParam(defaultValue = "1000000") Double maxPrice,
      @RequestParam(required = false, defaultValue = "price") String sort,
      // 'sort' can be 'price', 'priceDesc', etc.
      Pageable pageable,
      Model model) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 10);

    Page<BookEntity> books;

    // Determine sort order and filter books
    Sort sortOrder;
    switch (sort) {
      case "priceDesc":
        sortOrder = Sort.by(Sort.Order.desc("price"));
        break;
      default: // Default to ascending price
        sortOrder = Sort.by(Sort.Order.asc("price"));
        break;
    }

    // Update pageable with sort order
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);

    // Filter books based on the price range and sorting
    if (minPrice != null && maxPrice != null && (minPrice > 0 || maxPrice < 1000000)) {
      books = filterService.filterBooksByPriceRange(minPrice, maxPrice, pageable);
    } else {
      books = filterService.getBooksSortedByPrice(pageable);
    }

    // Populate Base64 image data for books
    books.forEach(book -> {
      if (book.getImage() != null && book.getImageDataBase64() == null) {
        String base64Image = encodeImageToBase64(book.getImage());
        book.setImageDataBase64(base64Image);
      } else if (book.getImage() == null) {
        book.setImageDataBase64(null);
      }
    });

    // Add attributes to the model
    model.addAttribute("books", books.getContent());
    model.addAttribute("currentPage", books.getNumber());
    model.addAttribute("totalPages", books.getTotalPages());
    model.addAttribute("totalItems", books.getTotalElements());
    model.addAttribute("minPrice", minPrice);
    model.addAttribute("maxPrice", maxPrice);
    model.addAttribute("sort", sort);

    return "home_test";
  }


  @GetMapping("/filterByTitle")
  public String filterBooksByTitle(
      @RequestParam(defaultValue = "1") Double minPrice,
      @RequestParam(defaultValue = "1000000") Double maxPrice,
      @RequestParam(defaultValue = "title") String sort,// 'sort' can be 'title', 'price', etc.
      Pageable pageable,
      Model model) {

    // Determine the sorting logic
    Sort sortOrder;
    switch (sort) {
      case "titleDesc":
        sortOrder = Sort.by(Sort.Order.desc("title"));
        break;
      case "titleAsc":
      default:
        sortOrder = Sort.by(Sort.Order.asc("title"));
        break;
    }
    // Apply the sort to the pageable
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);

    // Fetch the sorted books
    Page<BookEntity> books = filterService.getAllBooks(pageable); // Update method name if necessary

    books.forEach(book -> {
      if (book.getImage() != null && book.getImageDataBase64() == null) {
        String base64Image = encodeImageToBase64(book.getImage());
        book.setImageDataBase64(base64Image); // Populate Base64 data for the image
      } else if (book.getImage() == null) {
        book.setImageDataBase64(null); // Set to null explicitly if no image is present
      }
    });
    model.addAttribute("books", books.getContent());
    model.addAttribute("currentPage", books.getNumber());
    model.addAttribute("totalPages", books.getTotalPages());
    model.addAttribute("totalItems", books.getTotalElements());
    model.addAttribute("minPrice", minPrice);
    model.addAttribute("maxPrice", maxPrice);
    model.addAttribute("sort", sort);

    return "home_test";
  }

  // Helper method to encode image data to Base64
  private String encodeImageToBase64(byte[] imageData) {
    return Base64.getEncoder().encodeToString(imageData);
  }

  @GetMapping("/filterByCategory")
  public String filterBooksByCategory(
     @RequestParam String category, Model model) {
    List<BookEntity> books = filterService.filterByCategory(category);

    // Ensure image data is converted to Base64 for display
    books.forEach(book -> {
      if (book.getImage() != null && book.getImageDataBase64() == null) {
        String base64Image = encodeImageToBase64(book.getImage());
        book.setImageDataBase64(base64Image); // Populate Base64 data for the image
      } else if (book.getImage() == null) {
        book.setImageDataBase64(null); // Set to null explicitly if no image is present
      }
    });

  // Add attributes to the model for rendering in the view
    model.addAttribute("books", books);

    return "home_test";

}
}









