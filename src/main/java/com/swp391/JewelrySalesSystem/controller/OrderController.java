package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.OrderRequest;
import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
  private final OrderFacade orderFacade;

  @PostMapping("/order-product")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Create new order",
      tags = {"Sales Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_CASHIER_STAFF') and hasRole('ROLE_MANAGER')")
  public BaseResponse<Void> createOrder(@RequestBody OrderRequest request) {
    return this.orderFacade.orderProduct(request);
  }

  @PostMapping("/pre-order-product")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Submit pre order",
      tags = {"Sales Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> preOrder(@RequestBody PreOrderRequest request) {
    return this.orderFacade.preOrderProduct(request);
  }

  @GetMapping("/get-pre-order-product/{key}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get pre order by Key",
      tags = {"Sales Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PreOrderRequest> getPreOrder(@PathVariable("key") String key) {
    return this.orderFacade.getPreOrderProduct(key);
  }

  @PutMapping("/update-pre-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update pre order",
      tags = {"Sales Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> updatePreOrder(
      @RequestParam String key, @RequestBody PreOrderRequest request) {
    return this.orderFacade.updatePreOrderProduct(key, request);
  }

  @GetMapping("/get-key-pre-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get key pre order",
      tags = {"Sales Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<String>> updatePreOrder() {
    return this.orderFacade.getAllKeyPreOrder();
  }

  @GetMapping("/get-all-history-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all history order",
      tags = {"Sales Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<OrderHistoryResponse>> getAllHistoryOrder() {
    return this.orderFacade.getAllHistoryOrder();
  }
}
