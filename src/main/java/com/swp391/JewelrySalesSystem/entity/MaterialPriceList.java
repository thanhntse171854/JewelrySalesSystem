package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_price_list")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MaterialPriceList extends BaseEntity implements Serializable {
  @ManyToOne
  @JoinColumn(name = "material_id", nullable = false)
  private Material material;

  @Column(name = "buy_price", nullable = false)
  private Float buyPrice;

  @Column(name = "sell_price", nullable = false)
  private Float sellPrice;

  @Column(name = "effect_date", nullable = false)
  private Long effectDate;

  public void updatePrice(Float sellPrice, Float buyPrice) {
    if (sellPrice != null) {
      this.sellPrice = sellPrice;
    }
    if (buyPrice != null) {
      this.buyPrice = buyPrice;
    }
  }
}
