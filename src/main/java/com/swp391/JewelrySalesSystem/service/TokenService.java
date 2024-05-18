package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.User;

public interface TokenService {
  void saveRefreshToken(String refreshToken, User user);

  void deleteRefreshToken(Long id);
}
