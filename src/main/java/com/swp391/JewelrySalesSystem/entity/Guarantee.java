package com.swp391.JewelrySalesSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

  @Column(name = "guarantee_time")
  private Long guaranteeTime;
}
