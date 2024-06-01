package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.PaymentStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Payment extends BaseEntity implements Serializable {
  @Column(name = "payment_code", nullable = false, length = 100)
  private String paymentCode;

  @OneToOne
  @JoinColumn(name = "order_id", nullable = false, unique = true)
  private Orders order;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @Column(name = "total_price")
  private Float totalPrice;
}
