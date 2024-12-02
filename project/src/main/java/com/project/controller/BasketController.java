package com.project.controller;

import com.project.entity.AccountEntity;
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


  @GetMapping("/homeTest/basket")
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
      return "redirect:/homeTest?basketLoginError";
    }
    return "ShowBasket";
  }



  @PostMapping("/homeTest/updateBasket/{bookId}")
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

  @PostMapping("/homeTest/removeFromBasket/{bookId}")
  public String removeFromBasket(@PathVariable Long bookId) {
    // Call service method to remove the item from the basket
    System.out.println("bookId: "+ bookId);
    List<BasketDetails> details = basketService.getBasketFromLoggedInUser()
        .getBasketDetails();
    BasketDetails detail = null;
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
    return "redirect:/homeTest/basket";
  }






//  @GetMapping("/home/basket/checkout")
//  public String checkoutBasket() {
//    // Get the logged-in user's account ID
//    String username = SecurityContextHolder.getContext().getAuthentication().getName();
//    AccountEntity account = basketService.getBasketFromLoggedInUser().getAccountEntity();
//    if (account == null) {
//      return "redirect:/login";
//    }
//
//    // Redirect to the order creation endpoint
//    return "redirect:/order-create";
//  }
}
