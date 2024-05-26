package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductCategory extends BaseEntity implements Serializable {
  @Column(name = "category_name", nullable = false, length = 100)
  private String categoryName;

  @Column(name = "category_type", length = 50)
  @Enumerated(EnumType.STRING)
  private CategoryType categoryType;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Product> products;
}
