package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Customer extends BaseEntity implements Serializable {
  @Column(name = "customer_name", nullable = false)
  private String name;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "address")
  private String address;

  @Column(name = "date_of_birth")
  private Long dateOfBirth;

  @Column(name = "percent_discount")
  @Builder.Default
  private Float percentDiscount = 0F;

  @Column(name = "total_amount_purchased")
  @Builder.Default
  private Float totalAmountPurchased = 0F;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Orders> orders = new ArrayList<>();

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

  public void updateDiscount(Float total) {
    if (total >= 300000000) {
      this.percentDiscount = 15.0f;
    } else if (total >= 200000000) {
      this.percentDiscount = 10.0f;
    } else if (total >= 100000000) {
      this.percentDiscount = 5.0f;
    } else {
      this.percentDiscount = 0.0f;
    }
  }

  public void updateTotalAmountPurchase(Float totalAmountPurchased) {
    this.totalAmountPurchased = totalAmountPurchased;
  }

  public void updatePhone(String phone) {
    this.phone = phone;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void addAddress(String address) {
    this.address = address;
  }

  public void addBirthDate(Long dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
