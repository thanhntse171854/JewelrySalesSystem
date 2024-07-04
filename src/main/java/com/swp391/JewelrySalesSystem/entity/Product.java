package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.Gender;
import com.swp391.JewelrySalesSystem.enums.Size;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Product extends BaseEntity implements Serializable {
  @Column(name = "product_code", nullable = false, length = 20)
  private String productCode;

  @Column(name = "product_name", nullable = false, length = 100)
  private String productName;

  @Column(name = "gem_cost")
  @Builder.Default
  private Long gemCost = 0L;

  @Column(name = "production_cost")
  @Builder.Default
  private Long productionCost = 0L;

  @Column(name = "gender", length = 10)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private ProductCategory category;

  @Column(name = "is_gem")
  private Boolean isGem;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductGem> productGems = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductMaterial> productMaterials = new ArrayList<>();

  @Column(name = "size", nullable = false)
  @Enumerated(EnumType.STRING)
  private Size size;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductAsset> productAssets = new ArrayList<>();

  public void addProductMaterial(List<ProductMaterial> productMaterial) {
    this.productMaterials = productMaterial;
  }

  public void addImages(List<ProductAsset> productAssets) {
    this.productAssets = productAssets;
  }

  public void updateIsGem(boolean isGem) {
    this.isGem = isGem;
  }

  public void updateBasicInfo(
      String name,
      Long gemCost,
      Long productionCost,
      Gender gender,
      ProductCategory category,
      Size size) {
    if (name != null) {
      this.productName = name;
    }
    if (gemCost != null) {
      this.gemCost = gemCost;
    }
    if (productionCost != null) {
      this.productionCost = productionCost;
    }
    if (gender != null) {
      this.gender = gender;
    }
    if (category != null) {
      this.category = category;
    }
    if (size != null) {
      this.size = size;
    }
  }
}
