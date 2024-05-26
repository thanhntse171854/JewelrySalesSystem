package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_materials")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductMaterial extends BaseEntity implements Serializable {
  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "material_id", referencedColumnName = "id")
  private Material material;

  @Column(name = "weight")
  private Float weight;
}
