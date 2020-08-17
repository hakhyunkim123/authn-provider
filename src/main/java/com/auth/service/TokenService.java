package com.auth.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.auth.dto.TokenVertifyInfo;


public interface TokenService {
	public List<String> issueToken(String authCode, String redirectURL, 
			String clientCredentials, String grantType, HttpServletRequest request) throws UnsupportedEncodingException;
	
	public String genAccessToken(String clientId);
	
	public String getRefreshToken(String clientId);
	
	public TokenVertifyInfo findTokenVertifyInfoById(String clientId) throws NoSuchAlgorithmException;
}
