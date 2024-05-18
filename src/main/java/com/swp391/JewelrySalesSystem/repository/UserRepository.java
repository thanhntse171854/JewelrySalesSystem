package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByPhone(String username);
}
