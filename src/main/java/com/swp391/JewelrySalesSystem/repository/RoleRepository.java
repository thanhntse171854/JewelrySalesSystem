package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.Role;
import com.swp391.JewelrySalesSystem.enums.RoleUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleUser roleName);
}
