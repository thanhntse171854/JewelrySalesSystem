package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.OrderRequest;
import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import java.util.List;

public interface OrderFacade {
  BaseResponse<Void> orderProduct(OrderRequest request);

  BaseResponse<Void> preOrderProduct(PreOrderRequest request);

  BaseResponse<PreOrderRequest> getPreOrderProduct(String key);

  BaseResponse<Void> updatePreOrderProduct(String key, PreOrderRequest request);

  BaseResponse<List<String>> getAllKeyPreOrder();

  BaseResponse<List<String>> getKeyPreOrderOfStaffId(Long id);

  BaseResponse<Void> deletePreOderByKey(String key);

  BaseResponse<List<OrderHistoryResponse>> getAllHistoryOrder();

  BaseResponse<Void> updateStatusDelivery(Long id);
}
