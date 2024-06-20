package com.swp391.JewelrySalesSystem.interceptor;

import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.*;
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
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleEntityException(
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

  @ExceptionHandler(OrderExcetpion.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleOrderException(
      OrderExcetpion orderExcetpion) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(orderExcetpion.getErrorCode(), orderExcetpion.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PaymentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handlePaymentException(
      PaymentException paymentException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(paymentException.getErrorCode(), paymentException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MaterialException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleMaterialException(
      MaterialException materialException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(materialException.getErrorCode(), materialException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PriceListException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handlePriceException(
      PriceListException priceListException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(
                priceListException.getErrorCode(), priceListException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ProductException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleProductException(
      ProductException productException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(productException.getErrorCode(), productException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CategoryException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleProductException(
      CategoryException categoryException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(categoryException.getErrorCode(), categoryException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UploadException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleProductException(
      UploadException uploadException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(uploadException.getErrorCode(), uploadException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RoleException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleProductException(
      RoleException roleException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(roleException.getErrorCode(), roleException.getMessage()), false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ChangePasswordException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleProductException(
      ChangePasswordException changePasswordException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(
                changePasswordException.getErrorCode(), changePasswordException.getMessage()),
            false),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(GemException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<BaseResponse<ExceptionResponse>> handleProductException(
      GemException gemException) {
    return new ResponseEntity<>(
        BaseResponse.build(
            new ExceptionResponse(gemException.getErrorCode(), gemException.getMessage()), false),
        HttpStatus.BAD_REQUEST);
  }
}
