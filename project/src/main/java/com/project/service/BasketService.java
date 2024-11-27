package com.project.service;

import com.project.config.DynamicSchedulingConfig;
import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.entity.User;
import com.project.repository.BasketDetailsRepository;
import com.project.repository.BasketRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasketService {


  @Autowired
  private DynamicSchedulingConfig dynamicSchedulingConfig;
  private final BasketRepository basketRepository;
  private final UserService userService;

  private final BasketDetailsRepository basketDetailsRepository;
  public BasketService(BasketRepository basketRepository, UserService userService,
      BasketDetailsRepository basketDetailsRepository) {
    this.basketRepository = basketRepository;

    this.userService = userService;
    this.basketDetailsRepository = basketDetailsRepository;
  }

  public Basket getBaskedById (Long id) {
    return basketRepository.findById(id).orElse(null);
  }

  public void saveBasketToDatabase (Basket basket) {
    basketRepository.save(basket);
  }


  @Transactional
  public Basket getBasketFromLoggedInUser () {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(username);
    AccountEntity accountEntity = user.getAccount();
    return accountEntity.getBasket();
  }


  public BigDecimal calculateTotalAmount (List<BasketDetails> basketDetails) {
    BigDecimal totalAmount = BigDecimal.valueOf(0.0);
    if (basketDetails != null ) {

      for (int i = 0; i < basketDetails.size(); i++) {
        BigDecimal priceOfItemWithQuantity = basketDetails.get(i).getBook().getPrice().multiply(
            BigDecimal.valueOf(basketDetails.get(i).getQuantity()));
        totalAmount = totalAmount.add(priceOfItemWithQuantity);
      }
    }
    return totalAmount;
  }


//  @Transactional
//  public void addBasketDetails(Long basketId, BasketDetails basketDetails) {
//    // Retrieve the Basket entity by its ID
//    Basket basket = basketRepository.findById(basketId)
//        .orElseThrow(() -> new EntityNotFoundException("Basket not found with ID: " + basketId));
//
//    // Set the Basket entity in BasketDetails
//    basketDetails.setBasket(basket);
//    basketDetails.setUpdatedAt(LocalDateTime.now());
//
//    // Save the BasketDetails entity to the database
//    basketDetailsRepository.save(basketDetails);
//
//    // Start cleanup scheduler for this basket ID
//    dynamicSchedulingConfig.startBasketDetailsCleanupScheduler(basketId);
//  }





}
