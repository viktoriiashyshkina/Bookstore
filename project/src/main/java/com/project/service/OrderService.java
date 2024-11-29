package com.project.service;

import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.BasketDetails;
import com.project.entity.GiftCardEntity;
import com.project.entity.OrderDetailsEntity;
import com.project.entity.OrderEntity;
import com.project.entity.PaymentEntity;
import com.project.repository.AccountRepository;
import com.project.repository.BasketRepository;
import com.project.repository.BookRepository;
import com.project.repository.GiftCardRepository;
import com.project.repository.OrderRepository;
import com.project.repository.PaymentRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final AccountRepository accountRepository;
  private final BasketRepository basketRepository;
  private final PaymentRepository paymentRepository;
  private final BookRepository bookRepository;
  private final GiftCardRepository giftCardRepository;


  public OrderService(OrderRepository orderRepository, AccountRepository accountRepository,
      BasketRepository basketRepository, PaymentRepository paymentRepository,
      BookRepository bookRepository, GiftCardRepository giftCardRepository) {
    this.orderRepository = orderRepository;
    this.accountRepository = accountRepository;
    this.basketRepository = basketRepository;
    this.paymentRepository = paymentRepository;
    this.bookRepository = bookRepository;
    this.giftCardRepository = giftCardRepository;
  }

  public OrderEntity createOrder(Long accountId) {
    // Fetch the account
    AccountEntity account = accountRepository.findById(accountId)
        .orElseThrow(() -> new RuntimeException("Account not found"));

    // Fetch the basket for the account
    Basket basket = basketRepository.findByAccountEntity(account)
        .orElseThrow(() -> new RuntimeException("Basket not found"));

    // Create Order
    OrderEntity order = new OrderEntity();
    order.setAccountEntity(account);
    order.setDate(LocalDate.now().toString());
    order.setTotalAmount(basket.getTotalAmount());

    // Add OrderDetails from Basket
    List<OrderDetailsEntity> orderDetails = basket.getBasketDetails().stream().map(bd -> {
      OrderDetailsEntity detail = new OrderDetailsEntity();
      detail.setBook(bd.getBook());
      detail.setQuantity(bd.getQuantity());
      detail.setOrderEntity(order);
      return detail;
    }).collect(Collectors.toList());
    order.setOrderDetails(orderDetails);

    // Clear the basket
    basket.getBasketDetails().clear();
    basket.setTotalAmount(BigDecimal.ZERO);
    basketRepository.save(basket);

    // Save Order
    orderRepository.save(order);
    return order;
}
  public long getOrderCount() {
    return orderRepository.count();
  }


  public OrderEntity saveOrder(OrderEntity order) {
    return orderRepository.save(order);
  }

  public List<OrderEntity> getOrdersByAccount(Long accountId) {
    AccountEntity account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    return orderRepository.findByAccountEntity(account);
  }


  public OrderEntity getOrderDetails(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("Order not found"));
  }


  @Transactional
  public OrderEntity processPayment(Long orderId, PaymentEntity paymentDetails) {
    OrderEntity order = getOrderDetails(orderId);

    paymentDetails.setOrder(order);
    paymentRepository.save(paymentDetails);

    order.setPayment(paymentDetails);
    return orderRepository.save(order);
  }


}
