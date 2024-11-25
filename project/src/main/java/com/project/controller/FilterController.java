package com.project.controller;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import com.project.service.BookService;
import com.project.service.FilterService;
import java.awt.print.Book;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class FilterController {

  @Autowired
  private FilterService filterService;

 @GetMapping("/filterByPriceRange")
 public String filterBookByPriceRange(
     @RequestParam(defaultValue = "0") Double minPrice,
     @RequestParam(defaultValue = "1000000") Double maxPrice,
    @PageableDefault(size = 10) Pageable pageable,
     Model model) {


 Page<BookEntity> books = filterService.filterBooksByPriceRange(minPrice, maxPrice, pageable);


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
   return "home_test";

 }

  @GetMapping("/filterByPrice")
  public String filterBooksByPrice(
      @RequestParam(defaultValue = "1") Double minPrice,
      @RequestParam(defaultValue = "1000000") Double maxPrice,
      @RequestParam(defaultValue = "price") String sort,// 'sort' can be 'title', 'price', etc.
      Pageable pageable,
      Model model) {

    Page<BookEntity> books;

    switch (sort) {
      case "price":
        books = filterService.getBooksSortedByPrice(pageable);
        break;
      case "priceDesc":
        books = filterService.getBooksSortedByPriceDesc(pageable);
        break;
      case "title":
        books = filterService.getBooksSortedByTitle(pageable);
        break;
      case "titleDesc":
        books = filterService.getBooksSortedByTitleDesc(pageable);
        break;
      default:
        books = filterService.filterBooksByPriceRange(minPrice, maxPrice, pageable);
    }

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


  @GetMapping("/filterByTitle")
  public String filterBooksByTitle(
      @RequestParam(defaultValue = "1") Double minPrice,
      @RequestParam(defaultValue = "1000000") Double maxPrice,
      @RequestParam(defaultValue = "title") String sort,// 'sort' can be 'title', 'price', etc.
      Pageable pageable,
      Model model) {

    Page<BookEntity> books;

    switch (sort) {
      case "title":
        books = filterService.getBooksSortedByTitle(pageable);
        break;
      case "titleDesc":
        books = filterService.getBooksSortedByTitleDesc(pageable);
        break;
      default:
        books = filterService.filterBooksByPriceRange(minPrice, maxPrice, pageable);
    }


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
}

//
//    // Common handler for filtering and sorting
//    @GetMapping("/filterBook")
//    public String filterBooks(
//        @RequestParam(defaultValue = "0") Double minPrice,
//        @RequestParam(defaultValue = "1000000") Double maxPrice,
//        @RequestParam(defaultValue = "price") String sort, // 'sort' can be 'price', 'priceDesc', 'title', etc.
//        @PageableDefault(size = 10) Pageable pageable,
//        Model model) {
//
//      Page<BookEntity> books;
//
//      // Select sorting and filtering logic based on 'sort'
//      switch (sort) {
//        case "price":
//          books = filterService.getBooksSortedByPrice(pageable);
//          break;
//        case "priceDesc":
//          books = filterService.getBooksSortedByPriceDesc(pageable);
//          break;
//        case "title":
//          books = filterService.getBooksSortedByTitle(pageable);
//          break;
//        case "titleDesc":
//          books = filterService.getBooksSortedByTitleDesc(pageable);
//          break;
//        default:
//          books = filterService.filterBooksByPriceRange(minPrice, maxPrice, pageable);
//      }
//
//      // Process and encode image data
//      books.forEach(this::processBookImages);
//
//      // Add common model attributes
//      addPaginationAttributes(model, books, minPrice, maxPrice, sort);
//
//      return "home_test";
//    }
//
//    // Helper method to encode image data
//    private void processBookImages(BookEntity book) {
//      if (book.getImage() != null && book.getImageDataBase64() == null) {
//        String base64Image = Base64.getEncoder().encodeToString(book.getImage());
//        book.setImageDataBase64(base64Image);
//      } else if (book.getImage() == null) {
//        book.setImageDataBase64(null);
//      }
//    }
//
//    // Helper method to add pagination and filtering details to the model
//    private void addPaginationAttributes(Model model, Page<BookEntity> books, Double minPrice, Double maxPrice, String sort) {
//      model.addAttribute("books", books.getContent());
//      model.addAttribute("currentPage", books.getNumber());
//      model.addAttribute("totalPages", books.getTotalPages());
//      model.addAttribute("totalItems", books.getTotalElements());
//      model.addAttribute("minPrice", minPrice);
//      model.addAttribute("maxPrice", maxPrice);
//      model.addAttribute("sort", sort);
//    }
 // }

//  @GetMapping("/filterBooks")
//  public String filterBooks(
//      @RequestParam(defaultValue = "0") Double minPrice,
//      @RequestParam(defaultValue = "1000000") Double maxPrice,
//      @RequestParam(defaultValue = "price") String sort,
//      Pageable pageable,
//      Model model) {
//
//    Page<BookEntity> books;
//
//
//    // Determine sorting direction and property
//    Sort sortOrder;
//    switch (sort) {
//      case "priceDesc":
//        sortOrder = Sort.by(Sort.Order.desc("price"));
//        break;
//      case "titleDesc":
//        sortOrder = Sort.by(Sort.Order.desc("title"));
//        break;
//      case "title":
//        sortOrder = Sort.by(Sort.Order.asc("title"));
//        break;
//      default: // Default to ascending price
//        sortOrder = Sort.by(Sort.Order.asc("price"));
//    }
//
//    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 10);
//      books = filterService.filterBooksByPriceRange(minPrice, maxPrice, pageable);
//
//
//   books.forEach(book -> {
//     if (book.getImage() != null && book.getImageDataBase64() == null) {
//       String base64Image = encodeImageToBase64(book.getImage());
//       book.setImageDataBase64(base64Image); // Populate Base64 data for the image
//     } else if (book.getImage() == null) {
//       book.setImageDataBase64(null); // Set to null explicitly if no image is present
//     }
//   });
//
//    model.addAttribute("books", books.getContent());
//    model.addAttribute("currentPage", books.getNumber());
//    model.addAttribute("totalPages", books.getTotalPages());
//    model.addAttribute("totalItems", books.getTotalElements());
//    model.addAttribute("minPrice", minPrice);
//    model.addAttribute("maxPrice", maxPrice);
//    model.addAttribute("priceDesc", books.getContent().get(0).getTitle());
//
//    model.addAttribute("sort", sort);
//
//    return "home_test";
//  }
//
//    private String encodeImageToBase64(byte[] imageData) {
//    return Base64.getEncoder().encodeToString(imageData);
//  }
//}



