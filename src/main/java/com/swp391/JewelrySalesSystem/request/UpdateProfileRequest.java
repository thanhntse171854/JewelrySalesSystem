package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.Gender;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateProfileRequest {
  private String email;
  private String phone;
  private String name;
  private Gender gender;
  private Long birthday;
  private String address;
}
