package com.project.controller;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import com.project.service.HomeService;
import java.util.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home_cpy")
public class Home_cpy {

    private final BookRepository bookRepository;
    private final HomeService homeService;

    public Home_cpy(BookRepository bookRepository, HomeService homeService) {
        this.bookRepository = bookRepository;
        this.homeService = homeService;
    }

    @GetMapping
    public String getHomeScreen(Model model, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 10);
        Page<BookEntity> books = bookRepository.findAll(pageRequest);

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

        return "index_cpy";
    }

    // Add book to basket directly from the home page
    @PostMapping("/addToBasket/{bookId}")
    public String addToBasketFromHome(@PathVariable Long bookId, HttpSession session) {
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            List<BookEntity> basketBooks = (List<BookEntity>) session.getAttribute("basket");
            if (basketBooks == null) {
                basketBooks = new ArrayList<>();
            }

            basketBooks.add(book);
            session.setAttribute("basket", basketBooks);

            System.out.println("Book added from home page. Basket now contains " + basketBooks.size() + " items.");
        } else {
            System.err.println("Book not found for ID: " + bookId);
        }

        return "redirect:/home_cpy"; // Redirect to the same page to refresh
    }
}
