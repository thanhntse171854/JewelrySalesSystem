package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.DashboardFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CategoryTypeMostOrderResponse;
import com.swp391.JewelrySalesSystem.response.StaffCreateMostOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
  private final DashboardFacade dashboardFacade;

  @GetMapping("/today")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get total Amount today",
      tags = {"Dashboard APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Float> getSalesTotalAmountToday() {
    return this.dashboardFacade.getTodaySalesAmount();
  }

  @GetMapping("/yesterday")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get total Amount yesterday",
      tags = {"Dashboard APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Float> getSalesTotalAmountYesterDay() {
    return this.dashboardFacade.getYesterdaySalesAmount();
  }

  @GetMapping("/this-month")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get total Amount Yesterday",
      tags = {"Dashboard APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Float> getSalesTotalAmountThisMonth() {
    return this.dashboardFacade.getThisMonthSalesAmount();
  }

  @GetMapping("/last-month")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get total Amount last month",
      tags = {"Dashboard APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Float> getSalesTotalAmountLastMonth() {
    return this.dashboardFacade.getLastMonthSalesAmount();
  }

  @GetMapping("/staff-create-most-orders")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get staff with most order this month",
      tags = {"Dashboard APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<StaffCreateMostOrderResponse> getStaffCreateMostOrder() {
    return this.dashboardFacade.getStaffCreateMostOrderThisMonth();
  }

  @GetMapping("/category-type-most-orders")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get category type with most order this month",
      tags = {"Dashboard APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<CategoryTypeMostOrderResponse>> getCategoryTypeMostOrder() {
    return this.dashboardFacade.getCategoryTypeMostOrder();
  }
}
