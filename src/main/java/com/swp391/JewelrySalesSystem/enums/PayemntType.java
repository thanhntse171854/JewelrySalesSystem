package com.swp391.JewelrySalesSystem.enums;

public enum PayemntType {
  ORDER_SELL,
  ORDER_PURCHASE;

  public boolean isOrderSell() {
    return PayemntType.ORDER_SELL == this;
  }

  public boolean isOrderPurchase() {
    return PayemntType.ORDER_PURCHASE == this;
  }
}
