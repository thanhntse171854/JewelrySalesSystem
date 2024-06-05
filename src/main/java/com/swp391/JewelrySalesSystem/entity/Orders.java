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
  private float totalAmount;

  @Column(name = "delivery_stage_status")
  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @Column(name = "payment_method", nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  public void updateDelivery(DeliveryStatus deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }
}
