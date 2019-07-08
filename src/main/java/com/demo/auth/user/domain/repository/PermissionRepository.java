package com.demo.auth.user.domain.repository;

import com.demo.auth.user.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
