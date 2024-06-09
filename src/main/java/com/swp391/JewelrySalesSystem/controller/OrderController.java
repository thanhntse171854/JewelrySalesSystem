package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.OrderRequest;
import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderDetailResponse;
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
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_CASHIER_STAFF') and hasRole('ROLE_MANAGER')")
  public BaseResponse<Void> createOrder(@RequestBody OrderRequest request) {
    return this.orderFacade.orderProduct(request);
  }

  @PostMapping("/pre-order-products")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Submit pre order",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> preOrder(@RequestBody PreOrderRequest request) {
    return this.orderFacade.preOrderProduct(request);
  }

  @GetMapping("/pre-order-product/{key}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get pre order by Key",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PreOrderRequest> getPreOrder(@PathVariable("key") String key) {
    return this.orderFacade.getPreOrderProduct(key);
  }

  @PutMapping("/pre-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update pre order",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> updatePreOrder(
      @RequestParam String key, @RequestBody PreOrderRequest request) {
    return this.orderFacade.updatePreOrderProduct(key, request);
  }

  @GetMapping("/key-pre-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get key pre order",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<String>> getAllKeyPreOrder() {
    return this.orderFacade.getAllKeyPreOrder();
  }

  @GetMapping("/pre-order/{staffId}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get key pre order",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<String>> getKeyPreOrderOfStaffId(@PathVariable("staffId") Long id) {
    return this.orderFacade.getKeyPreOrderOfStaffId(id);
  }

  @DeleteMapping("/pre-order/{key}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get key pre order of staff staff",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> deletePreOrderByKey(@PathVariable("key") String key) {

    return this.orderFacade.deletePreOderByKey(key);
  }

  @GetMapping("/all-history-orders")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all history order",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<OrderHistoryResponse>> getAllHistoryOrder() {
    return this.orderFacade.getAllHistoryOrder();
  }

  @PostMapping("/delivery/{orderId}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update delivery order",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> updateStatusDelivery(@PathVariable("orderId") Long id) {
    return this.orderFacade.updateStatusDelivery(id);
  }

  @PostMapping("/order-detail/{orderCode}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get order detail by orderCode",
      tags = {"Sell Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<OrderDetailResponse> updateStatusDelivery(
      @PathVariable("orderCode") String code) {
    return this.orderFacade.getOrderDetail(code);
  }
}
