package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Role;
import com.swp391.JewelrySalesSystem.entity.User;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.enums.RoleUser;
import com.swp391.JewelrySalesSystem.exception.LoginException;
import com.swp391.JewelrySalesSystem.facade.UserFacade;
import com.swp391.JewelrySalesSystem.request.LoginRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.LoginResponse;
import com.swp391.JewelrySalesSystem.security.SecurityAccountDetails;
import com.swp391.JewelrySalesSystem.service.JWTService;
import com.swp391.JewelrySalesSystem.service.TokenService;
import com.swp391.JewelrySalesSystem.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
  private final JWTService jwtService;
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;

  @Override
  public BaseResponse<LoginResponse> login(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getPhone(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    User account = userService.findByPhone(loginRequest.getPhone());

    boolean isNotActive = !account.isActive();
    if (isNotActive) throw new LoginException(ErrorCode.USER_IS_DEACTIVATED);

    SecurityAccountDetails userPrinciple = (SecurityAccountDetails) authentication.getPrincipal();
    return BaseResponse.build(buildLoginResponse(userPrinciple, account), true);
  }

  private LoginResponse buildLoginResponse(
      SecurityAccountDetails securityAccountDetails, User user) {
    var accessToken = jwtService.generateAccessToken(securityAccountDetails);
    var refreshToken = jwtService.generateRefreshToken(securityAccountDetails);

    List<RoleUser> roleUsers = user.getRoles().stream().map(Role::getName).toList();
    tokenService.saveRefreshToken(refreshToken, user);
    return LoginResponse.builder()
        .id(user.getId())
        .phone(user.getPhone())
        .name(user.getName())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .roleUsers(roleUsers)
        .build();
  }

  @Override
  public void logout() {
    var principal =
        (SecurityAccountDetails)
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userService.findById(principal.getId());
    tokenService.deleteRefreshToken(user.getId());
  }
}
