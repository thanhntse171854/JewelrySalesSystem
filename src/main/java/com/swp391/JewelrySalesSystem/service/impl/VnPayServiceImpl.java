package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VnPayServiceImpl implements VnPayService {

  @Value("${spring.vnpay.pay-url}")
  private String vnpPayUrl;

  @Value("${spring.vnpay.return-url}")
  private String vnpReturnUrl;

  @Value("${spring.vnpay.tmn-code}")
  private String vnpTmnCode;

  @Value("${spring.vnpay.hash-secret}")
  private String secretKey;

  @Value("${spring.vnpay.api-url}")
  private String vnpApiUrl;

  @Value("${spring.vnpay.version}")
  private String vnPayVersion;

  @Value("${spring.vnpay.command}")
  private String vnPayCommand;

  @Value("${spring.vnpay.order-type}")
  private String vnPayOrderType;

  @Override
  public String hmacSHA512(String data) {
    try {
      final Mac hmac512 = Mac.getInstance("HmacSHA512");
      byte[] hmacKeyBytes = secretKey.getBytes();
      final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
      hmac512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = hmac512.doFinal(dataBytes);
      StringBuilder sb = new StringBuilder(2 * result.length);
      for (byte b : result) {
        sb.append(String.format("%02x", b & 0xff));
      }
      return sb.toString();

    } catch (Exception ex) {
      return "";
    }
  }

  @Override
  public Map<String, String> buildVnPayParams(
      Long amount, String code, HttpServletRequest request) {
    var vnpTxnRef = code;

    Map<String, String> vnpParams = new HashMap<>();
    vnpParams.put("vnp_Version", vnPayVersion);
    vnpParams.put("vnp_Command", vnPayCommand);
    vnpParams.put("vnp_TmnCode", vnpTmnCode);
    vnpParams.put("vnp_Amount", String.valueOf(amount));
    vnpParams.put("vnp_CurrCode", "VND");
    vnpParams.put("vnp_BankCode", "NCB");
    vnpParams.put("vnp_TxnRef", vnpTxnRef);
    vnpParams.put("vnp_OrderInfo", "Payment for " + vnpTxnRef);
    vnpParams.put("vnp_Locale", "vn");
    vnpParams.put("vnp_ReturnUrl", vnpReturnUrl);
    vnpParams.put("vnp_OrderType", "billpayment");
    vnpParams.put("vnp_IpAddr", getClientIp(request));

    ZoneId hcmZone = ZoneId.of("Asia/Ho_Chi_Minh");
    ZonedDateTime now = ZonedDateTime.now(hcmZone);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String vnpCreateDate = now.format(formatter);
    vnpParams.put("vnp_CreateDate", vnpCreateDate);

    ZonedDateTime expireTime = now.plusMinutes(15);
    String vnpExpireDate = expireTime.format(formatter);
    vnpParams.put("vnp_ExpireDate", vnpExpireDate);

    return vnpParams;
  }

  public static String getClientIp(HttpServletRequest request) {
    String remoteAddr = "";

    if (request != null) {
      remoteAddr = request.getHeader("X-Forwarded-For");
      if (remoteAddr == null || "".equals(remoteAddr)) {
        remoteAddr = request.getHeader("Proxy-Client-IP");
      }
      if (remoteAddr == null || "".equals(remoteAddr)) {
        remoteAddr = request.getHeader("WL-Proxy-Client-IP");
      }
      if (remoteAddr == null || "".equals(remoteAddr)) {
        remoteAddr = request.getHeader("HTTP_CLIENT_IP");
      }
      if (remoteAddr == null || "".equals(remoteAddr)) {
        remoteAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (remoteAddr == null || "".equals(remoteAddr)) {
        remoteAddr = request.getRemoteAddr();
      }
    }

    return remoteAddr;
  }

  @Override
  public String getPaymentUrl(String queryUrl) {
    return String.format("%s?%s", vnpPayUrl, queryUrl);
  }
}
