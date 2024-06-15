package com.swp391.JewelrySalesSystem.entity;

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
@Table(name = "purchase_order")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PurchaseOrder extends BaseEntity implements Serializable {

  @Column(name = "purchase_order_code", nullable = false, unique = true)
  private String purchaseOrderCode;

  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id")
  private User user;

  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "is_product_store")
  private boolean isProductStore;

  @Column(name = "total_price")
  private Float totalPrice;

  @Column(name = "payment_method")
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<PurchaseOrderDetail> list = new ArrayList<>();

  public void updatePaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}
