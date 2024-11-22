package com.project.service;

import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.User;
import com.project.repository.BasketRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

  private final BasketRepository basketRepository;
  private final UserService userService;
  public BasketService(BasketRepository basketRepository, UserService userService) {
    this.basketRepository = basketRepository;

    this.userService = userService;
  }


  public void saveOrderToDatabase (Basket basket) {
    basketRepository.save(basket);
  }

  public Basket showBasket () {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(username);
    AccountEntity accountEntity = user.getAccount();
    return accountEntity.getBasket();
  }

}
