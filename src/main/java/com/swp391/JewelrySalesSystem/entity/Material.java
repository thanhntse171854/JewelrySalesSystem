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
@Table(name = "materials")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Material extends BaseEntity implements Serializable {
  @Column(name = "material_name", nullable = false, length = 100)
  private String name;

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<MaterialPriceList> priceList = new ArrayList<>();

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
  private List<ProductMaterial> productMaterials = new ArrayList<>();
}
