package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.RoleUser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoleRequest {
  private Long id;
  private List<RoleUser> roles;
}
