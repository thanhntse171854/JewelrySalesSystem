package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.enums.Gender;
import com.swp391.JewelrySalesSystem.enums.RoleUser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserProfileResponse {
  private Long id;
  private String email;
  private String phone;
  private String name;
  private Gender gender;
  private Long birthday;
  private String avatar;
  private String address;
  private List<RoleUser> roleUser;
}
