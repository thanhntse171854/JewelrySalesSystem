package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gems")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Gem extends BaseEntity implements Serializable {
  @Column(name = "gem_code", length = 50)
  private String gemCode;

  @Column(name = "gem_name", length = 100)
  private String gemName;

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

  @Column(name = "is_jewelry")
  @Builder.Default
  private boolean isDiamondJewelry = true;

  public void updateBasicInfo(
      String name,
      String code,
      String cut,
      String color,
      Float carat,
      boolean isDiamondJewelry,
      String origin,
      String clarity) {
    if (name != null) {
      this.gemName = name;
    }
    if (code != null) {
      this.gemCode = code;
    }
    if (cut != null) {
      this.cut = cut;
    }
    if (color != null) {
      this.color = color;
    }
    if (carat != null) {
      this.carat = carat;
    }
    this.isDiamondJewelry = isDiamondJewelry;
    if (origin != null) {
      this.origin = origin;
    }
    if (clarity != null) {
      this.clarity = clarity;
    }
  }
}
