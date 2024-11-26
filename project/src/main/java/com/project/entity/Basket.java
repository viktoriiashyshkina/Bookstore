package com.project.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Basket {

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String date) {
    Date = date;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public int getZipCode() {
    return zipCode;
  }

  public void setZipCode(int zipCode) {
    this.zipCode = zipCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public AccountEntity getAccountEntity() {
    return accountEntity;
  }

  public void setAccountEntity(AccountEntity accountEntity) {
    this.accountEntity = accountEntity;
  }

  public List<BasketDetails> getBasketDetails() {
    return basketDetails;
  }

  public void setBasketDetails(List<BasketDetails> basketDetails) {
    this.basketDetails = basketDetails;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "basket_id")
  private Long id;

  private String Date;

  private Double totalAmount;

  private String email;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private int zipCode;

  private String address;

  private String birthday;


  @OneToOne
  @JoinColumn(name = "account_id")
  private AccountEntity accountEntity;

 @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
 // @OneToMany
  @JoinColumn
  private List<BasketDetails> basketDetails;

//  @OneToOne
//  @JoinColumn(name = "order_id")
//  private PaymentEntity payment;


}
