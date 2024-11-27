package com.project.service;

import com.project.entity.BasketDetails;
import com.project.repository.BasketDetailsRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasketDetailsService {

  private final BasketDetailsRepository basketDetailsRepository;

  public BasketDetailsService(BasketDetailsRepository basketDetailsRepository) {
    this.basketDetailsRepository = basketDetailsRepository;
  }

  public BasketDetails getBaskedDetailsById (Long id) {
    return basketDetailsRepository.findById(id).orElse(null);
  }

@Transactional
  public void saveOrderToDatabase (BasketDetails incompleteOrder) {basketDetailsRepository.save(incompleteOrder);
  }

//  public boolean checkAndCleanupExpiredBasketDetails(Long basketId) {
//    List<BasketDetails> basketDetails = basketDetailsRepository.findByBasketId(basketId);
//    if (basketDetails.isEmpty()) {
//      return true; // Stop scheduler if no items are associated with this basket
//    }
//
//    LocalDateTime expirationThreshold = LocalDateTime.now().minusMinutes(30);
//    List<BasketDetails> expiredItems = basketDetails.stream()
//        .filter(item -> item.getUpdatedAt().isBefore(expirationThreshold))
//        .toList();
//
//    if (!expiredItems.isEmpty()) {
//      basketDetailsRepository.deleteAll(expiredItems); // Remove expired items
//    }
//
//    return basketDetailsRepository.findByBasketId(basketId).isEmpty();
//  }


}