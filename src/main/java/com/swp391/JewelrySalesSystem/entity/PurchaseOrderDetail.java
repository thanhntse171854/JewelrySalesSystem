package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_detail_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PurchaseOrderDetail extends BaseEntity implements Serializable {

  @Column(name = "name")
  private String name;

  @Column(name = "product_id")
  private Long productId;

  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "purchase_order_id")
  private PurchaseOrder purchaseOrder;

  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "material_id")
  private Material material;

  @Column(name = "weight")
  private Float weight;

  @Column(name = "origin", length = 50)
  private String origin;

  @Column(name = "color", length = 50)
  private String color;

  @Column(name = "clarity")
  private String clarity;

  @Column(name = "cut")
  private String cut;

  @Column(name = "carat")
  private Float carat;

  @Column(name = "price")
  private Float price;
}
