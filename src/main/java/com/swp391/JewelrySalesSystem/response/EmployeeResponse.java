package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.enums.RoleUser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EmployeeResponse {
  private Long staffId;
  private String name;
  private String phone;
  private List<RoleUser> roles;
}
