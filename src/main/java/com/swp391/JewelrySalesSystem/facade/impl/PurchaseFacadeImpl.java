package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.*;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.enums.PayemntType;
import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import com.swp391.JewelrySalesSystem.enums.PaymentStatus;
import com.swp391.JewelrySalesSystem.exception.OrderExcetpion;
import com.swp391.JewelrySalesSystem.exception.PaymentException;
import com.swp391.JewelrySalesSystem.facade.PurchaseFacade;
import com.swp391.JewelrySalesSystem.request.GemFilterRequest;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.PurchaseOrderRequest;
import com.swp391.JewelrySalesSystem.request.ValidateOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.GemPriceResponse;
import com.swp391.JewelrySalesSystem.response.OrderHistoryResponse;
import com.swp391.JewelrySalesSystem.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
  private final PriceService priceService;
  private final CustomerService customerService;
  private final PaymentService paymentService;

  @Override
  public BaseResponse<OrderHistoryResponse> validateOrder(ValidateOrderRequest request) {
    Orders orders =
        orderService.findOrderByPhoneAndCode(request.getOrderCode(), request.getPhone());
    if (orders == null) {
      throw new OrderExcetpion(ErrorCode.ORDER_NOT_FOUND);
    }
    return BaseResponse.build(
        OrderHistoryResponse.builder()
            .orderId(orders.getId())
            .orderCode(orders.getOrderCode())
            .salesStaffName(orders.getUser().getName())
            .dateOrder(orders.getCreatedAt())
            .totalPrice(orders.getTotalAmount())
            .deliveryStatus(orders.getDeliveryStatus())
            .paymentMethod(orders.getPaymentMethod())
            .customerPhone(orders.getCustomer().getPhone())
            .build(),
        true);
  }

  @Override
  public BaseResponse<Void> createPurchase(PurchaseOrderRequest request) {
    User user = userService.findById(request.getStaffId());
    Customer customer = customerService.findByPhone(request.getPhone());
    if (customer == null) {
      customer =
          Customer.builder().name(request.getCustomerName()).phone(request.getPhone()).build();
      customerService.save(customer);
    }

    var purchaseOrder =
        PurchaseOrder.builder()
            .purchaseOrderCode(request.getPurchaseOrderCode())
            .user(user)
            .customer(customer)
            .isProductStore(request.isProductStore())
            .totalPrice(request.getTotalPrice())
            .paymentMethod(PaymentMethod.NONE)
            .build();

    for (var purchase : request.getList()) {
      purchaseOrder
          .getList()
          .add(
              PurchaseOrderDetail.builder()
                  .productId(
                      (purchase.getProductCode() != null)
                          ? productService.findByProductCode(purchase.getProductCode()).getId()
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
                          ? materialService.findById(purchase.getMaterialId())
                          : null)
                  .weight((purchase.getWeight() != null) ? purchase.getWeight() : null)
                  .purchaseOrder(purchaseOrder)
                  .build());
    }

    purchaseService.save(purchaseOrder);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<List<GemPriceResponse>> getGemByFilter(GemFilterRequest request) {
    List<Gem> gems = priceService.filterGemPriceLists(request);
    List<GemPriceList> gemPriceList = new ArrayList<>();
    for (var gem : gems) {
      gemPriceList.add(priceService.findGemPriceList(gem));
    }
    List<GemPriceResponse> gemPriceResponseList =
        gemPriceList.stream()
            .map(
                gemTmp ->
                    GemPriceResponse.builder()
                        .gemId(gemTmp.getId())
                        .origin(gemTmp.getOrigin())
                        .color(gemTmp.getColor())
                        .clarity(gemTmp.getClarity())
                        .cut(gemTmp.getCut())
                        .carat(gemTmp.getCarat())
                        .gemBuyPrice(gemTmp.getBuyPrice())
                        .gemSellPrice(gemTmp.getSellPrice())
                        .effectDate(gemTmp.getEffectDate())
                        .build())
            .toList();
    return BaseResponse.build(gemPriceResponseList, true);
  }

  @Override
  public BaseResponse<Void> payment(PaymentRequest request) {
    PurchaseOrder purchaseOrder = purchaseService.findByPurchaseOrderCode(request.getOrderCode());
    if (purchaseOrder == null) {
      throw new OrderExcetpion(ErrorCode.ORDER_NOT_FOUND);
    }
    Customer customer = customerService.findByPhone(request.getCustomerPhone());

    if (request.getName() != null) customer.updateName(request.getName());

    if (request.getAddress() != null) customer.addAddress(request.getAddress());

    if (request.getAddress() != null) customer.addBirthDate(request.getDateOfBirth());

    customerService.save(customer);

    purchaseOrder.updatePaymentMethod(request.getPaymentMethod());
    purchaseService.save(purchaseOrder);

    Payment payment = paymentService.findByOrderId(request.getOrderId());

    boolean isValidAmount = request.getAmount().equals(purchaseOrder.getTotalPrice());
    if (isValidAmount) {
      if (payment == null) {
        payment =
            Payment.builder()
                .paymentCode("PM" + generatePaymentCode())
                .purchaseOrder(purchaseOrder)
                .status(PaymentStatus.SUCCESS)
                .payemntType(PayemntType.ORDER_PURCHASE)
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

  private String generatePaymentCode() {
    Random random = new Random();
    int otp = random.nextInt(9999);
    return String.format("%06d", otp);
  }
}
