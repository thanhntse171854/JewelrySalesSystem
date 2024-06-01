package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentFacade {
  ResponseEntity<String> createPayment(PaymentRequest request);
}
