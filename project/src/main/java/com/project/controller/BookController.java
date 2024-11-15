package com.project.controller;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {


  @Autowired
  private BookRepository bookRepository;

//  @GetMapping
//  public String getBooks(Model model) {
//    model.addAttribute("books", bookRepository.findAll());
//    return "books"; // This matches the Thymeleaf template books.html
//  }

  @GetMapping
  public String getBooks(Model model) {
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

    return "books";
  }

  @PostMapping("/add")
  public String addBook(
      @RequestParam("title") String title,
      @RequestParam("author") String author,
      @RequestParam("category") String category,
      @RequestParam("isbn") String isbn,
      @RequestParam("price") double price,
      @RequestParam("description") String description,
      @RequestParam("image") MultipartFile imageFile,
      RedirectAttributes redirectAttributes) {

    BookEntity book = new BookEntity();
    book.setTitle(title);
    book.setAuthor(author);
    book.setCategory(category);
    book.setIsbn(isbn);
    book.setPrice(BigDecimal.valueOf(price));
    book.setDescription(description);

    try {
      if (!imageFile.isEmpty()) {
        book.setImage(imageFile.getBytes());
      }
    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "Image upload failed!");
      return "redirect:/books";
    }
    System.out.println("here");
    bookRepository.save(book);
    return "redirect:/books";
  }


  @PostMapping("/edit")
  public String editBook(
      @RequestParam("title") String title,
      @RequestParam("author") String author,
      @RequestParam("category") String category,
      @RequestParam("isbn") String isbn,
      @RequestParam("price") double price,
      @RequestParam("description") String description,
      @RequestParam("image") MultipartFile imageFile,
      RedirectAttributes redirectAttributes) {

    BookEntity book = new BookEntity();
    book.setTitle(title);
    book.setAuthor(author);
    book.setCategory(category);
    book.setIsbn(isbn);
    book.setPrice(BigDecimal.valueOf(price));
    book.setDescription(description);

    try {
      if (!imageFile.isEmpty()) {
        book.setImage(imageFile.getBytes());
      }
    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "Image upload failed!");
      return "redirect:/books";
    }

    bookRepository.save(book);
    return "redirect:/books";
  }

  @PostMapping("/edit/{id}")
  public String editBook(
      @PathVariable("id") Long id,
      @RequestParam("title") String title,
      @RequestParam("author") String author,
      @RequestParam("category") String category,
      @RequestParam("isbn") String isbn,
      @RequestParam("price") double price,
      @RequestParam("description") String description,
      @RequestParam("image") MultipartFile imageFile,
      RedirectAttributes redirectAttributes) {

    BookEntity book = bookRepository.findById(id).orElse(null);
    if (book == null) {
      redirectAttributes.addFlashAttribute("error", "Book not found!");
      return "redirect:/books";
    }

    book.setTitle(title);
    book.setAuthor(author);
    book.setCategory(category);
    book.setIsbn(isbn);
    book.setPrice(BigDecimal.valueOf(price));
    book.setDescription(description);

    try {
      if (!imageFile.isEmpty()) {
        book.setImage(imageFile.getBytes());
      }
    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "Image upload failed!");
      return "redirect:/books";
    }

    bookRepository.save(book);
    return "redirect:/books";
  }


  @PostMapping("/delete/{id}")
  public String deleteBook (
      @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    BookEntity book = bookRepository.findById(id).orElse(null);
    if (book == null) {
      redirectAttributes.addFlashAttribute("error", "Book not found!");
      return "redirect:/books";
    }
    bookRepository.delete(book);
    return "redirect:/books";
  }






  @GetMapping("/image/{id}")
  @ResponseBody
  public byte[] getImage(@PathVariable("id") Long id) {
    BookEntity book = bookRepository.findById(id).orElse(null);
    return (book != null) ? book.getImage() : null;
  }

}
