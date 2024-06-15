package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.PayemntType;
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
  @JoinColumn(name = "order_id", unique = true)
  private Orders order;

  @OneToOne
  @JoinColumn(name = "purchase_order_id", unique = true)
  private PurchaseOrder purchaseOrder;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @Column(name = "payment_type")
  @Enumerated(EnumType.STRING)
  private PayemntType payemntType;

  @Column(name = "total_price")
  private Float totalPrice;

  public void updateStatus(PaymentStatus paymentStatus) {
    this.status = paymentStatus;
  }
}
