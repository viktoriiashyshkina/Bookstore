package com.project.service;

import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.repository.BasketDetailsRepository;
import com.project.repository.BasketRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BasketDetailsCleanupService {

  @Autowired
  private BasketDetailsRepository basketDetailsRepository;

  @Autowired BasketRepository basketRepository;

  @Autowired
  private EntityManager entityManager;

  public boolean cleanupBasketDetails(Long basketId, Duration expirationTime) {

    List<BasketDetails> basketDetails = basketDetailsRepository.findByBasketId(basketId);

    System.out.println("BasketDetailsList: " + basketDetails);

    if (basketDetails.isEmpty()) {
      return true; // Stop scheduler if no items exist for this basket
    }

    LocalDateTime expirationThreshold = LocalDateTime.now().minus(expirationTime);
    List<BasketDetails> expiredItems = basketDetails.stream()
        .filter(item -> item.getUpdatedAt().isBefore(expirationThreshold))
        .toList();

    System.out.println("expired items: " + expiredItems);

    if (!expiredItems.isEmpty()) {
      basketDetailsRepository.deleteExpiredItems(expirationThreshold, basketId);
      Basket basket = basketRepository.findById(basketId).orElse(null);
      basket.setTotalAmount(BigDecimal.valueOf(0));
     //basketRepository.save(basket);
    }

    // Check if all items are removed
    return basketDetailsRepository.findByBasketId(basketId).isEmpty();
  }
}

//@Service
//@Transactional
//public class BasketDetailsCleanupService {
//
//  @Autowired
//  private BasketDetailsRepository basketDetailsRepository;
//
//  @Autowired
//  private BasketRepository basketRepository; // Assuming you have a BasketRepository
//
//  @Autowired
//  private AccountService accountService; // Service to get the current logged-in user account
//
//  // Method to cleanup basket details and delete the basket if necessary
//  public boolean cleanupBasketDetails(Long basketId, Duration expirationTime) {
//    System.out.println("here5");
//    List<BasketDetails> basketDetails = basketDetailsRepository.findByBasketId(basketId);
//
//    if (basketDetails.isEmpty()) {
//      System.out.println("here6");
//      return true; // Stop scheduler if no items exist for this basket
//    }
//
//    LocalDateTime expirationThreshold = LocalDateTime.now().minus(expirationTime);
//    List<BasketDetails> expiredItems = basketDetails.stream()
//        .filter(item -> item.getUpdatedAt().isBefore(expirationThreshold))
//        .toList();
//
//    System.out.println("expired items: " + expiredItems);
//
//    if (!expiredItems.isEmpty()) {
//      System.out.println("here7");
//      // Delete expired items from the basket
//      basketDetailsRepository.deleteExpiredItems(expirationThreshold, basketId);
//    }
//
//    // Check if all items are removed and delete the basket if empty
//    List<BasketDetails> remainingItems = basketDetailsRepository.findByBasketId(basketId);
//
//    System.out.println("remaining items: " +remainingItems);
//
//    if (remainingItems.isEmpty()) {
//      System.out.println("here8");
//      // Delete the basket for the logged-in user
//      deleteBasketForLoggedInUser(basketId);
//      return true; // Stop scheduler since the basket is empty
//    }
//
//    return false; // Continue the cleanup process if there are still items
//  }
//
//  private void deleteBasketForLoggedInUser(Long basketId) {
//    System.out.println("here9");
//    // Retrieve the logged-in user's account
//    AccountEntity loggedInAccount = accountService.getLoggedInAccount();
//
//    System.out.println("logged in account: " + loggedInAccount);
//    System.out.println("basket for logged in account: " + loggedInAccount.getBasket());
//    System.out.println("basket id: " +loggedInAccount.getBasket().getId());
//    System.out.println("basketId from before: " +basketId);
//
//    if (loggedInAccount != null && loggedInAccount.getBasket() != null
//        && loggedInAccount.getBasket().getId().equals(basketId)) {
//      System.out.println("here10");
//      // If the current user's basket matches the one that is empty, delete it
//      Basket basketToDelete = loggedInAccount.getBasket();
//      basketRepository.delete(basketToDelete);
//    }
//  }
//}

