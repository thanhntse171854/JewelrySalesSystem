package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.PaymentStatus;
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
  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id")
  private User user;

  @Column(name = "customer_name", length = 50)
  private String customerName;

  @Column(name = "phone", length = 16, nullable = false)
  private String phone;

  @Column(name = "is_product_store")
  private boolean isProductStore;

  @Column(name = "total_price")
  private Float totalPrice;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<PurchaseOrderDetail> list = new ArrayList<>();
}
