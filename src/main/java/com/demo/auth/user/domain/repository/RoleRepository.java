package com.demo.auth.user.domain.repository;

import com.demo.auth.user.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
