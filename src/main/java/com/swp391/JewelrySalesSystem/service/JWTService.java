package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.security.SecurityAccountDetails;

public interface JWTService {
  Boolean validateToken(String token);

  Long getIdFromJwtToken(String token);

  String getUsernameFromJwtToken(String token);

  String generateAccessToken(SecurityAccountDetails securityAccountDetails);

  String generateRefreshToken(SecurityAccountDetails securityAccountDetails);
}
