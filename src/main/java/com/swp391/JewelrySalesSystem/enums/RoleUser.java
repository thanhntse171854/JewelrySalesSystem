package com.swp391.JewelrySalesSystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleUser {
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_MANAGER("ROLE_MANAGER"),
  ROLE_CASHIER_STAFF("ROLE_CASHIER_STAFF"),
  ROLE_SALES_STAFF("ROLE_SALES_STAFF");
  private final String roleName;

  public static String getRoleName(String roleName) {
    for (RoleUser role : RoleUser.values()) {
      if (role.getRoleName().equals(roleName)) return role.getRoleName();
    }
    return null;
  }
}
