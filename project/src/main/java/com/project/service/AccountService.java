package com.project.service;
//
//import com.project.entity.AccountEntity;
//import com.project.entity.OrderEntity;
//import com.project.repository.AccountRepository;
//import java.util.List;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AccountService {
//
//  private final AccountRepository accountRepository;
//
//  public AccountService(AccountRepository accountRepository) {
//    this.accountRepository = accountRepository;
//  }
//
//  public AccountEntity getAccount (String username) {
//    return accountRepository.findByUsername(username);
//  }
//  public AccountEntity updateAccount (AccountEntity account) {
//    accountRepository.findByUsername(account.getUsername());
//    account.setFirstName(account.getFirstName());
//    account.setLastName(account.getLastName());
//    account.setZipCode(account.getZipCode());
//    account.setAddress(account.getAddress());
//    account.setBirthday(account.getBirthday());
//    account.setPhoneNumber(account.getPhoneNumber());
//    return accountRepository.save(account);
//  }
//  public void saveAccountToDatabase (AccountEntity account) {
//     accountRepository.save(account);
//  }
//
//  public String getLoggedInAccount() {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    return auth.getName();
//  }
//
//  public List<OrderEntity> getAllOrdersFromLoggedInAccount () {
//    String accountName = getLoggedInAccount();
//    AccountEntity account = getAccount(accountName);
//    return account.getOrderList();
//  }
//
//
//

//}
