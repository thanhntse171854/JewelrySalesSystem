package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.OrderRequest;
import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;

import java.util.List;

public interface OrderFacade {
  BaseResponse<Void> orderProduct(OrderRequest request);

  BaseResponse<Void> preOrderProduct(PreOrderRequest request);

  BaseResponse<PreOrderRequest> getPreOrderProduct(String key);

  BaseResponse<Void> updatePreOrderProduct(String key, PreOrderRequest request);

  BaseResponse<List<String>>  getAllKeyPreOrder();
}
