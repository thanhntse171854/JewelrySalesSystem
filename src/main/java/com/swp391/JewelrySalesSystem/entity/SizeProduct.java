package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "size_products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SizeProduct extends BaseEntity implements Serializable {
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "size_id")
  private Size size;

  @Column(name = "quantity", nullable = false)
  private Long quantity;
}
