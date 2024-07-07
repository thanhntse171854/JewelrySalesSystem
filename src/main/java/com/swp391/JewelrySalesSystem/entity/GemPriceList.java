package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gem_price_list")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GemPriceList extends BaseEntity implements Serializable {

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
