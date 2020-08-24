package com.auth.service;

import java.io.IOException;
import java.security.KeyPair;

import com.auth.dto.JWKey;

public interface JWKService {
	
	public void genJWK(String kid) throws IOException;
	
	public JWKey findJWKeyById(String kid);

}
