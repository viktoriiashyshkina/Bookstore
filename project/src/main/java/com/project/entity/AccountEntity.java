package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long id;

  private String username;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private int zipCode;

  private String address;

  private String birthday;

  private String phoneNumber;

  @OneToMany
  @JoinColumn(name ="account_id")
  private List<OrderEntity> orderList;

  @OneToOne
  @JoinColumn(name ="account_id")
  private Basket basket;





}
