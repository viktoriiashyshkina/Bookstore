package com.project.controller;

import com.project.entity.User;

import com.project.service.UserService;
import java.awt.print.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

  private final UserService userService;


  @Autowired
  public AdminController(UserService userService) {
    this.userService = userService;
  }

  // Show Admin Dashboard
  @GetMapping("/admin/dashboard")
  public String showAdminDashboard(Model model) {
    return "admin_dashboard"; // Thymeleaf template for the admin dashboard
  }

//  // Show the page for registering new users
//  @GetMapping("/admin/register")
//  public String showAdminRegisterPage(Model model) {
//    model.addAttribute("userForm", new User());
//    return "register-admin"; // Thymeleaf template for registering new users
//  }
//
//  // Handle new user registration (Admin only)
//  @PostMapping("/admin/register")
//  public String registerAdminUser(@ModelAttribute User form, RedirectAttributes redirectAttributes) {
//    try {
//      // Register the user (admins are being reform.getUsername(), form.getEmail(), form.getPassword()gistered as ROLE_ADMIN)
////      userService.registerAdmin(form);
//      redirectAttributes.addFlashAttribute("message", "Admin user registered successfully.");
//      return "redirect:/admin/dashboard"; // Redirect to admin dashboard after successful registration
//    } catch (Exception e) {
//      redirectAttributes.addFlashAttribute("error", "Error registering admin user. Please try again.");
//      return "redirect:/admin/register"; // Redirect back to the registration page if an error occurs
//    }
//  }
//
//  @GetMapping("/addBook")
//  public String showAddBookForm(Model model) {
//    model.addAttribute("book", new Book());
//    return "add-book";
//  }

//  @PostMapping("/addBook")
//  public String addBook(@ModelAttribute Book book) {
//    bookRepository.save(book);
//    return "redirect:/admin/dashboard";
//  }

//  @PutMapping("/updateBook/{id}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
//    Book book = bookRepository.findById(id)
//        .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
//    book.setTitle(bookDetails.getTitle());
//    book.setAuthor(bookDetails.getAuthor());
//    book.setDescription(bookDetails.getDescription());
//    book.setPrice(bookDetails.getPrice());
//    Book updatedBook = bookRepository.save(book);
//    return ResponseEntity.ok(updatedBook);
//  }
//
//  @DeleteMapping("/deleteBook/{id}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable Long id) {
//    Book book = bookRepository.findById(id)
//        .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
//    bookRepository.delete(book);
//    Map<String, Boolean> response = new HashMap<>();
//    response.put("deleted", Boolean.TRUE);
//    return ResponseEntity.ok(response);
//  }
}
