package com.project.service;

import com.project.entity.AccountEntity;
import com.project.entity.OrderEntity;
import com.project.entity.User;
import com.project.repository.AccountRepository;
import com.project.repository.UserRepository;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  private final UserRepository userRepository;

  public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
  }

  public AccountEntity getAccount (String username) {
    return accountRepository.findByUsername(username);
  }

  public AccountEntity updateAccount(AccountEntity account) {
    return accountRepository.save(account); // Save and return the updated account entity
  }

  @Transactional
  public AccountEntity getLoggedInAccount() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username);
    return user.getAccount();
  }

//  public List<OrderEntity> getAllOrdersFromLoggedInAccount () {
//    String accountName = getLoggedInAccount();
//    AccountEntity account = getAccount(accountName);
//    return account.getOrderList();
//  }




}
