package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_assets")
public class ProductAsset extends BaseEntity implements Serializable {
  @Column(name = "media_key", nullable = false)
  private String mediaKey;

  @Column(name = "media_url", nullable = false)
  private String mediaUrl;

  @ManyToOne(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
}
