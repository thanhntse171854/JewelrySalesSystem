package com.swp391.JewelrySalesSystem.security;

import com.swp391.JewelrySalesSystem.exception.InvalidTokenException;
import com.swp391.JewelrySalesSystem.service.JWTService;
import com.swp391.JewelrySalesSystem.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JWTService jwtService;
  private final UserService userService;
  private final List<String> PUBLIC_URL = List.of("/api/v1/users/login");

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromHeader(request);
    String requestURI = request.getRequestURI();

    if (isPublicUrl(requestURI)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      if (token != null && jwtService.validateToken(token)) {
        String email = jwtService.getUsernameFromJwtToken(token);
        var principle = userService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(principle, null, principle.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
      filterChain.doFilter(request, response);
    } catch (InvalidTokenException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  private boolean isPublicUrl(String requestURI) {
    return PUBLIC_URL.stream().anyMatch(requestURI::startsWith);
  }
}
