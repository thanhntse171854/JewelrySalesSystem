package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_gem")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductGem extends BaseEntity implements Serializable {
  @ManyToOne
  @JoinColumn(name = "gem_id")
  private Gem gem;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "weight")
  private float weight;
}
