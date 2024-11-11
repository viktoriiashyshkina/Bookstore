package com.project.util;

import java.util.Random;

public class OrderNumberGenerator {

  public static final int ORDER_NUMBER_LENGTH = 5;

  public static String generateOrderNumber() {
    Random random = new Random();
    StringBuilder accountNumber = new StringBuilder();

    for (int i = 0; i < ORDER_NUMBER_LENGTH; i++) {
      int digit = random.nextInt(10);
      accountNumber.append(digit);
    }

    return accountNumber.toString();
  }

}
