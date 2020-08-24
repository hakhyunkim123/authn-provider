package com.auth.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.AuthenticationInfo;
import com.auth.service.TokenService;
import com.nimbusds.jose.JOSEException;

/**
 * 토큰 발급 컨트롤러
 * Request Body에 어떤 데이터가 올지 생각하면서 코딩할 것.
 */
@RestController("/auth")
public class AuthNProviderController {
	
	@Autowired
	TokenService tokenserivce;
	
	@PostMapping("/issue/token")
	public HashMap<String, String> issueToken(
			@RequestHeader("value = Authorization") String clientCredentials,
			@RequestBody AuthenticationInfo authenticationInfo,
			HttpServletRequest request) throws UnsupportedEncodingException, JOSEException {
		
		List<String> tokens = tokenserivce.issueToken(authenticationInfo.getAuthorizationCode(), null, clientCredentials, authenticationInfo.getGrantType(), request);
		
		HttpSession session = request.getSession(); 
		String userId = (String) session.getAttribute("user-id");
		
		HashMap<String, String> idAndTokens = new HashMap<String, String>();
		
		idAndTokens.put("user-id", userId);
		idAndTokens.put("access-token", tokens.get(0));
		idAndTokens.put("refresh-token", tokens.get(1));
		
		return idAndTokens;
		
	}
}
