package com.auth.service.impl;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dto.JWKey;
import com.auth.repository.JWKeyDao;
import com.auth.service.JWKService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JWKServiceImpl implements JWKService {
	
	@Autowired
	JWKeyDao jwkeyDao;
	
	public void genJWK(String kid) throws IOException {
		KeyPairGenerator gen = null;
		try {
			gen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
//		gen.initialize
		gen.initialize(2048);
		KeyPair keyPair = gen.generateKeyPair();
		
		// Convert to JWK format
		JWK jwk = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyUse(KeyUse.SIGNATURE)
				.keyID(kid)
				.build();
		
		JWKey jwkey = new JWKey();
		jwkey.setKid(kid);
		jwkey.setRsaKeyPair(jwkey.serializeRSAKeyPair(keyPair));
		jwkey.setJwk(jwk);
		
		//JWK 저장
		jwkeyDao.save(jwkey);
		
	}
	
	public JWKey findJWKeyById(String kid) {
		Optional<JWKey> jwkey = jwkeyDao.findById(kid);
		return jwkey.orElseThrow(() -> new NoSuchElementException());
	}

}
