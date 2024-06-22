package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByPhone(String username);

  @Modifying
  @Transactional
  @Query(value = "update users u set is_active = false where u.id = :id", nativeQuery = true)
  void deactivateStaffById(@Param("id") Long id);

  boolean existsByPhone(String phone);

  boolean existsByEmail(String email);
}
