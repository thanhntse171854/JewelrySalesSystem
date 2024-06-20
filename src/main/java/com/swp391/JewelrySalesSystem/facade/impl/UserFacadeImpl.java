package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Role;
import com.swp391.JewelrySalesSystem.entity.User;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.enums.RoleUser;
import com.swp391.JewelrySalesSystem.exception.ChangePasswordException;
import com.swp391.JewelrySalesSystem.exception.LoginException;
import com.swp391.JewelrySalesSystem.facade.UserFacade;
import com.swp391.JewelrySalesSystem.request.ChangePasswordRequest;
import com.swp391.JewelrySalesSystem.request.LoginRequest;
import com.swp391.JewelrySalesSystem.request.UpdateProfileRequest;
import com.swp391.JewelrySalesSystem.request.UpdateRoleRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.EmployeeResponse;
import com.swp391.JewelrySalesSystem.response.LoginResponse;
import com.swp391.JewelrySalesSystem.response.UserProfileResponse;
import com.swp391.JewelrySalesSystem.security.SecurityAccountDetails;
import com.swp391.JewelrySalesSystem.service.*;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
  private final JWTService jwtService;
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;
  private final CloudinaryService cloudinaryService;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

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

  @Override
  public BaseResponse<UserProfileResponse> getProfile(Long id) {
    //    var principal =
    //        (SecurityAccountDetails)
    //            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //    User user = userService.findByPhone(principal.getPhone());

    User user = userService.findById(id);

    return BaseResponse.build(
        UserProfileResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .gender(user.getGender())
            .avatar(user.getAvatar())
            .address(user.getAddress())
            .birthday(user.getDateOfBirth())
            .roleUser(getRole(user))
            .isActive(user.isActive())
            .build(),
        true);
  }

  @Override
  public BaseResponse<UserProfileResponse> getProfileDetail(Long id) {
    User user = userService.findById(id);

    return BaseResponse.build(
        UserProfileResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .gender(user.getGender())
            .avatar(user.getAvatar())
            .address(user.getAddress())
            .birthday(user.getDateOfBirth())
            .roleUser(getRole(user))
            .build(),
        true);
  }

  @Override
  public BaseResponse<UserProfileResponse> updateRole(UpdateRoleRequest request) {
    User user = userService.findById(request.getId());
    List<Role> roles = new ArrayList<>();
    for (RoleUser roleEnum : request.getRoles()) {
      Role role = roleService.findRole(roleEnum);
      roles.add(role);
    }
    user.updateRole(roles);
    userService.save(user);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<UserProfileResponse> updateProfileByStaff(UpdateProfileRequest request) {
    //    var principal =
    //        (SecurityAccountDetails)
    //            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //    User user = userService.findByPhone(principal.getPhone());

    User user = userService.findById(request.getId());
    if (request.getName() != null) {
      user.setName(request.getName());
    }
    if (request.getEmail() != null) {
      user.setEmail(request.getEmail());
    }
    if (request.getGender() != null) {
      user.setGender(request.getGender());
    }
    if (request.getAddress() != null) {
      user.setAddress(request.getAddress());
    }
    if (request.getPhone() != null) {
      user.setPhone(request.getPhone());
    }
    if (request.getBirthday() != null) {
      user.setDateOfBirth(request.getBirthday());
    }

    userService.save(user);

    return BaseResponse.build(
        UserProfileResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .gender(user.getGender())
            .avatar(user.getAvatar())
            .address(user.getAddress())
            .birthday(user.getDateOfBirth())
            .roleUser(getRole(user))
            .isActive(user.isActive())
            .build(),
        true);
  }

  private List<RoleUser> getRole(User user) {
    List<RoleUser> roles = new ArrayList<>();
    for (var role : user.getRoles()) {
      roles.add(role.getName());
    }
    return roles;
  }

  @Override
  public BaseResponse<List<EmployeeResponse>> findAllStaff() {
    List<User> users = userService.findAll();
    List<EmployeeResponse> employeeResponses = new ArrayList<>();
    for (var employee : users) {
      employeeResponses.add(
          EmployeeResponse.builder()
              .staffId(employee.getId())
              .name(employee.getName())
              .phone(employee.getPhone())
              .roles(getRole(employee))
              .isActive(employee.isActive())
              .build());
    }
    return BaseResponse.build(employeeResponses, true);
  }

  @Override
  public BaseResponse<Void> deactivateStaff(Long id) {
    User user = userService.findById(id);
    userService.deactivateStaff(user.getId());
    return BaseResponse.ok();
  }

  @Override
  @SneakyThrows
  public BaseResponse<String> updateAvatar(Long id, MultipartFile file) {
    String avatar = this.cloudinaryService.uploadImage(file.getBytes());
    User user = userService.findById(id);
    user.setAvatar(avatar);
    userService.save(user);
    return BaseResponse.build(avatar, true);
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordRequest request) {
    User user = userService.findById(request.getId());

    boolean isValidCurrentPassword =
        passwordEncoder.matches(request.getOldPassword(), user.getPassword());
    if (!isValidCurrentPassword)
      throw new ChangePasswordException(ErrorCode.CURRENT_PASSWORD_DOES_NOT_MATCH);

    boolean isValidConfirmPassword =
        request.getNewPassword().equals(request.getConfirmNewPassword());
    if (!isValidConfirmPassword)
      throw new ChangePasswordException(ErrorCode.INVALID_CONFIRM_NEW_PASSWORD);

    boolean isPasswordDifferent = request.getOldPassword().equals(request.getNewPassword());
    if (isPasswordDifferent)
      throw new ChangePasswordException(ErrorCode.OLD_PASSWORD_EQUALS_NEW_PASSWORD);

    user.changePassword(passwordEncoder.encode(request.getNewPassword()));
    userService.save(user);
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
