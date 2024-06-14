package com.swp391.JewelrySalesSystem.enums;

public enum PaymentStatus {
  PENDING,
  SUCCESS,
  NONE,
  FAIL;

  public boolean isPending() {
    return PaymentStatus.PENDING == this;
  }

  public boolean isSuccess() {
    return PaymentStatus.SUCCESS == this;
  }

  public boolean isFail() {
    return PaymentStatus.FAIL == this;
  }

  public boolean isNone() {
    return PaymentStatus.NONE == this;
  }
}
