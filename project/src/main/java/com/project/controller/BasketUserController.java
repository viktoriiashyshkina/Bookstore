package com.project.controller;

import com.project.config.DynamicSchedulingConfig;
import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.entity.BookEntity;
import com.project.entity.User;
import com.project.repository.AccountRepository;
import com.project.repository.BasketDetailsRepository;
import com.project.repository.BasketRepository;
import com.project.repository.BookRepository;
import com.project.service.AccountService;
import com.project.service.BasketDetailsService;
import com.project.service.BasketService;
import com.project.service.HomeService;
import com.project.service.UserService;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/homeTest")
public class BasketUserController {

  private final BookRepository bookRepository;

  private final HomeService homeService;

  private final BasketDetailsService basketDetailsService;

  private final BasketService basketService;

  private  final AccountService accountService;

  private final BasketRepository basketRepository;

  private final BasketDetailsRepository basketDetailsRepository;

  private final AccountRepository accountRepository;

  private final UserService userService;

  private final DynamicSchedulingConfig dynamicSchedulingConfig;

  public BasketUserController(BookRepository bookRepository, HomeService homeService,
      BasketDetailsService basketDetailsService, BasketService basketService,
      AccountService accountService, BasketRepository basketRepository,
      BasketDetailsRepository basketDetailsRepository, AccountRepository accountRepository,
      UserService userService, DynamicSchedulingConfig dynamicSchedulingConfig) {
    this.bookRepository = bookRepository;
    this.homeService = homeService;
    this.basketDetailsService = basketDetailsService;
    this.basketService = basketService;
    this.accountService = accountService;
    this.basketRepository = basketRepository;
    this.basketDetailsRepository = basketDetailsRepository;
    this.accountRepository = accountRepository;
    this.userService = userService;
    this.dynamicSchedulingConfig = dynamicSchedulingConfig;
  }


//  @GetMapping
//  public String getHomeScreen(Model model, Pageable pageable) {
//
//    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 10);
//
//    Page<BookEntity> books = bookRepository.findAll(pageRequest);
//
//    //List<BookEntity> books = bookRepository.findAll();
//    model.addAttribute("books", books);
//
//    // Retrieve and set the image data for each book
//    for (BookEntity book : books.getContent()) {
//      byte[] imageData = book.getImage();
//      if (imageData != null) {
//        String imageDataBase64 = Base64.getEncoder().encodeToString(imageData);
//        book.setImageDataBase64(imageDataBase64);
//      }
//    }
//
//    model.addAttribute("books", books.getContent());
//    model.addAttribute("currentPage", books.getNumber());
//    model.addAttribute("totalPages", books.getTotalPages());
//    model.addAttribute("totalItems", books.getTotalElements());
//
//    return "index";
//  }

//

//  @PostMapping("/addToBasket/{bookId}")
//  public String addToBasket(
//      @PathVariable("bookId") Long bookId,
//      @RequestParam Integer quantity,
//      Model model) {
//    BookEntity book = bookRepository.findById(bookId).orElse(null);
//    BasketDetails basketDetails = new BasketDetails();
//    basketDetails.setQuantity(quantity);
//    basketDetails.setBook(book);
//
//
//    //Get basket from logged in user, if basket is empty create new one
//    Basket basket = basketService.getBasketFromLoggedInUser();
//    System.out.println("The basket is: " + basket);
//    if (basket == null) {
//      Basket newBasket = new Basket();
//      List<BasketDetails> listOfBasketDetails = new ArrayList<>();
//      listOfBasketDetails.add(basketDetails);
//      newBasket.setBasketDetails(listOfBasketDetails);
//      AccountEntity account = accountService.getLoggedInAccount();
//      newBasket.setAccountEntity(account);
//      account.setBasket(newBasket);
//
//      basketService.saveBasketToDatabase(newBasket);
//      basketDetailsService.saveOrderToDatabase(basketDetails);
//      accountService.updateAccount(account);
//
//
//    } else {
//      List<BasketDetails> listOfBasketDetails = basket.getBasketDetails();
//      for(int i=0; i< listOfBasketDetails.size();i++) {
//        if(Objects.equals(listOfBasketDetails.get(i).getBook().getIsbn(),
//            basketDetails.getBook().getIsbn())){
//          listOfBasketDetails.get(i).setQuantity(listOfBasketDetails.get(i).getQuantity()+quantity);
//          basketDetailsService.saveOrderToDatabase(listOfBasketDetails.get(i));
//          break;
//        } else if (i==listOfBasketDetails.size()-1) {
//          listOfBasketDetails.add(basketDetails);
//          basketDetailsService.saveOrderToDatabase(basketDetails);
//        }
//      }
//
//    }
//
//    // display message in home that the book is successfully added to the basket
//    model.addAttribute("success", "The item was successfully added to the basket");
//
//    return "redirect:/home/user";
//
//  }


//  @PostMapping("/home/user/addToBasket/{bookId}")
//  public String addToBasket(
//      @PathVariable("bookId") Long bookId,
//      @RequestParam Integer quantity,
//      Model model
//  ) {
//    BookEntity book = bookRepository.findById(bookId).orElse(null);
////    String username = SecurityContextHolder.getContext().getAuthentication().getName();
////    User user = userService.findByUsername(username);
////    System.out.println("Logged in User is: "+user.getUsername());
////    AccountEntity accountEntity = user.getAccount();
////    System.out.println("Account Id is : " + accountEntity.getId());
//    Basket basket = basketService.getBasketFromLoggedInUser();
//    System.out.println("Basket is: " + basket);
//
//    if (basket == null) {
//      basket = new Basket();
//      basket.setBasketDetails(new ArrayList<>());
//      basket.setAccountEntity(accountService.getLoggedInAccount());
//      String username = SecurityContextHolder.getContext().getAuthentication().getName();
//      User user = userService.findByUsername(username);
//      AccountEntity accountEntity = user.getAccount();
//      accountEntity.setBasket(basket);
//      basketService.saveBasketToDatabase(basket);
//      accountService.updateAccount(accountEntity);
//    }
//
//    Optional<BasketDetails> existingDetail = basket.getBasketDetails().stream()
//        .filter(detail -> detail.getBook().getIsbn().equals(book.getIsbn()))
//        .findFirst();
//
//    if (existingDetail.isPresent()) {
//      // Update the quantity and refresh timestamp
//      BasketDetails detail = existingDetail.get();
//      detail.setQuantity(detail.getQuantity() + quantity);
//      detail.setUpdatedAt(LocalDateTime.now());
//      basketDetailsService.saveOrderToDatabase(detail);
//    } else {
//      // Create a new BasketDetails
//      BasketDetails newDetail = new BasketDetails();
//     // newDetail.setBasket(basket);
//      newDetail.setBook(book);
//      newDetail.setQuantity(quantity);
//      newDetail.setUpdatedAt(LocalDateTime.now());
//      basketDetailsService.saveOrderToDatabase(newDetail);
//      basket.getBasketDetails().add(newDetail);
//      basketService.saveBasketToDatabase(basket);
//
//
//    }
//
//    //basketService.saveBasketToDatabase(basket);
//
//    model.addAttribute("success", "The item was successfully added to the basket");
//    return "redirect:/home/user";
//  }




