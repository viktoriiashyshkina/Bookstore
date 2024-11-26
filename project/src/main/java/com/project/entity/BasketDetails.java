package com.project.entity;

import com.project.service.BasketService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDetails {

  public BasketDetails(Integer quantity, BookEntity book) {
    this.quantity = quantity;
    this.book = book;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Integer quantity;

  @ManyToOne
  private BookEntity book;

//  @ManyToOne
//  private Basket basket;



}
