package com.project.service;


import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.entity.OrderDetailsEntity;
import com.project.entity.OrderEntity;
import com.project.entity.User;
import com.project.repository.OrderDetailsRepository;
import com.project.repository.OrderRepository;
import com.project.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private OrderDetailsRepository orderDetailsRepository;
  @Autowired
  private PaymentRepository paymentRepository;

  public List<OrderEntity> getOrdersByUser(User user) {
    return orderRepository.findByAccountEntity(user.getAccount()); // Assumes you have an order repository
  }

  public long getOrderCount() {
    return orderRepository.count();
  }

  public List<OrderEntity> getRecentOrders() {
    return orderRepository.findTop10ByOrderByIdDesc();  // Assumes OrderEntity has a field 'orderDate'
  }

  @Transactional
  public OrderEntity createOrder(AccountEntity account, Basket basket) {
    if (basket == null || basket.getBasketDetails().isEmpty()) {
      throw new RuntimeException("Basket is empty, cannot create order");
    }
    if (account == null) {
      throw new RuntimeException("Account is null, cannot create order");
    }

    // Create and save the order entity
    OrderEntity order = new OrderEntity();
    order.setAccountEntity(account);
    order.setDate(LocalDateTime.now().toString());
    order.setTotalAmount(basket.getTotalAmount());
    order = orderRepository.save(order);

    // Create and save order details
    // Set order reference
    // Set account reference
    List<OrderDetailsEntity> orderDetailsList = new ArrayList<>();
    for (BasketDetails basketDetails : basket.getBasketDetails()) {
      OrderDetailsEntity orderDetails = new OrderDetailsEntity();
      orderDetails.setBook(basketDetails.getBook());
      orderDetails.setQuantity(basketDetails.getQuantity());
      orderDetails.setOrderEntity(order);  // Set order reference
      orderDetails.setAccountEntity(account); // Set account reference
      OrderDetailsEntity apply = orderDetails;
      orderDetailsList.add(apply);
    }

    orderDetailsRepository.saveAll(orderDetailsList);

    return order;
  }
}







