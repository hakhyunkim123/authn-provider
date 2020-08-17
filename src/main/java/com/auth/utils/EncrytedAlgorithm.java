package com.auth.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncrytedAlgorithm {

	public static byte[] sha256(String msg) throws NoSuchAlgorithmException {
		MessageDigest md =  MessageDigest.getInstance("SHA-256");
		md.update(msg.getBytes());
		
		return md.digest();
	}
}
