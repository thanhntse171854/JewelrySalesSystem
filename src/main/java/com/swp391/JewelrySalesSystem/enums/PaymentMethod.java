package com.swp391.JewelrySalesSystem.enums;

public enum PaymentMethod {
  NONE,
  CASH,
  CREDIT;

  public boolean isCash() {
    return PaymentMethod.CASH == this;
  }

  public boolean isCredit() {
    return PaymentMethod.CREDIT == this;
  }

  public boolean isNone() {
    return PaymentMethod.NONE == this;
  }
}
