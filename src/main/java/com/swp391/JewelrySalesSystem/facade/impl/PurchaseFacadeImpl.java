package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.dto.PurchaseOrderDTO;
import com.swp391.JewelrySalesSystem.dto.PurchaseOrderDetailDTO;
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
import com.swp391.JewelrySalesSystem.response.*;
import com.swp391.JewelrySalesSystem.service.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

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
  private final SpringTemplateEngine springTemplateEngine;
  private final DataMapperService dataMapperService;
  private final DocumentService documentService;

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
          Customer.builder()
              .name(request.getCustomerName())
              .phone(request.getPhone())
              .address(request.getAddress())
              .dateOfBirth(request.getBirthday())
              .build();
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
      if (priceService.findGemPriceList(gem) != null) {
        gemPriceList.add(priceService.findGemPriceList(gem));
      }
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

  @Override
  public ResponseEntity<byte[]> generateDocument(String orderCode) {
    PurchaseOrder purchaseOrder = purchaseService.findByPurchaseOrderCode(orderCode);
    if (purchaseOrder == null) {
      throw new OrderExcetpion(ErrorCode.ORDER_NOT_FOUND);
    }
    List<PurchaseOrderDetailDTO> list = new ArrayList<>();
    for (var puchase : purchaseOrder.getList()) {
      if (puchase.getProductId() == null) {
        list.add(
            PurchaseOrderDetailDTO.builder()
                .productName(puchase.getName())
                .material(puchase.getMaterial() != null ? puchase.getMaterial().getName() : null)
                .origin(puchase.getOrigin())
                .color(puchase.getColor())
                .clarity(puchase.getClarity())
                .carat(puchase.getCarat())
                .weight(puchase.getWeight())
                .price(puchase.getPrice())
                .size("NONE")
                .build());
      } else {
        Product product = productService.findById(puchase.getProductId());
        list.add(
            PurchaseOrderDetailDTO.builder()
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .size(product.getSize().toString())
                .build());
      }
    }

    Long createdAtMillis = purchaseOrder.getCreatedAt();
    LocalDateTime createdAt =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAtMillis), ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = createdAt.format(formatter);

    PurchaseOrderDTO purchaseOrderDTO =
        PurchaseOrderDTO.builder()
            .orderCode(purchaseOrder.getPurchaseOrderCode())
            .customerName(purchaseOrder.getCustomer().getName())
            .customerPhone(purchaseOrder.getCustomer().getPhone())
            .customerAddress(purchaseOrder.getCustomer().getAddress())
            .totalPrice(purchaseOrder.getTotalPrice())
            .paymentMethod(purchaseOrder.getPaymentMethod())
            .list(list)
            .createAt(formattedDate)
            .build();

    String html = null;
    Context context = dataMapperService.setData(purchaseOrderDTO);
    html = springTemplateEngine.process("PurchaseOrder.html", context);
    byte[] pdfFile = documentService.htmlToPdf(html);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);

    String filename = orderCode + ".pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    return new ResponseEntity<>(pdfFile, headers, HttpStatus.OK);
  }

  @Override
  public BaseResponse<List<PurchaseOrderResponse>> getAllPurchaseOrder() {
    List<PurchaseOrder> list = purchaseService.findAll();
    List<PurchaseOrderResponse> purchaseOrderResponses = new ArrayList<>();
    for (var purchase : list) {
      purchaseOrderResponses.add(
          PurchaseOrderResponse.builder()
              .orderId(purchase.getId())
              .customerPhone(purchase.getCustomer().getPhone())
              .dateOrder(purchase.getCreatedAt())
              .customerName(purchase.getCustomer().getName())
              .salesStaffName(purchase.getUser().getName())
              .totalPrice(purchase.getTotalPrice())
              .orderCode(purchase.getPurchaseOrderCode())
              .build());
    }
    return BaseResponse.build(purchaseOrderResponses, true);
  }

  @Override
  public BaseResponse<Void> deleteOderByKey(String code) {
    PurchaseOrder purchaseOrder = purchaseService.findByPurchaseOrderCode(code);
    Payment payment = paymentService.findByPurchaseId(purchaseOrder.getId());
    if (purchaseOrder != null && payment != null && payment.getStatus().isSuccess()) {
      throw new OrderExcetpion(ErrorCode.ORDER_DELETE_FAIL);
    }
    purchaseService.deleteById(purchaseOrder.getId());
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<PurchaseOrderDetailResponse> getDetailPurchase(String code) {
    PurchaseOrder purchaseOrder = purchaseService.findByPurchaseOrderCode(code);
    if (purchaseOrder == null) throw new OrderExcetpion(ErrorCode.ORDER_NOT_FOUND);
    return BaseResponse.build(
        PurchaseOrderDetailResponse.builder()
            .orderId(purchaseOrder.getId())
            .customerAddress(purchaseOrder.getCustomer().getAddress())
            .customerPhone(purchaseOrder.getCustomer().getPhone())
            .customerName(purchaseOrder.getCustomer().getName())
            .birthday(purchaseOrder.getCustomer().getDateOfBirth())
            .orderCode(purchaseOrder.getPurchaseOrderCode())
            .totalAmount(purchaseOrder.getTotalPrice())
            .build(),
        true);
  }

  private String generatePaymentCode() {
    Random random = new Random();
    int otp = random.nextInt(9999);
    return String.format("%06d", otp);
  }
}