  @PostMapping("/addToBasket/{bookId}")
  public String addToBasket(
      @PathVariable("bookId") Long bookId,
      @RequestParam Integer quantity,
      Model model
  ) {
    // Find the book by its ID
    BookEntity book = bookRepository.findById(bookId).orElse(null);
    if (book == null) {
      model.addAttribute("error", "Book not found");
      return "redirect:/homeTest";
    }

    // Retrieve the basket for the currently logged-in user
    Basket basket = basketService.getBasketFromLoggedInUser();
    if (basket == null) {
      // If no basket exists, create a new one
      basket = new Basket();
      basket.setBasketDetails(new ArrayList<>());
      basket.setAccountEntity(accountService.getLoggedInAccount());

      // Associate the new basket with the user's account
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.findByUsername(username);
      AccountEntity accountEntity = user.getAccount();
      accountEntity.setBasket(basket);
      basketService.saveBasketToDatabase(basket);
      accountService.updateAccount(accountEntity);
    }

    // Check if the book is already in the basket
    Optional<BasketDetails> existingDetail = basket.getBasketDetails().stream()
        .filter(detail -> detail.getBook().getIsbn().equals(book.getIsbn()))
        .findFirst();

    if (existingDetail.isPresent()) {
      // Update the quantity and refresh the timestamp
      BasketDetails detail = existingDetail.get();
      detail.setQuantity(detail.getQuantity() + quantity);
      detail.setUpdatedAt(LocalDateTime.now());
      basketDetailsService.saveOrderToDatabase(detail);
    } else {
      // Create a new BasketDetails item
      BasketDetails newDetail = new BasketDetails();
      newDetail.setBasket(basket);
      newDetail.setBook(book);
      newDetail.setQuantity(quantity);
      newDetail.setUpdatedAt(LocalDateTime.now());
      basketDetailsService.saveOrderToDatabase(newDetail);
      basket.getBasketDetails().add(newDetail);
      basketService.saveBasketToDatabase(basket);
    }

    Long basketId = basket.getId();
    List<BasketDetails> basketDetailsList = basketDetailsRepository.findByBasketId(basketId);
    BigDecimal totalAmount = basketService.calculateTotalAmount(basketDetailsList);
    basket.setTotalAmount(totalAmount);
    basketService.saveBasketToDatabase(basket);


    resetExpirationTimeForAllItems(basket);
    // Start the cleanup scheduler for this basket
    dynamicSchedulingConfig.startBasketDetailsCleanupScheduler(basket.getId(), Duration.ofMinutes(5));

    // Display success message and redirect
    model.addAttribute("success", "The item was successfully added to the basket");
    return "redirect:/homeTest";
  }


  private void resetExpirationTimeForAllItems(Basket basket) {
    // Set the expiration time to the most recent item's updated time
    LocalDateTime latestUpdatedAt = basket.getBasketDetails().stream()
        .map(BasketDetails::getUpdatedAt)
        .max(LocalDateTime::compareTo)
        .orElse(LocalDateTime.now()); // Default to current time if no items exist

    // Update all BasketDetails with the latest expiration time
    for (BasketDetails detail : basket.getBasketDetails()) {
      detail.setUpdatedAt(latestUpdatedAt);
      basketDetailsService.saveOrderToDatabase(detail); // Persist the updated BasketDetails
    }
  }





//  @PostMapping("/basket/view")
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


//  @PostMapping("/basket/view")
//  @ResponseBody
//  public Map<String, Object> viewBasket() {
//    Basket basket = basketService.getBasketFromLoggedInUser();
//    List<BasketDetails> basketDetails = basket != null ? basket.getBasketDetails() : new ArrayList<>();
//
//      double totalPrice = basketDetails.stream()
//          .mapToDouble(detail -> detail.getQuantity()*Double.valueOf(
//              String.valueOf(detail.getBook().getPrice())))
//          .sum();
//
//    // Return basket details as a response
//    Map<String, Object> response = new HashMap<>();
//    response.put("basket", basketDetails);
//    response.put("totalPrice", totalPrice);
//    return response;
//  }



}
