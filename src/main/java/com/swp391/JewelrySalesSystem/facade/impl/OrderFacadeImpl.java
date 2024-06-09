package com.swp391.JewelrySalesSystem.facade.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.JewelrySalesSystem.entity.*;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.enums.PaymentStatus;
import com.swp391.JewelrySalesSystem.exception.OrderExcetpion;
import com.swp391.JewelrySalesSystem.exception.SizeException;
import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.OrderRequest;
import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.OrderDetailResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import com.swp391.JewelrySalesSystem.response.ProductOrderDetailResponse;
import com.swp391.JewelrySalesSystem.service.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
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
              .dateOfBirth(orderRequest.getDateOfBirth())
              .address(orderRequest.getAddress())
              .totalAmountPurchased(0L)
              .percentDiscount(0f)
              .build();
      customerService.save(customer);
    }

    var newOrder =
        Orders.builder()
            .orderCode(orderRequest.getOrderCode())
            .customer(customer)
            .user(user)
            .totalAmount(orderRequest.getTotalPrice())
            .deliveryStatus(DeliveryStatus.PENDING)
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
        size.updateQuantity(Long.valueOf(order.getQuantity()));
        productSizeService.save(size);
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
      customer.updateDiscount(totalAmount);
      customer.updateTotalAmountPurchase(totalAmount);
      customerService.save(customer);
    }

    orderService.save(newOrder);
    paymentService.savePayment(
        Payment.builder()
            .paymentCode("PM" + generatePaymentCode())
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

  @Override
  public BaseResponse<List<String>> getKeyPreOrderOfStaffId(Long id) {
    List<String> list = cacheService.getKeyByStaffId(String.valueOf(id));
    return BaseResponse.build(list, true);
  }

  @Override
  public BaseResponse<Void> deletePreOderByKey(String key) {
    cacheService.delete(key);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<List<OrderHistoryResponse>> getAllHistoryOrder() {
    List<Orders> list = orderService.getAllHistoryOrder();
    List<OrderHistoryResponse> orderHistoryResponses = new ArrayList<>();
    for (Orders listOrder : list) {
      orderHistoryResponses.add(
          OrderHistoryResponse.builder()
              .orderId(listOrder.getId())
              .orderCode(listOrder.getOrderCode())
              .salesStaffName(listOrder.getUser().getName())
              .totalPrice(listOrder.getTotalAmount())
              .deliveryStatus(listOrder.getDeliveryStatus())
              .paymentMethod(listOrder.getPaymentMethod())
              .dateOrder(listOrder.getCreatedAt())
              .build());
    }
    return BaseResponse.build(orderHistoryResponses, true);
  }

  @Override
  public BaseResponse<Void> updateStatusDelivery(Long id) {
    Orders orders =
        orderService
            .findOrderById(id)
            .orElseThrow(() -> new OrderExcetpion(ErrorCode.ORDER_NOT_FOUND));
    orders.updateDelivery(DeliveryStatus.SUCCESS);
    orderService.save(orders);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<OrderDetailResponse> getOrderDetail(String code) {
    var order = orderService.findByOrderCode(code);
    List<ProductOrderDetailResponse> orderDetailResponses = new ArrayList<>();
    for (var orderDetail : order.getOrderDetails()) {
      orderDetailResponses.add(
          ProductOrderDetailResponse.builder()
              .productId(orderDetail.getProduct().getId())
              .productCode(orderDetail.getProduct().getProductCode())
              .productName(orderDetail.getProduct().getProductName())
              .quantity(Long.valueOf(orderDetail.getQuantity()))
              .size(orderDetail.getSize() != null ? orderDetail.getSize().getSize() : null)
              .build());
    }

    return BaseResponse.build(
        OrderDetailResponse.builder()
            .customerName(order.getCustomer().getName())
            .customerPhone(order.getCustomer().getPhone())
            .paymentMethod(order.getPaymentMethod())
            .dateSell(order.getCreatedAt())
            .list(orderDetailResponses)
            .build(),
        true);
  }

  private String generatePaymentCode() {
    Random random = new Random();
    int otp = random.nextInt(9999);
    return String.format("%06d", otp);
  }
}
