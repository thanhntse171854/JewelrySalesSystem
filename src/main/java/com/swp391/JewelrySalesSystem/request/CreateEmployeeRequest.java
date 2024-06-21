package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.Gender;
import com.swp391.JewelrySalesSystem.enums.RoleUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateEmployeeRequest {
  @NotNull(message = "Email is require")
  @NotBlank(message = "Email can not be blank")
  @Pattern(
      regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message = "The email format is incorrect.")
  @Schema(description = "example", example = "example@email.com")
  private String email;

  @NotNull(message = "Name is require")
  @NotBlank(message = "Name can not be blank")
  @Pattern(regexp = "^[A-Za-z]{2,30}$\n", message = "The name format is incorrect.")
  @Schema(description = "name", example = "Nguyen Van A")
  private String name;

  @NotNull(message = "Phone is require")
  @NotBlank(message = "Phone can not be blank")
  @Pattern(regexp = "^\\d{10}$\n", message = "The phone number must be 10 number.")
  @Schema(description = "phone", example = "0123456789")
  private String phone;

  private Gender gender;

  @NotNull(message = "Date Of Birth is require")
  @NotBlank(message = "Date Of Birth can not be blank")
  @Pattern(
      regexp = "^(0[1-9]|1[0-2])/(0[1-9]|1\\d|2\\d|3[01])/2006$\n",
      message = "The date of birth is incorrect.")
  @Schema(description = "Date of birth", example = "01/01/2003")
  private Long dateOfBirth;

  private String address;

  @NotNull(message = "Password is require")
  @NotBlank(message = "Password can not be blank")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
      message = "The password must be lest 8 characters, English, numbers, and special characters.")
  @Schema(description = "password", example = "NguyenVan@123")
  private String password;

  @NotNull(message = "Confirm password is require")
  @NotBlank(message = "Confirm password can not be blank")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
      message = "The password must be lest 8 characters, English, numbers, and special characters.")
  @Schema(description = "password", example = "NguyenVan@123")
  private String confirmPassword;

  private RoleUser role;
}
