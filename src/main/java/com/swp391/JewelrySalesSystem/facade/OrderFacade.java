package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.UpsertOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderDetailResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import com.swp391.JewelrySalesSystem.response.OrderResponse;
import java.util.List;

public interface OrderFacade {
  BaseResponse<Void> orderProduct(UpsertOrderRequest request);

  BaseResponse<List<OrderResponse>> getOrderProductBySeller(Long staffId);

  BaseResponse<Void> deleteOderByKey(String code);

  BaseResponse<Void> payment(PaymentRequest request);

  BaseResponse<OrderDetailResponse> getOrderDetail(String code);

  BaseResponse<List<OrderHistoryResponse>> getAllHistoryOrder();

  BaseResponse<Void> updateStatusDelivery(String code);
}
