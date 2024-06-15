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
  PRODUCT_IS_EXIST("1007", "Product code is exist!"),
  ORDER_NOT_FOUND("1008", "Order not found"),
  CATEGORY_NOT_FOUND("1009", "Category not found"),
  MATERIAL_NOT_FOUND("1010", "Material not found"),
  PAYMENT_FAIL("1011", "Payment doesn't successful"),
  ORDER_DELETE_FAIL("1012", "The order has been paid and delivered"),
  ORDER_ASSIGNED("1013", "The order has been delivered"),
  UPLOAD_FAIL("1014", "Failed to upload image"),
  ROLE_NOT_FOUND("1015", "Role not found");

  private final String code;
  private final String message;
}
