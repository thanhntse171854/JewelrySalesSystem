package com.swp391.JewelrySalesSystem.enums;

public enum Gender {
  MALE,
  FEMALE;

  public boolean isMale() {
    return Gender.MALE == this;
  }

  public boolean isFemale() {
    return Gender.FEMALE == this;
  }
}
