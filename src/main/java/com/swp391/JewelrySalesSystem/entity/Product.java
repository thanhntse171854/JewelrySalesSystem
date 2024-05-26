package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.Gender;
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

  @Column(name = "product_image", columnDefinition = "text")
  private String productImage;

  @Column(name = "gem_cost")
  private Long gemCost;

  @Column(name = "production_cost")
  private Long productionCost;

  @Column(name = "gender", length = 10)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private ProductCategory category;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "guarantee", nullable = false, referencedColumnName = "id")
  private Guarantee guarantee;

  @Column(name = "is_gem")
  private Boolean isGem;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductGem> productGems = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductMaterial> productMaterials = new ArrayList<>();

  @OneToMany(mappedBy = "product")
  private List<SizeProduct> sizeProducts = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductAsset> productAssets = new ArrayList<>();
}
