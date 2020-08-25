package com.auth.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.AuthenticationInfo;
import com.auth.dto.Client;
import com.auth.service.ClientService;
import com.auth.service.JWKService;
import com.auth.service.TokenService;
import com.auth.utils.AuthConstants;
import com.nimbusds.jose.JOSEException;


@RestController("/auth")
public class AuthNProviderController {
	
	@Autowired
	TokenService tokenSerivce;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	JWKService jwkeyService;
	
	/**
	 * 토큰 발급 요청 처리
	 * Request Body에 어떤 데이터가 올지 생각하면서 코딩할 것.
	 */
	@PostMapping("/token/issue")
	public HashMap<String, String> issueToken(
			@RequestHeader("value = Authorization") String clientCredentials,
			@RequestBody AuthenticationInfo authenticationInfo,
			HttpServletRequest request) throws UnsupportedEncodingException, JOSEException {
		
		List<String> tokens = tokenSerivce.issueToken(authenticationInfo.getAuthorizationCode(), null, clientCredentials, authenticationInfo.getGrantType(), request);
		
		HttpSession session = request.getSession(); 
		String userId = (String) session.getAttribute("user-id");
		
		HashMap<String, String> idAndTokens = new HashMap<String, String>();
		
		idAndTokens.put("user-id", userId);
		idAndTokens.put("access-token", tokens.get(0));
		idAndTokens.put("refresh-token", tokens.get(1));
		
		return idAndTokens;
	}
	
	/**
	 * 토큰 재발급
	 * Request Body에 어떤 데이터가 올지 생각하면서 코딩할 것.
	 */
	@PostMapping("/token/issue")
	public String reIssueToken() {

		String refreshToken = "";
		return refreshToken;
	}
	
	/**
	 * JWK 등록 요청 처리
	 * Request Body에 어떤 데이터가 올지 생각하면서 코딩할 것.
	 * @throws IOException 
	 */
	@PostMapping("/jwk/add")
	public String addJWk() throws IOException {
		
		jwkeyService.genJWK(AuthConstants.JWK_KID);
		
		return "success";
	}
	
	/**
	 * 클라이언트 등록 요청 처리
	 * Request Body에 어떤 데이터가 올지 생각하면서 코딩할 것.
	 */
	@PostMapping("/client/register")
	public String registerClient(
			@RequestBody Client clientInfo) {
		
		clientService.registerClient(clientInfo);
		
		return "success";
	}
}
