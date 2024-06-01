package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.facade.PaymentFacade;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentFacadeImpl implements PaymentFacade {
  @Override
  public ResponseEntity<String> createPayment(PaymentRequest request) {
    return null;
  }
}
