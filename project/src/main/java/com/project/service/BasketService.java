package com.project.service;

import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.User;
import com.project.repository.BasketRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasketService {

  private final BasketRepository basketRepository;
  private final UserService userService;
  public BasketService(BasketRepository basketRepository, UserService userService) {
    this.basketRepository = basketRepository;

    this.userService = userService;
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




}
