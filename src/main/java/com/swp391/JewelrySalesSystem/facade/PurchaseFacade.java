package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.GemFilterRequest;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.PurchaseOrderRequest;
import com.swp391.JewelrySalesSystem.request.ValidateOrderRequest;
import com.swp391.JewelrySalesSystem.response.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface PurchaseFacade {
  BaseResponse<OrderHistoryResponse> validateOrder(ValidateOrderRequest request);

  BaseResponse<Void> createPurchase(PurchaseOrderRequest request);

  BaseResponse<List<GemPriceResponse>> getGemByFilter(GemFilterRequest request);

  BaseResponse<Void> payment(PaymentRequest request);

  ResponseEntity<byte[]> generateDocument(String orderCode);

  BaseResponse<List<PurchaseOrderResponse>> getAllPurchaseOrder();

  BaseResponse<Void> deleteOderByKey(String code);

  BaseResponse<PurchaseOrderDetailResponse> getDetailPurchase(String code);

  BaseResponse<List<PurchaseOrderResponse>> getAllPurchaseOrderBySeller(Long id);
}
