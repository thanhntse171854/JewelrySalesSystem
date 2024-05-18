package com.swp391.JewelrySalesSystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  ACCOUNT_NOT_FOUND("1001", "Can not find account with username."),
  USER_IS_DEACTIVATED("1002", "Your account is deactivated"),
  BAD_CREDENTIAL_LOGIN("1201", "Invalid username or password");

  private final String code;
  private final String message;
}
