package com.project.service;

import com.project.entity.OrderEntity;
import com.project.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public void saveOrderToDatabase (OrderEntity order) {
    orderRepository.save(order);
  }






}
