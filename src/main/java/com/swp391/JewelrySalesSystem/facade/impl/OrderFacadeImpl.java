package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.dto.OrderDetailDTO;
import com.swp391.JewelrySalesSystem.dto.OrderDetailProductDTO;
import com.swp391.JewelrySalesSystem.entity.*;
import com.swp391.JewelrySalesSystem.enums.*;
import com.swp391.JewelrySalesSystem.exception.OrderExcetpion;
import com.swp391.JewelrySalesSystem.exception.PaymentException;
import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.OrderCriteria;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.UpsertOrderRequest;
import com.swp391.JewelrySalesSystem.request.VnPayCallbackParamRequest;
import com.swp391.JewelrySalesSystem.response.*;
import com.swp391.JewelrySalesSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
public class OrderFacadeImpl implements OrderFacade {

  private final ProductService productService;
  private final UserService userService;
  private final CustomerService customerService;
  private final OrderService orderService;
  private final PaymentService paymentService;
  private final SpringTemplateEngine springTemplateEngine;
  private final DataMapperService dataMapperService;
  private final DocumentService documentService;
  private final VnPayService vnPayService;

  @Override
  public BaseResponse<Void> orderProduct(UpsertOrderRequest orderRequest) {
    User user = userService.findById(orderRequest.getStaffId());
    Customer customer = customerService.findByPhone(orderRequest.getPhone());
    Orders orders = orderService.findOrderByCode(orderRequest.getOrderCode());
    orders.updateDiscount(orderRequest.getDiscount() != null ? orderRequest.getDiscount() : 0F);
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

    orders.updateInfor(customer, user, orderRequest.getTotalPrice(), orderRequest.getDiscount());

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
  public BaseResponse<List<OrderResponse>> getOrderProductBySeller(OrderCriteria orderCriteria) {
    var orders = orderService.findOrderBySeller(orderCriteria.getId());
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
              .discount(order.getDiscount())
              .createAt(order.getCreatedAt())
              .build());
    }
    return BaseResponse.build(list, true);
  }

  @Override
  public BaseResponse<String> payment(
      PaymentRequest request, HttpServletRequest httpServletRequest) {
    String paymentUrl = null;

    Orders orders = orderService.findOrderById(request.getOrderId());
    Customer customer = customerService.findByPhone(request.getCustomerPhone());
    Payment payment = paymentService.findByOrderId(request.getOrderId());

    customer.updateName(request.getName());
    customer.addAddress(request.getAddress());
    customer.addBirthDate(request.getDateOfBirth());

    if (request.getPaymentMethod().isCredit()) {
      Long amount = (long) (request.getAmount() * 100L);
      var vnpParams =
          vnPayService.buildVnPayParams(amount, request.getOrderCode(), httpServletRequest);
      List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
      Collections.sort(fieldNames);
      StringBuilder hashData = new StringBuilder();
      StringBuilder query = new StringBuilder();
      Iterator itr = fieldNames.iterator();

      while (itr.hasNext()) {
        String fieldName = (String) itr.next();
        String fieldValue = vnpParams.get(fieldName);
        if ((fieldValue != null) && (!fieldValue.isEmpty())) {

          hashData.append(fieldName);
          hashData.append('=');
          hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

          query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
          query.append('=');
          query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
          if (itr.hasNext()) {
            query.append('&');
            hashData.append('&');
          }
        }
      }
      String queryUrl = query.toString();
      String vnpSecureHash = vnPayService.hmacSHA512(hashData.toString());
      queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
      paymentUrl = vnPayService.getPaymentUrl(queryUrl);

      if (payment == null) {
        payment =
            Payment.builder()
                .paymentCode("PM" + generatePaymentCode())
                .order(orders)
                .status(PaymentStatus.PENDING)
                .payemntType(PayemntType.ORDER_SELL)
                .totalPrice(request.getAmount())
                .build();
      } else {
        payment.updateStatus(PaymentStatus.SUCCESS);
      }
      paymentService.savePayment(payment);

      return BaseResponse.build(paymentUrl, true);
    }

    Float totalAmount = request.getAmount() + customer.getTotalAmountPurchased();
    customer.updateDiscount(totalAmount);
    customer.updateTotalAmountPurchase(totalAmount);

    orders.updatePaymentMethod(request.getPaymentMethod());
    orders.updateAmount(request.getAmount());
    orderService.save(orders);

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
      for (var order : orders.getOrderDetails()) {
        Product product = productService.findByProductIdAndActive(order.getProduct().getId());
        productService.deactivateProduct(product.getId());
      }

      return BaseResponse.build("SUCCESS", true);
    } else {
      if (payment != null) {
        payment.updateStatus(PaymentStatus.PENDING);
        paymentService.savePayment(payment);
      }
      throw new PaymentException(ErrorCode.PAYMENT_FAIL);
    }
  }

  @Override
  public BaseResponse<Void> paymentCallBack(VnPayCallbackParamRequest request) {
    Orders orders = orderService.findByOrderCode(request.getVnp_TxnRef());
    for (var order : orders.getOrderDetails()) {
      Product product = productService.findByProductIdAndActive(order.getProduct().getId());
      productService.deactivateProduct(product.getId());
    }
    Payment payment = paymentService.findByOrderId(orders.getId());
    if (payment == null) throw new RuntimeException();

    payment.updateStatus(PaymentStatus.SUCCESS);
    paymentService.savePayment(payment);

    Customer customer = orders.getCustomer();

    Float totalAmount =
        Long.parseLong(request.getVnp_Amount()) + customer.getTotalAmountPurchased();
    customer.updateDiscount(totalAmount);
    customer.updateTotalAmountPurchase(totalAmount);

    orders.updatePaymentMethod(PaymentMethod.CREDIT);
    orderService.save(orders);
    customerService.save(customer);
    return BaseResponse.ok();
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
      orderService.save(orders);
      return BaseResponse.ok();
    }
    orders.updateDelivery(DeliveryStatus.FAIL);
    orderService.save(orders);
    throw new OrderExcetpion(ErrorCode.ORDER_ASSIGNED);
  }

  @Override
  public ResponseEntity<byte[]> generateDocument(String orderCode) {
    Orders orders = orderService.findByOrderCode(orderCode);

    List<OrderDetailProductDTO> orderDetailProductDTOS = new ArrayList<>();
    for (var order : orders.getOrderDetails()) {
      float quantity = 1f;
      boolean isNotJewelryOrDiamond =
          !order.getProduct().getCategory().getCategoryType().equals(CategoryType.JEWELRY)
              && !order.getProduct().getCategory().getCategoryType().equals(CategoryType.DIAMOND);
      if (isNotJewelryOrDiamond) {
        quantity = order.getProduct().getProductMaterials().get(0).getWeight();
      }

      orderDetailProductDTOS.add(
          OrderDetailProductDTO.builder()
              .name(order.getProduct().getProductName())
              .code(order.getProduct().getProductCode())
              .categoryType(order.getProduct().getCategory().getCategoryType())
              .size(order.getProduct().getSize())
              .price(String.format("%.0f", order.getPrice()))
              .quantity(1f)
              .build());
    }

    Long createdAtMillis = orders.getCreatedAt();
    LocalDateTime createdAt =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAtMillis), ZoneId.systemDefault());

    Long warrantyAtMillis = orders.getCreatedAt() + 15811200000L;
    LocalDateTime warranty =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(warrantyAtMillis), ZoneId.systemDefault());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = createdAt.format(formatter);
    String warrantyTo = warranty.format(formatter);

    OrderDetailDTO orderDetailDTO =
        OrderDetailDTO.builder()
            .orderCode(orders.getOrderCode())
            .customerName(orders.getCustomer().getName())
            .customerPhone(orders.getCustomer().getPhone())
            .customerAddress(orders.getCustomer().getAddress())
            .discount(orders.getDiscount())
            .totalAmount(String.format("%.0f", orders.getTotalAmount()))
            .paymentMethod(orders.getPaymentMethod())
            .list(orderDetailProductDTOS)
            .createAt(formattedDate)
            .warrantyTo(warrantyTo)
            .build();

    String html = null;
    Context context = dataMapperService.setData(orderDetailDTO);
    html = springTemplateEngine.process("OrderBill.html", context);
    byte[] pdfFile = documentService.htmlToPdf(html);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);

    String filename = orderCode + ".pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    return new ResponseEntity<>(pdfFile, headers, HttpStatus.OK);
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
            .discount(order.getDiscount())
            .totalPrice(order.getTotalAmount())
            .build(),
        true);
  }

  private String generatePaymentCode() {
    Random random = new Random();
    int otp = random.nextInt(9999);
    return String.format("%06d", otp);
  }
}
