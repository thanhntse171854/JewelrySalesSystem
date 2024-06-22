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
  ROLE_NOT_FOUND("1015", "Role not found"),
  CURRENT_PASSWORD_DOES_NOT_MATCH("1016", "Current password is invalid"),
  INVALID_CONFIRM_NEW_PASSWORD("1017", "New password and confirm new password does not match"),
  OLD_PASSWORD_EQUALS_NEW_PASSWORD(
      "1018", "Please choose a new password different from the old one"),
  PASSWORD_AND_NEW_PASSWORD_IS_NOT_EXIST("1019", "Please enter password and confirm password"),
  GEM_NOT_FOUND("1020", "Gem not found!"),
  STAFF_NOT_FOUND("1021", "No employee was found to create an order"),
  PHONE_AND_MAIL_EXIST("1022", "Both phone number and email already exist"),
  EMAIL_EXIST("1023", "Email already exists"),
  PHONE_EXIST("1024", "Phone number already exists"),
  INVALID_CONFIRM_PASSWORD("1025", "Password and confirm password does not match");

  private final String code;
  private final String message;
}
