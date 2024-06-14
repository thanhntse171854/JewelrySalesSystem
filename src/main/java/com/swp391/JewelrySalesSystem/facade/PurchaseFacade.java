package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.GemFilterRequest;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.PurchaseOrderRequest;
import com.swp391.JewelrySalesSystem.request.ValidateOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.GemPriceResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import java.util.List;

public interface PurchaseFacade {
  BaseResponse<OrderHistoryResponse> validateOrder(ValidateOrderRequest request);

  BaseResponse<Void> createPurchase(PurchaseOrderRequest request);

  BaseResponse<List<GemPriceResponse>> getGemByFilter(GemFilterRequest request);

  BaseResponse<Void> payment(PaymentRequest request);
}
