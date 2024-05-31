package com.swp391.JewelrySalesSystem.enums;

public enum PaymentMethod {
  CASH,
  CREDIT;

  public boolean isCash() {
    return PaymentMethod.CASH == this;
  }

  public boolean isCredit() {
    return PaymentMethod.CREDIT == this;
  }
}
