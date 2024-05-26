package com.swp391.JewelrySalesSystem.enums;

public enum CategoryType {
  GOLD,
  DIAMOND,
  JEWELRY;

  private boolean isGold() {
    return CategoryType.GOLD == this;
  }

  private boolean isDiamond() {
    return CategoryType.DIAMOND == this;
  }

  private boolean isJewelry() {
    return CategoryType.JEWELRY == this;
  }
}
