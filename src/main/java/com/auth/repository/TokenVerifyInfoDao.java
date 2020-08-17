package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.dto.TokenVertifyInfo;

@Repository
public interface TokenVerifyInfoDao extends JpaRepository<TokenVertifyInfo, String>{
	
}
