package com.swp391.JewelrySalesSystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  ACCOUNT_NOT_FOUND("1001", "Can not find account with phone."),
  USER_IS_DEACTIVATED("1002", "Your account is deactivated"),
  BAD_CREDENTIAL_LOGIN("1003", "Invalid username or password"),
  ENTITY_NOT_FOUND_OR_DELETED("1004", "Entity Not Found or Deleted"),

  //  Not found price list
  GEM_PRICE_LIST_NOT_FOUND("1005", "Gem price list not found");

  private final String code;
  private final String message;
}
