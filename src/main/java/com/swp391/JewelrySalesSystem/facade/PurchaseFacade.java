package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.PurchaseOrderRequest;
import com.swp391.JewelrySalesSystem.request.ValidateOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;

public interface PurchaseFacade {
  BaseResponse<OrderHistoryResponse> validateOrder(ValidateOrderRequest request);

  BaseResponse<Void> createPurchase(PurchaseOrderRequest request);
}
