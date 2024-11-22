package com.project.service;

import com.project.entity.*;
import com.project.repository.BasketRepository;
import com.project.repository.OrderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;
    public BasketService(BasketRepository basketRepository, UserService userService) {
        this.basketRepository = basketRepository;

        this.userService = userService;
    }


    public void saveOrderToDatabase (basket basket) {
        basketRepository.save(basket);
    }

    public basket showBasket () {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        AccountEntity accountEntity = user.getAccount();
        return accountEntity.getBasket();
    }

}
