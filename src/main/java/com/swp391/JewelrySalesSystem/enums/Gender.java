package com.swp391.JewelrySalesSystem.enums;

public enum Gender {
  MALE,
  FEMALE,
  UNISEX;

  public boolean isMale() {
    return Gender.MALE == this;
  }

  public boolean isFemale() {
    return Gender.FEMALE == this;
  }

  public boolean isUnisex() {
    return Gender.UNISEX == this;
  }
}
