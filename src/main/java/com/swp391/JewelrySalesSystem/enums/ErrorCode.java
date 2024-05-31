package com.swp391.JewelrySalesSystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  ACCOUNT_NOT_FOUND("1001", "Can not find account with phone."),
  USER_IS_DEACTIVATED("1002", "Your account is deactivated"),
  BAD_CREDENTIAL_LOGIN("1003", "Invalid username or password"),
  PRODUCT_NOT_FOUND_OR_DELETED("1004", "Product Not Found or Deleted"),

  GEM_PRICE_LIST_NOT_FOUND("1005", "Gem price list not found"),
  MATERIAL_PRICE_LIST_NOT_FOUND("1006", "Gem price list not found"),
  SIZE_NOT_FOUND("1007", "Product size does not exist or is out of stock");

  private final String code;
  private final String message;
}
