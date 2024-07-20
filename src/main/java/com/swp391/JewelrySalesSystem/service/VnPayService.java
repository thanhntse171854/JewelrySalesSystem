package com.swp391.JewelrySalesSystem.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

public interface VnPayService {
  String hmacSHA512(String data);

  Map<String, String> buildVnPayParams(
      Long amount, String orderCode, HttpServletRequest httpServletRequest);

  String getPaymentUrl(String queryUrl);
}
