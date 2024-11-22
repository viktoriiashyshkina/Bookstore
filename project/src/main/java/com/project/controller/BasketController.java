package com.project.controller;

import com.project.entity.*;
import com.project.repository.BookRepository;
import com.project.service.BasketDetailsService;
import com.project.service.BasketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BookRepository bookRepository;
    private final BasketDetailsService incompleteOrderDetailsService;
    private final BasketService basketService;
    public BasketController(BasketDetailsService incompleteOrderDetailsService, BasketService basketService) {
        this.incompleteOrderDetailsService = incompleteOrderDetailsService;
        this.basketService = basketService;
    }


    // Add book to basket
    @PostMapping("/add/{bookId}")
    public String addToBasket(@PathVariable Long bookId, @RequestParam int quantity, HttpSession session) {
        BookEntity book = bookRepository.findById(bookId).orElse(null);
        BasketDetails incompleteOrder = new BasketDetails();
        incompleteOrder.setQuantity(quantity);
        incompleteOrder.setBook(book);
        incompleteOrderDetailsService.saveOrderToDatabase(incompleteOrder);




          return "redirect:/basket";

    }


    @GetMapping
    public String viewBasket (Model model) {
       basket basket=basketService.showBasket();
        List<BasketDetails> basketDetails = basket.getBasketDetails();

        model.addAttribute("basket", basketDetails);


        return "basket";
    }

    // Remove book from basket
    @PostMapping("/remove/{bookId}")
    public String removeFromBasket(@PathVariable Long bookId, HttpSession session) {
        List<BasketDetails> basket = (List<BasketDetails>) session.getAttribute("basket");

        if (basket != null) {
            System.out.println("Basket before removing: " + basket.size() + " items.");
            basket.removeIf(orderDetail -> orderDetail.getBook().getId().equals(bookId));
            session.setAttribute("basket", basket);
            System.out.println("Basket after removing: " + basket.size() + " items.");
        } else {
            System.err.println("Attempt to remove from an empty basket.");
        }

        return "redirect:/basket";
    }
}
