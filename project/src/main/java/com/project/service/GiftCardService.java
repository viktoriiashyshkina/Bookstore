package com.project.service;

import com.project.entity.GiftCardEntity;
import com.project.entity.User;
import com.project.repository.AccountRepository;
import com.project.repository.GiftCardRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCardService {

  @Autowired
  private GiftCardRepository giftCardRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private UserRepository userRepository;

  // Method to redeem the gift card and update the account balance using the username
  public String redeemGiftCard(String cardCode, String username) {
    // Fetch the gift card from the database using the cardCode
    GiftCardEntity giftCard = giftCardRepository.findByCardCode(cardCode)
        .orElseThrow(() -> new RuntimeException("Gift Card not found"));

    // Ensure that the gift card has not already been redeemed
    if (giftCard.isRedeemed()) {
      throw new RuntimeException("This gift card has already been redeemed");
    }

    // Fetch the user's account using the username
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new RuntimeException("User not found");
    }

    // Add the gift card balance to the user's account balance
    user.getAccount().setBalance(user.getAccount().getBalance().add(giftCard.getBalance()));
    userRepository.save(user); // Save the updated user with the new balance

    // Mark the gift card as redeemed
    giftCard.setRedeemed(true);
    giftCard.setAccountEntity(user.getAccount());
    giftCardRepository.save(giftCard); // Save the updated gift card status

    // Return the success message
    return "Gift Card redeemed successfully! Your new balance is: " + user.getAccount().getBalance();
  }
}

