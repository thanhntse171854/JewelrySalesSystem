package com.swp391.JewelrySalesSystem.exception;

import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EntityNotFoundException extends RuntimeException {
  private final String errorCode;
  private final String message;

  public EntityNotFoundException(ErrorCode errorCode) {
    super(errorCode.name());
    this.errorCode = errorCode.getCode();
    this.message = errorCode.getMessage();
  }
}
