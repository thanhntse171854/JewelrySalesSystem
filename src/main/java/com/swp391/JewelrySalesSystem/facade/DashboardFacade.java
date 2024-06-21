package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CategoryTypeMostOrderResponse;
import com.swp391.JewelrySalesSystem.response.StaffCreateMostOrderResponse;
import java.util.List;

public interface DashboardFacade {
  BaseResponse<Float> getTodaySalesAmount();

  BaseResponse<Float> getYesterdaySalesAmount();

  BaseResponse<Float> getThisMonthSalesAmount();

  BaseResponse<Float> getLastMonthSalesAmount();

  BaseResponse<StaffCreateMostOrderResponse> getStaffCreateMostOrderThisMonth();

  BaseResponse<List<CategoryTypeMostOrderResponse>> getCategoryTypeMostOrder();
}
