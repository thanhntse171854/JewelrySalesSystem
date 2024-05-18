package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.security.SecurityAccountDetails;
import com.swp391.JewelrySalesSystem.service.JWTService;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JWTServiceImpl implements JWTService {
  @Value("${spring.jwt.secretKey}")
  private String secretKey;

  @Value("${spring.jwt.accessTokenExpirationTime}")
  private String accessTokenExpirationTime;

  @Value("${spring.jwt.refreshTokenExpirationTime}")
  private String refreshTokenExpirationTime;

  @Override
  public Boolean validateToken(String token) {
    if (null == token) return false;
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parse(token);
    } catch (MalformedJwtException
        | ExpiredJwtException
        | UnsupportedJwtException
        | IllegalArgumentException exception) {

    }
    return true;
  }

  @Override
  public Long getIdFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .get("userId", Long.class);
  }

  @Override
  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .get("phone", String.class);
  }

  @Override
  public String generateAccessToken(SecurityAccountDetails securityAccountDetails) {
    Map<String, Object> claims = setClaims(securityAccountDetails);
    return Jwts.builder()
        .setSubject(securityAccountDetails.getUsername())
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime)))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  @Override
  public String generateRefreshToken(SecurityAccountDetails securityAccountDetails) {
    Map<String, Object> claims = setClaims(securityAccountDetails);
    return Jwts.builder()
        .setSubject(securityAccountDetails.getUsername())
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime)))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  private Map<String, Object> setClaims(SecurityAccountDetails securityAccountDetails) {
    List<String> role =
        securityAccountDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", securityAccountDetails.getId());
    claims.put("phone", securityAccountDetails.getPhone());
    claims.put("roles", role);
    return claims;
  }
}
