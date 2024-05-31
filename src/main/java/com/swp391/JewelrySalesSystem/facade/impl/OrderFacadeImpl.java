package com.swp391.JewelrySalesSystem.facade.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.JewelrySalesSystem.entity.*;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.enums.PaymentStatus;
import com.swp391.JewelrySalesSystem.exception.SizeException;
import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.OrderRequest;
import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.service.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

  private final ProductService productService;
  private final UserService userService;
  private final CustomerService customerService;
  private final ProductSizeService productSizeService;
  private final OrderService orderService;
  private final CacheService cacheService;
  private final PaymentService paymentService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public BaseResponse<Void> orderProduct(OrderRequest orderRequest) {
    User user = userService.findById(orderRequest.getStaffId());
    Customer customer = customerService.findByPhone(orderRequest.getPhone());

    if (customer == null) {
      customer =
          Customer.builder()
              .name(orderRequest.getName())
              .phone(orderRequest.getPhone())
              .totalAmountPurchased(0L)
              .percentDiscount(0f)
              .build();
      customerService.save(customer);
    }

    var newOrder =
        Orders.builder()
            .customer(customer)
            .user(user)
            .totalAmount(orderRequest.getTotalPrice())
            .paymentStatus(PaymentStatus.PENDING)
            .paymentMethod(orderRequest.getPaymentMethod())
            .build();

    for (var order : orderRequest.getOrderList()) {
      Product product = productService.findByProductIdAndActive(order.getProductId());
      SizeProduct size = null;
      boolean isJewelryCategory =
          product.getCategory().getCategoryType().equals(CategoryType.JEWELRY);
      if (isJewelryCategory) {
        size = productSizeService.findByProductIdAndSizeId(order.getProductId(), order.getSizeId());
        if (size == null || size.getQuantity() < order.getQuantity()) {
          throw new SizeException(ErrorCode.SIZE_NOT_FOUND);
        }
      }
      newOrder
          .getOrderDetails()
          .add(
              OrderDetail.builder()
                  .order(newOrder)
                  .product(product)
                  .size(size != null ? size.getSize() : null)
                  .quantity(order.getQuantity())
                  .build());
    }

    if (orderRequest.getPaymentMethod().isCash()) {
      Long totalAmount = (long) (orderRequest.getTotalPrice() + customer.getTotalAmountPurchased());
      newOrder.updatePaymentStatus(PaymentStatus.SUCCESS);
      customer.updateDiscount(totalAmount);
      customer.updateTotalAmountPurchase(totalAmount);
      customerService.save(customer);
    }

    orderService.save(newOrder);
    paymentService.savePayment(
        Payment.builder()
            .paymentCode("ABC")
            .order(newOrder)
            .status(PaymentStatus.SUCCESS)
            .totalPrice(orderRequest.getTotalPrice())
            .build());
    cacheService.delete(orderRequest.getKeyProOrder());
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<Void> preOrderProduct(PreOrderRequest request) {
    String cacheKey = String.format("%s-%s", request.getStaffId(), request.getCustomer());
    cacheService.store(cacheKey, request, 30, TimeUnit.MINUTES);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<PreOrderRequest> getPreOrderProduct(String key) {
    PreOrderRequest preOrderRequest = null;
    Object value = cacheService.retrieve(key);
    if (value instanceof LinkedHashMap) {
      preOrderRequest = objectMapper.convertValue(value, PreOrderRequest.class);
    } else if (value instanceof PreOrderRequest) {
      preOrderRequest = (PreOrderRequest) value;
    }
    return BaseResponse.build(preOrderRequest, true);
  }

  @Override
  public BaseResponse<Void> updatePreOrderProduct(String key, PreOrderRequest newUpdate) {
    if (cacheService.hasKey(key)) {
      cacheService.store(key, newUpdate, 30, TimeUnit.MINUTES);
      return BaseResponse.ok();
    } else {
      return BaseResponse.fail();
    }
  }

  @Override
  public BaseResponse<List<String>> getAllKeyPreOrder() {
    List<String> keys = cacheService.getAllKeys();
    return BaseResponse.build(keys, true);
  }
}
