package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.dto.JWKey;

public interface JWKeyDao extends JpaRepository<JWKey, String>{

}
