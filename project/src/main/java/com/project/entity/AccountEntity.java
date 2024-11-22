package com.project.entity;

import jakarta.persistence.*;

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

  private String username = getEmail();

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
  private basket basket;






}
