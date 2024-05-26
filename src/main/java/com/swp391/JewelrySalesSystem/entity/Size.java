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
@Table(name = "sizes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Size extends BaseEntity implements Serializable {
  @Column(name = "size", nullable = false)
  private Float size;

  @Column(name = "diameter", nullable = false)
  private Float diameter;
}
