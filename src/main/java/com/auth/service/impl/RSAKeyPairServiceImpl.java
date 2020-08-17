package com.auth.service.impl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dto.RSAKeyPair;
import com.auth.repository.RSAKeyPairDao;
import com.auth.service.RSAKeyPairService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RSAKeyPairServiceImpl implements RSAKeyPairService {
	
	@Autowired
	RSAKeyPairDao rsaKeyPairDao;
	
	public void genRSAKeyPair(String kid) {
		KeyPairGenerator gen = null;
		try {
			gen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		gen.initialize(2048);
		KeyPair keyPair = gen.generateKeyPair();
		
		saveRSAKeyPair(kid, keyPair);
		
	}
	
	public void saveRSAKeyPair(String kid, KeyPair keyPair) {
		RSAKeyPair rsaKeyPair = new RSAKeyPair();
		rsaKeyPair.setKid(kid);
		rsaKeyPair.setRSAKeyPair(keyPair);
		
		// 암호화!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		rsaKeyPairDao.save(rsaKeyPair);
	}
	
	public RSAKeyPair findRSAKeyPairById(String kid) {
		Optional<RSAKeyPair> rsaKeyPair = rsaKeyPairDao.findById(kid);
		
		return rsaKeyPair.orElseThrow(() -> new NoSuchElementException());
	}

}
