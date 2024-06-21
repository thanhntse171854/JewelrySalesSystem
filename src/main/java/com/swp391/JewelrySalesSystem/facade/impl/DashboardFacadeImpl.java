package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Orders;
import com.swp391.JewelrySalesSystem.entity.User;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.DashboardException;
import com.swp391.JewelrySalesSystem.facade.DashboardFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CategoryTypeMostOrderResponse;
import com.swp391.JewelrySalesSystem.response.StaffCreateMostOrderResponse;
import com.swp391.JewelrySalesSystem.service.OrderDetailService;
import com.swp391.JewelrySalesSystem.service.OrderService;
import com.swp391.JewelrySalesSystem.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardFacadeImpl implements DashboardFacade {

  private final OrderService orderService;
  private final OrderDetailService orderDetailService;
  private final UserService userService;

  @Override
  public BaseResponse<Float> getTodaySalesAmount() {
    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
    LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

    long startDate = startOfDay.toInstant(ZoneOffset.UTC).toEpochMilli();
    long endDate = endOfDay.toInstant(ZoneOffset.UTC).toEpochMilli();

    Float totalAmount = totalOrder(startDate, endDate);
    return BaseResponse.build(totalAmount, true);
  }

  @Override
  public BaseResponse<Float> getYesterdaySalesAmount() {
    LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();
    LocalDateTime endOfYesterday = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);

    long startDate = startOfYesterday.toInstant(ZoneOffset.UTC).toEpochMilli();
    long endDate = endOfYesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

    Float totalAmount = totalOrder(startDate, endDate);

    return BaseResponse.build(totalAmount, true);
  }

  @Override
  public BaseResponse<Float> getThisMonthSalesAmount() {
    LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
    LocalDateTime endOfMonth =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(LocalTime.MAX);

    long startDate = startOfMonth.toInstant(ZoneOffset.UTC).toEpochMilli();
    long endDate = endOfMonth.toInstant(ZoneOffset.UTC).toEpochMilli();

    Float totalAmount = totalOrder(startDate, endDate);

    return BaseResponse.build(totalAmount, true);
  }

  @Override
  public BaseResponse<Float> getLastMonthSalesAmount() {
    LocalDateTime startOfLastMonth =
        LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
    LocalDateTime endOfLastMonth =
        LocalDate.now()
            .minusMonths(1)
            .withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth())
            .atTime(LocalTime.MAX);

    long startDate = startOfLastMonth.toInstant(ZoneOffset.UTC).toEpochMilli();
    long endDate = endOfLastMonth.toInstant(ZoneOffset.UTC).toEpochMilli();

    Float totalAmount = totalOrder(startDate, endDate);

    return BaseResponse.build(totalAmount, true);
  }

  @Override
  public BaseResponse<StaffCreateMostOrderResponse> getStaffCreateMostOrderThisMonth() {
    LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
    LocalDateTime endOfMonth =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(LocalTime.MAX);

    long startDate = startOfMonth.toInstant(ZoneOffset.UTC).toEpochMilli();
    long endDate = endOfMonth.toInstant(ZoneOffset.UTC).toEpochMilli();

    List<Object[]> objects = orderService.findStaffAndTotalOrderByStaff(startDate, endDate);
    if (objects == null) {
      throw new DashboardException(ErrorCode.STAFF_NOT_FOUND);
    }

    Object[] row = objects.get(0);
    Long staffId = ((Number) row[0]).longValue();
    int orderCount = ((Number) row[1]).intValue();

    User user = userService.findById(staffId);

    return BaseResponse.build(
        StaffCreateMostOrderResponse.builder()
            .staffName(user.getName())
            .totalOrder(orderCount)
            .build(),
        true);
  }

  @Override
  public BaseResponse<List<CategoryTypeMostOrderResponse>> getCategoryTypeMostOrder() {
    List<Object[]> results = orderDetailService.findCategoryTypeMostOrder();
    List<CategoryTypeMostOrderResponse> categoryTypeSales = new ArrayList<>();

    int totalOrders = results.stream().mapToInt(result -> ((Number) result[1]).intValue()).sum();

    for (Object[] result : results) {
      String categoryType = (String) result[0];
      int totalOrder = ((Number) result[1]).intValue();
      double percentage = (double) totalOrder / totalOrders * 100;
      categoryTypeSales.add(
          new CategoryTypeMostOrderResponse(categoryType, totalOrder, percentage));
    }

    return BaseResponse.build(categoryTypeSales, true);
  }

  private Float totalOrder(Long startDate, Long endDate) {
    List<Orders> orders = orderService.findOrderByDate(startDate, endDate);
    Float totalAmount = 0F;
    for (var order : orders) {
      if (order.getDeliveryStatus().isSuccess()) totalAmount += order.getTotalAmount();
    }
    return totalAmount;
  }
}
