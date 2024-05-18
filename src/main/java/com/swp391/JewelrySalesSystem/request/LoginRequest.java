package com.swp391.JewelrySalesSystem.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginRequest {
  @NotNull(message = "Phone is require")
  @NotBlank(message = "Phone can not be blank")
  @Schema(description = "0123456789", example = "0123456789")
  private String phone;

  @NotNull(message = "Password is require")
  @NotBlank(message = "Password can not be blank")
  private String password;
}
