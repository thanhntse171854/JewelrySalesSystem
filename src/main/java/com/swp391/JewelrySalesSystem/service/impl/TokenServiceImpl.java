package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.RefreshToken;
import com.swp391.JewelrySalesSystem.entity.User;
import com.swp391.JewelrySalesSystem.repository.TokenRepository;
import com.swp391.JewelrySalesSystem.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
  private final TokenRepository tokenRepository;

  @Override
  public void saveRefreshToken(String refreshToken, User user) {
    RefreshToken token = RefreshToken.builder().user(user).refreshToken(refreshToken).build();
    tokenRepository.save(token);
  }

  @Override
  @Transactional
  public void deleteRefreshToken(Long id) {
    this.tokenRepository.deleteByUserId(id);
  }
}
