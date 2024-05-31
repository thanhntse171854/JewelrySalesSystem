package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guarantees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Guarantee extends BaseEntity implements Serializable {
  @Column(name = "guarantee_name", length = 100)
  private String guaranteeName;

  @Column(name = "guarantee_from")
  private Long guaranteeFrom = System.currentTimeMillis();

  @Column(name = "guarantee_to", nullable = false)
  private Long guaranteeTo;

  @ManyToOne
  @JoinColumn(name = "order_detail_id")
  private OrderDetail orderDetail;
}
