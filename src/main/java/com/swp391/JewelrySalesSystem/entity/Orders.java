package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Orders extends BaseEntity implements Serializable {

  @Column(name = "order_code", unique = true, nullable = false)
  private String orderCode;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "staff_id", referencedColumnName = "id")
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<OrderDetail> orderDetails = new ArrayList<>();

  @Column(name = "total_amount", nullable = false)
  private Float totalAmount;

  @Column(name = "delivery_stage_status")
  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @Column(name = "discount")
  @Builder.Default
  private Float discount = 0F;

  @Column(name = "payment_method", nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  public void updateInfor(Customer customer, User user, Float totalAmount, Float discount) {
    this.customer = customer;
    this.user = user;
    this.totalAmount = totalAmount;
    this.discount = discount;
  }

  public void updateDelivery(DeliveryStatus deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }

  public void upsertOrderCode(String code) {
    this.orderCode = code;
  }

  public void updatePaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public void updateAmount(Float totalAmount) {
    this.totalAmount = totalAmount;
  }

  public void updateDiscount(Float discount) {
    this.discount = discount;
  }
}
