package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.RoleUser;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Role extends BaseEntity implements Serializable {

  @Column(name = "role_name", nullable = false, length = 100)
  @Enumerated(EnumType.STRING)
  private RoleUser name;
}
