package com.swp391.JewelrySalesSystem.enums;

public enum DeliveryStatus {
  PENDING,
  SUCCESS,
  FAIL;

  public boolean isPending() {
    return DeliveryStatus.PENDING == this;
  }

  public boolean isSuccess() {
    return DeliveryStatus.SUCCESS == this;
  }

  public boolean isFail() {
    return DeliveryStatus.FAIL == this;
  }
}
