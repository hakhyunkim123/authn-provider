package com.auth.service;

import java.security.KeyPair;

import com.auth.dto.RSAKeyPair;

public interface RSAKeyPairService {
	
	public void genRSAKeyPair(String kid);
	
	public void saveRSAKeyPair(String kid, KeyPair keyPair);
	
	public RSAKeyPair findRSAKeyPairById(String kid);

}
