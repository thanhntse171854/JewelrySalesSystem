package com.swp391.JewelrySalesSystem.enums;

public enum PaymentMethod {
  CASH,
  QR;

  public boolean isCash() {
    return PaymentMethod.CASH == this;
  }

  public boolean isQR() {
    return PaymentMethod.QR == this;
  }
}
