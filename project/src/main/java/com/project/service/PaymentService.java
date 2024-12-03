package com.project.service;

import com.project.entity.AccountEntity;
import com.project.entity.Basket;
import com.project.entity.OrderEntity;
import com.project.entity.User;
import com.project.repository.AccountRepository;
import com.project.repository.OrderRepository;
import com.project.repository.PaymentRepository;
import com.project.repository.UserRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BasketService basketService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;

    @Transactional
    public String processPayment(Long userId) {
        // Fetch the user's account using the userId
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the account and basket for the logged-in user
        AccountEntity account = accountService.getAccount(user.getUsername());
        Basket basket = basketService.getBasketFromLoggedInUser();

        if (account == null || basket == null || basket.getTotalAmount() == null
            || basket.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Basket is empty or total amount is invalid.");
        }

        // Check if the user has sufficient balance
        if (account.getBalance().compareTo(basket.getTotalAmount()) < 0) {
            return "Insufficient balance. Please add money or redeem a gift card.";
        }

        // Deduct the basket total from the user's account balance
        account.setBalance(account.getBalance().subtract(basket.getTotalAmount()));
        accountRepository.save(account); // Save the updated account balance

        // Create and save the order from the basket details
        orderService.createOrder(account, basket);

        return "Payment successful! Your order has been placed. Your new balance is: "
            + account.getBalance();
    }
}


    //    private final PaymentRepository paymentRepository;
//
//    public PaymentService(PaymentRepository paymentRepository) {
//        this.paymentRepository = paymentRepository;
//    }
//
//    public PaymentEntity processPayment(OrderEntity order, String paymentMethod, String cardNumber, String expirationDate, String cvv) {
//        if (!isValidCard(cardNumber, expirationDate, cvv)) {
//            throw new IllegalArgumentException("Invalid payment details");
//        }
//
//        PaymentEntity payment = new PaymentEntity();
//        payment.setPaymentMethod(paymentMethod);
//        payment.setAmount(order.getTotalAmount());
//        payment.setStatus("SUCCESS");
//        payment.setOrder(order);
//        payment.setPaymentDate(LocalDateTime.now());
//
//        return paymentRepository.save(payment);
//    }
//
//    private boolean isValidCard(String cardNumber, String expirationDate, String cvv) {
//        return cardNumber.length() == 16 && cvv.length() == 3;
//    }

