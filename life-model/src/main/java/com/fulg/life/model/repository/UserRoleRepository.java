package com.fulg.life.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT userRole FROM UserRole userRole WHERE userRole.role.role = :role")
    List<UserRole> findByRoleName(@Param("role") String role);

    @Query("SELECT userRole FROM UserRole userRole WHERE userRole.user.username = :username")
    List<UserRole> findByUserName(@Param("username") String username);

    @Query("SELECT userRole FROM UserRole userRole WHERE userRole.user.username = :username AND userRole.role.role = :role")
    UserRole findByUserRole(@Param("username") String username, @Param("role") String role);
}
