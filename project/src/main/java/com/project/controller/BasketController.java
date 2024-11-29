package com.project.controller;

import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.entity.BookEntity;
import com.project.repository.BasketDetailsRepository;
import com.project.repository.BookRepository;
import com.project.service.BasketDetailsService;
import com.project.service.BasketService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/home/user")
public class BasketController {


  private final BookRepository bookRepository;
  private final BasketDetailsService basketDetailsService;
  private final BasketService basketService;

  private final BasketDetailsRepository basketDetailsRepository;

  public BasketController(BookRepository bookRepository, BasketDetailsService basketDetailsService,
      BasketService basketService, BasketDetailsRepository basketDetailsRepository) {
    this.bookRepository = bookRepository;
    this.basketDetailsService = basketDetailsService;
    this.basketService = basketService;
    this.basketDetailsRepository = basketDetailsRepository;
  }


  @GetMapping("/home/basket")
  public String getBasket(Model model) {

    if (!Objects.equals(SecurityContextHolder.getContext().getAuthentication().getName(),
        "anonymousUser")) {
      if (basketService.getBasketFromLoggedInUser().getBasketDetails().size() != 0) {

        System.out.println(basketService.getBasketFromLoggedInUser().getBasketDetails().size());

        Basket basket = basketService.getBasketFromLoggedInUser();
        List<BasketDetails> basketDetails = basket.getBasketDetails();


        for (BasketDetails detail : basketDetails) {
          byte[] imageData = detail.getBook().getImage();
          if (imageData != null) {
            String imageDataBase64 = Base64.getEncoder().encodeToString(imageData);
            detail.getBook().setImageDataBase64(imageDataBase64);
          }
        }


        model.addAttribute("basketDetails", basketDetails);
        model.addAttribute("basket", basket);

      } else  {
        model.addAttribute("emptyBasket", "true");
      }


    } else {
      return "redirect:/login";
    }
    return "ShowBasket";
  }



  @PostMapping("/home/user/updateBasket/{bookId}")
  public String updateBasket(@PathVariable Long bookId, @RequestParam int quantity, Model model) {
    // Update the basket details for the specific book
    basketService.updateBasketDetails(bookId, quantity);

    // Retrieve updated basket details and add to the model
    List<BasketDetails> updatedDetails = basketService.getBasketFromLoggedInUser()
        .getBasketDetails();
    model.addAttribute("details", updatedDetails);

    // Redirect back to the cart page
    return "redirect:/home/basket";
  }

  @PostMapping("/home/user/removeFromBasket/{bookId}")
  public String removeFromBasket(@PathVariable Long bookId) {
    // Call service method to remove the item from the basket
    System.out.println("bookId: "+ bookId);
    List<BasketDetails> details = basketService.getBasketFromLoggedInUser()
        .getBasketDetails();
    BasketDetails detail = null;
//    for (int i=0; i<details.size(); i++) {
//      if (Objects.equals(details.get(i).getBook().getId(), bookId)) {
//        detail = details.get(i);
//        break;
//      }
//    }

    for (int i = 0; i < details.size(); i++) {
      if (Objects.equals(details.get(i).getBook().getId(), bookId)) {
        detail = details.get(i);
        System.out.println("Found detail: " + detail);
        break;
      }
    }

    if (detail == null) {
      System.out.println("No matching BasketDetails found for bookId: " + bookId);
    } else {
      basketService.removeBasketDetail(detail);

    }


    System.out.println("Detail to remove: " +detail);
    
    //basketService.removeBasketDetail(detail);

    // Redirect back to the cart page to reflect the changes
    return "redirect:/home/basket";
  }


//    @PostMapping("/basket/view")
//  public String viewBasket (Model model) {
//    Basket basket=basketService.getBasketFromLoggedInUser();
//    List<BasketDetails> basketDetails = basket.getBasketDetails();
//      System.out.println("im here");
//
//      double totalPrice = basketDetails.stream()
//          .mapToDouble(detail -> detail.getQuantity()*Double.valueOf(
//              String.valueOf(detail.getBook().getPrice())))
//          .sum();
//
//    model.addAttribute("basket", basketDetails);
//      model.addAttribute("totalPrice", totalPrice);
//
//
//    return "redirect:/home/user#basket";
//  }


 //  Add book to basket
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

  // Remove book from basket
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
