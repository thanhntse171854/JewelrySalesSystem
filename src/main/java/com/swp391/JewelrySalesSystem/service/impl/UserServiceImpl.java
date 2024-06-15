package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.User;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.LoginException;
import com.swp391.JewelrySalesSystem.repository.UserRepository;
import com.swp391.JewelrySalesSystem.security.SecurityAccountDetails;
import com.swp391.JewelrySalesSystem.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public User findByPhone(String phone) {
    return userRepository
        .findByPhone(phone)
        .orElseThrow(() -> new LoginException(ErrorCode.ACCOUNT_NOT_FOUND));
  }

  @Override
  public User findById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new LoginException(ErrorCode.ACCOUNT_NOT_FOUND));
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public void deactivateStaff(Long id) {
    userRepository.deactivateStaffById(id);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByPhone(phone)
            .orElseThrow(() -> new LoginException(ErrorCode.ACCOUNT_NOT_FOUND));
    List<GrantedAuthority> authorityList =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
            .collect(Collectors.toList());
    return SecurityAccountDetails.build(user, authorityList);
  }
}
