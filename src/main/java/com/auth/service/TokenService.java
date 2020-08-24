package com.auth.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.auth.dto.TokenVertifyInfo;
import com.nimbusds.jose.JOSEException;


public interface TokenService {
	public List<String> issueToken(String authCode, String redirectURL, 
			String clientCredentials, String grantType, HttpServletRequest request) throws UnsupportedEncodingException, JOSEException;
	
	public String genAccessToken(String clientId) throws JOSEException;
	
	public String getRefreshToken(String clientId) throws JOSEException;
	
	public TokenVertifyInfo findTokenVertifyInfoById(String clientId);
}
