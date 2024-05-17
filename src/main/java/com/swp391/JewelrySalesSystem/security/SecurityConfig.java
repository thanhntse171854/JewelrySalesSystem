package com.swp391.JewelrySalesSystem.security;

import com.swp391.JewelrySalesSystem.service.JWTService;
import com.swp391.JewelrySalesSystem.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
  private final UserService userService;
  private final JWTService jwtService;

  private static final String[] WHITE_LIST = {
    "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/v1/location/**"
  };

  private static final String[] PUBLIC_LIST = {"/api/v1/users/**"};

  public SecurityConfig(UserService userService, JWTService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public JwtAuthenticationFilter authTokenFilter() {
    return new JwtAuthenticationFilter(jwtService, userService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(WHITE_LIST)
                    .permitAll()
                    .requestMatchers(PUBLIC_LIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated());
    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
  }
}
