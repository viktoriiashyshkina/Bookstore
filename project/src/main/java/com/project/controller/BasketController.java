package com.project.controller;

import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import com.project.service.BasketDetailsService;
import com.project.service.BasketService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/home/user")
public class BasketController {


  private final BookRepository bookRepository;
  private final BasketDetailsService basketDetailsService;
  private final BasketService basketService;

  public BasketController(BookRepository bookRepository, BasketDetailsService basketDetailsService,
      BasketService basketService) {
    this.bookRepository = bookRepository;
    this.basketDetailsService = basketDetailsService;
    this.basketService = basketService;
  }


  // Add book to basket
//  @PostMapping("/add/{bookId}")
//  public String addToBasket( @PathVariable("id") Long bookId, @RequestParam Integer quantity) {
//    BookEntity book = bookRepository.findById(bookId).orElse(null);
//    BasketDetails basketDetails = new BasketDetails();
//    basketDetails.setQuantity(quantity);
//    basketDetails.setBook(book);
//    basketDetailsService.saveOrderToDatabase(basketDetails);
//
//    return "redirect:/home/user";
//
//  }


//  @GetMapping
//  public String viewBasket (Model model) {
//    Basket basket=basketService.showBasket();
//    List<BasketDetails> basketDetails = basket.getBasketDetails();
//
//    model.addAttribute("basket", basketDetails);
//
//
//    return "basket";
//  }
//
//  // Remove book from basket
//  @PostMapping("/remove/{bookId}")
//  public String removeFromBasket(@PathVariable Long bookId, HttpSession session) {
//    List<BasketDetails> basket = (List<BasketDetails>) session.getAttribute("basket");
//
//    if (basket != null) {
//      System.out.println("Basket before removing: " + basket.size() + " items.");
//      basket.removeIf(orderDetail -> orderDetail.getBook().getId().equals(bookId));
//      session.setAttribute("basket", basket);
//      System.out.println("Basket after removing: " + basket.size() + " items.");
//    } else {
//      System.err.println("Attempt to remove from an empty basket.");
//    }
//
//    return "redirect:/basket";
//  }
}
