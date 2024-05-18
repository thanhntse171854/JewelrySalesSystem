package com.swp391.JewelrySalesSystem.security;

import com.swp391.JewelrySalesSystem.entity.User;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SecurityAccountDetails implements UserDetails {
  private Long id;
  private String phone;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public static SecurityAccountDetails build(User account, List<GrantedAuthority> authorityList) {
    return SecurityAccountDetails.builder()
        .id(account.getId())
        .phone(account.getPhone())
        .password(account.getPassword())
        .authorities(authorityList)
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.phone;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
