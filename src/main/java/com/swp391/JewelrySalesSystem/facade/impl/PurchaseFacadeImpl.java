package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Orders;
import com.swp391.JewelrySalesSystem.entity.PurchaseOrder;
import com.swp391.JewelrySalesSystem.entity.PurchaseOrderDetail;
import com.swp391.JewelrySalesSystem.entity.User;
import com.swp391.JewelrySalesSystem.facade.PurchaseFacade;
import com.swp391.JewelrySalesSystem.request.PurchaseOrderRequest;
import com.swp391.JewelrySalesSystem.request.ValidateOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import com.swp391.JewelrySalesSystem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseFacadeImpl implements PurchaseFacade {
  private final OrderService orderService;
  private final UserService userService;
  private final MaterialService materialService;
  private final ProductService productService;
  private final PurchaseService purchaseService;

  @Override
  public BaseResponse<OrderHistoryResponse> validateOrder(ValidateOrderRequest request) {
    Orders orders = orderService.findOrderByPhoneAndId(request.getOrderId(), request.getPhone());
    if (orders == null) {
      return BaseResponse.fail();
    }
    return BaseResponse.build(
        OrderHistoryResponse.builder()
            .orderId(orders.getId())
            .salesStaffName(orders.getUser().getName())
            .dateOrder(orders.getCreatedAt())
            .totalPrice(orders.getTotalAmount())
            .deliveryStatus(orders.getDeliveryStatus())
            .paymentMethod(orders.getPaymentMethod())
            .build(),
        true);
  }

  @Override
  public BaseResponse<Void> createPurchase(PurchaseOrderRequest request) {
    User user = userService.findById(request.getStaffId());
    var purchaseOrder =
        PurchaseOrder.builder()
            .user(user)
            .customerName(request.getCustomerName())
            .phone(request.getPhone())
            .isProductStore(request.isProductStore())
            .totalPrice(request.getTotalPrice())
            .build();

    for (var purchase : request.getList()) {
      purchaseOrder
          .getList()
          .add(
              PurchaseOrderDetail.builder()
                  .productId(
                      (purchase.getProductId() != null)
                          ? productService.findByProductIdAndActive(purchase.getProductId()).getId()
                          : null)
                  .name((purchase.getName() != null) ? purchase.getName() : null)
                  .carat((purchase.getCarat() != null) ? purchase.getCarat() : null)
                  .price((purchase.getPrice() != null) ? purchase.getPrice() : null)
                  .cut((purchase.getCut() != null) ? purchase.getCut() : null)
                  .color((purchase.getColor() != null) ? purchase.getColor() : null)
                  .clarity((purchase.getClarity() != null) ? purchase.getClarity() : null)
                  .origin((purchase.getOrigin() != null) ? purchase.getOrigin() : null)
                  .material(
                      (purchase.getMaterialId() != null)
                          ? materialService.findById(purchase.getMaterialId()).orElse(null)
                          : null)
                  .weight((purchase.getWeight() != null) ? purchase.getWeight() : null)
                  .purchaseOrder(purchaseOrder)
                  .build());
    }

    purchaseService.save(purchaseOrder);
    return BaseResponse.ok();
  }
}
