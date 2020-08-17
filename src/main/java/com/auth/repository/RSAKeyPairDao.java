package com.auth.repository;

import java.security.KeyPair;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.dto.RSAKeyPair;

public interface RSAKeyPairDao extends JpaRepository<RSAKeyPair, String>{

}
