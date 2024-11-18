package com.project.service;

import com.project.entity.OrderEntity;
import com.project.entity.PaymentEntity;
import com.project.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentEntity processPayment(OrderEntity order, String paymentMethod, String cardNumber, String expirationDate, String cvv) {
        if (!isValidCard(cardNumber, expirationDate, cvv)) {
            throw new IllegalArgumentException("Invalid payment details");
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus("SUCCESS");
        payment.setOrder(order);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    private boolean isValidCard(String cardNumber, String expirationDate, String cvv) {
        return cardNumber.length() == 16 && cvv.length() == 3;
    }
}
