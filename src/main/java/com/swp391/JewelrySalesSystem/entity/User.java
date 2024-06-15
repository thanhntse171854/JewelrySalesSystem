package com.swp391.JewelrySalesSystem.entity;

import com.swp391.JewelrySalesSystem.enums.Gender;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class User extends BaseEntity implements Serializable {
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "phone", length = 10, nullable = false, unique = true)
  private String phone;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "date_of_birth", nullable = false)
  private Long dateOfBirth;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "address")
  private String address;

  @Column(name = "is_first_login", nullable = false)
  @Builder.Default
  private boolean isFirstLogin = true;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Builder.Default
  private List<Role> roles = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<RefreshToken> refreshTokens = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Orders> orders = new ArrayList<>();

  public void updateRole(List<Role> role) {
    this.roles = role;
  }
}
