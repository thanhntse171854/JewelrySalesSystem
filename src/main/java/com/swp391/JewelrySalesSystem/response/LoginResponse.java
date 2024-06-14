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
public class LoginResponse {
  private Long id;
  private String phone;
  private String name;
  private String accessToken;
  private String refreshToken;
  private List<RoleUser> roleUsers;
}
