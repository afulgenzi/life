package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(@Param("email") String emailAddress);

    User findByUsername(@Param("username") String username);
}
