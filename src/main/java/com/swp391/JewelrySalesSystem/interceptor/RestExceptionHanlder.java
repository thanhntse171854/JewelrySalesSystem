package com.swp391.JewelrySalesSystem.interceptor;

import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.EntityNotFoundException;
import com.swp391.JewelrySalesSystem.exception.LoginException;
import com.swp391.JewelrySalesSystem.exception.SizeException;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHanlder extends ResponseEntityExceptionHandler {
  @ExceptionHandler(LoginException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleLoginException(
      LoginException loginException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(loginException.getErrorCode(), loginException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleOTPException(
      EntityNotFoundException entityNotFoundException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(
                entityNotFoundException.getErrorCode(), entityNotFoundException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<BaseResponse<ExceptionResponse>> handleBadCredentialsException(
      BadCredentialsException exception) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(
                ErrorCode.BAD_CREDENTIAL_LOGIN.getCode(),
                ErrorCode.BAD_CREDENTIAL_LOGIN.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SizeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleOTPException(
      SizeException sizeException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(sizeException.getErrorCode(), sizeException.getMessage()), false),
        HttpStatus.BAD_REQUEST);
  }
}
