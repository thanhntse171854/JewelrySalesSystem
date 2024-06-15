package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.*;
import com.swp391.JewelrySalesSystem.enums.*;
import com.swp391.JewelrySalesSystem.exception.OrderExcetpion;
import com.swp391.JewelrySalesSystem.exception.PaymentException;
import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.UpsertOrderRequest;
import com.swp391.JewelrySalesSystem.response.*;
import com.swp391.JewelrySalesSystem.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

  private final ProductService productService;
  private final UserService userService;
  private final CustomerService customerService;
  private final OrderService orderService;
  private final PaymentService paymentService;

  @Override
  public BaseResponse<Void> orderProduct(UpsertOrderRequest orderRequest) {
    User user = userService.findById(orderRequest.getStaffId());
    Customer customer = customerService.findByPhone(orderRequest.getPhone());
    Orders orders = orderService.findOrderByCode(orderRequest.getOrderCode());

    if (customer == null) {
      customer =
          Customer.builder()
              .name(orderRequest.getName())
              .phone(orderRequest.getPhone())
              .totalAmountPurchased(0F)
              .percentDiscount(0F)
              .build();
      customerService.save(customer);
    }

    orders.updateInfor(customer, user, orderRequest.getTotalPrice());

    for (var order : orderRequest.getOrderList()) {
      Product product = productService.findByProductIdAndActive(order.getProductId());
      orders
          .getOrderDetails()
          .add(
              OrderDetail.builder().order(orders).product(product).price(order.getPrice()).build());
    }
    orders.upsertOrderCode(orderRequest.getOrderCode());
    orderService.save(orders);

    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<List<OrderResponse>> getOrderProductBySeller(Long id) {
    List<Orders> orders = orderService.findOrderBySeller(id);
    List<OrderResponse> list = new ArrayList<>();
    for (var order : orders) {
      list.add(
          OrderResponse.builder()
              .orderId(order.getId())
              .orderCode(order.getOrderCode())
              .name(order.getCustomer().getName())
              .phone(order.getCustomer().getPhone())
              .deliveryStatus(order.getDeliveryStatus())
              .paymentMethod(order.getPaymentMethod())
              .totalPrice(order.getTotalAmount())
              .build());
    }
    return BaseResponse.build(list, true);
  }

  @Override
  public BaseResponse<Void> payment(PaymentRequest request) {
    Orders orders = orderService.findOrderById(request.getOrderId());
    Customer customer = customerService.findByPhone(request.getCustomerPhone());
    Payment payment = paymentService.findByOrderId(request.getOrderId());

    customer.updateName(request.getName());
    customer.addAddress(request.getAddress());
    customer.addBirthDate(request.getDateOfBirth());

    if (request.getPaymentMethod().isCash()) {
      Float totalAmount = request.getAmount() + customer.getTotalAmountPurchased();
      customer.updateDiscount(totalAmount);
      customer.updateTotalAmountPurchase(totalAmount);

      orders.updatePaymentMethod(request.getPaymentMethod());
      orderService.save(orders);
    }

    customerService.save(customer);

    boolean isValidAmount = request.getAmount().equals(orders.getTotalAmount());
    if (isValidAmount) {
      if (payment == null) {
        payment =
            Payment.builder()
                .paymentCode("PM" + generatePaymentCode())
                .order(orders)
                .status(PaymentStatus.SUCCESS)
                .payemntType(PayemntType.ORDER_SELL)
                .totalPrice(request.getAmount())
                .build();
      } else {
        payment.updateStatus(PaymentStatus.SUCCESS);
      }
      paymentService.savePayment(payment);
      return BaseResponse.ok();
    } else {
      if (payment != null) {
        payment.updateStatus(PaymentStatus.PENDING);
        paymentService.savePayment(payment);
      }
      throw new PaymentException(ErrorCode.PAYMENT_FAIL);
    }
  }

  @Override
  public BaseResponse<Void> deleteOderByKey(String code) {
    Orders orders = orderService.findByOrderCode(code);
    if (orders.getDeliveryStatus().isPending() && orders.getPaymentMethod().isNone()) {
      orderService.delete(orders.getId());
      return BaseResponse.ok();
    }
    throw new OrderExcetpion(ErrorCode.ORDER_DELETE_FAIL);
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
              .customerPhone(listOrder.getCustomer().getPhone())
              .build());
    }
    return BaseResponse.build(orderHistoryResponses, true);
  }

  @Override
  public BaseResponse<Void> updateStatusDelivery(String code) {
    Orders orders = orderService.findByOrderCode(code);
    if (orders.getDeliveryStatus().isPending()) {
      orders.updateDelivery(DeliveryStatus.SUCCESS);
      for (var order : orders.getOrderDetails()) {
        Product product = productService.findByProductIdAndActive(order.getId());
        productService.deactivateProduct(product.getId());
      }
      orderService.save(orders);
      return BaseResponse.ok();
    }
    orders.updateDelivery(DeliveryStatus.FAIL);
    orderService.save(orders);
    throw new OrderExcetpion(ErrorCode.ORDER_ASSIGNED);
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
              .price(orderDetail.getPrice())
              .build());
    }

    return BaseResponse.build(
        OrderDetailResponse.builder()
            .orderId(order.getId())
            .orderCode(order.getOrderCode())
            .customerName(order.getCustomer().getName())
            .customerPhone(order.getCustomer().getPhone())
            .deliveryStatus(order.getDeliveryStatus())
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
