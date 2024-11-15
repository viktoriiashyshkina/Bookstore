package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name ="payment_id")
  private Long id;

  private String paymentMethod;

  private Double Amount;

  private String status;

  @OneToOne
  @JoinColumn(name = "payment_id")
  private OrderEntity order;

}
