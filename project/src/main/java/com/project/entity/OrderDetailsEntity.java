package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_details_id")
  private Long id;

  private Integer quantity;

  @ManyToOne
  private BookEntity book;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity orderEntity;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private AccountEntity accountEntity;




}
