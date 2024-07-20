package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.OrderCriteria;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.UpsertOrderRequest;
import com.swp391.JewelrySalesSystem.request.VnPayCallbackParamRequest;
import com.swp391.JewelrySalesSystem.response.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface OrderFacade {
  BaseResponse<Void> orderProduct(UpsertOrderRequest request);

  BaseResponse<List<OrderResponse>> getOrderProductBySeller(OrderCriteria orderCriteria);

  BaseResponse<Void> deleteOderByKey(String code);

  BaseResponse<String> payment(PaymentRequest request, HttpServletRequest httpServletRequest);

  BaseResponse<Void> paymentCallBack(VnPayCallbackParamRequest request);

  BaseResponse<OrderDetailResponse> getOrderDetail(String code);

  BaseResponse<List<OrderHistoryResponse>> getAllHistoryOrder();

  BaseResponse<Void> updateStatusDelivery(String code);

  ResponseEntity<byte[]> generateDocument(String orderCode);
}
