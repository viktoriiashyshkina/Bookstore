package com.project.controller;

import com.project.entity.OrderEntity;
import com.project.entity.PaymentEntity;
import com.project.repository.OrderRepository;
import com.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentController(PaymentService paymentService, OrderRepository orderRepository) {
        this.paymentService = paymentService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/process")
    public String processPayment(
            @RequestParam Long orderId,
            @RequestParam String paymentMethod,
            @RequestParam String cardNumber,
            @RequestParam String expirationDate,
            @RequestParam String cvv,
            Model model) {

        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            model.addAttribute("paymentStatus", "Order not found for ID: " + orderId);
            return "paymentStatus"; // Show an error status
        }

        OrderEntity order = optionalOrder.get();

        PaymentEntity payment = paymentService.processPayment(order, paymentMethod, cardNumber, expirationDate, cvv);

        model.addAttribute("paymentStatus", payment.getStatus());
        return "paymentStatus";
    }
}
