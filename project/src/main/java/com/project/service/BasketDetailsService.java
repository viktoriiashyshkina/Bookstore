package com.project.service;

import com.project.entity.BasketDetails;
import com.project.repository.BasketDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class BasketDetailsService {

  private final BasketDetailsRepository basketDetailsRepository;

  public BasketDetailsService(BasketDetailsRepository basketDetailsRepository) {
    this.basketDetailsRepository = basketDetailsRepository;
  }

  public BasketDetails getBaskedDetailsById (Long id) {
    return basketDetailsRepository.findById(id).orElse(null);
  }


  public void saveOrderToDatabase (BasketDetails incompleteOrder) {basketDetailsRepository.save(incompleteOrder);
  }

}