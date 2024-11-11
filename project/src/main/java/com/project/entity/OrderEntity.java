package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_id")
  private Long id;

  private String orderId;

  private String Date;

  private Double totalAmount;

  private String email;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private int zipCode;

  private String address;

  private String birthday;


  @ManyToOne
  private AccountEntity accountEntity;

  @OneToMany
  @JoinColumn(name = "order_id")
  private List<OrderDetailsEntity> orderDetails;

  @OneToOne
  @JoinColumn(name = "order_id")
  private PaymentEntity payment;



}
