package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.PurchaseFacade;
import com.swp391.JewelrySalesSystem.request.PurchaseOrderRequest;
import com.swp391.JewelrySalesSystem.request.ValidateOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

  private final PurchaseFacade purchaseFacade;

  @PostMapping("/validate-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Validate order",
      tags = {"Purchase Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<OrderHistoryResponse> validateOrder(
      @RequestBody ValidateOrderRequest request) {
    return this.purchaseFacade.validateOrder(request);
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Create purchase order",
      tags = {"Purchase Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> createPurchase(@RequestBody @Nullable PurchaseOrderRequest request) {
    return this.purchaseFacade.createPurchase(request);
  }
}
