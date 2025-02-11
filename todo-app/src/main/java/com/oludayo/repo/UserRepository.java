package com.oludayo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oludayo.models.User;

public interface UserRepository extends JpaRepository<User, Long> {}
