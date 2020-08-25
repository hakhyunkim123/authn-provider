package com.auth.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dto.Client;
import com.auth.dto.JWKey;
import com.auth.dto.TokenVertifyInfo;
import com.auth.repository.ClientDao;
import com.auth.repository.JWKeyDao;
import com.auth.repository.TokenVerifyInfoDao;
import com.auth.service.ClientService;
import com.auth.service.JWKService;
import com.auth.service.TokenService;
import com.auth.utils.AuthConstants;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {	
	@Autowired
	TokenVerifyInfoDao tokenVertifyInfoDao;
	
	@Autowired
	JWKService jwkService;
	
	@Autowired
	ClientService clientService;
	
	/**
	 * 토큰 발급
	 * @throws JOSEException 
	 */
	public List<String> issueToken(String authCode, String redirectURL, 
			String clientCredentials, String grantType, HttpServletRequest request) throws UnsupportedEncodingException, JOSEException {
		
		HashMap<String, String> decodedClientCredentials = clientService.decodeClientCredentials(clientCredentials);
		
		String clientId = decodedClientCredentials.get("client-id");
		String clientSecret = decodedClientCredentials.get("client-secret");
		
		// 클라이언트 검증
		boolean isClientCorrect = clientService.vertifyClient(clientId, clientSecret);
	
		if(!isClientCorrect) {
//			insertLoginHist();
//			PUB 후처리 이력적재
//			return error
			return null;
		}
		
		HttpSession session = request.getSession(); 
		if(grantType.equals("authorization_code")) {
			
			String authCodeInSession = (String)session.getAttribute("auth-code");
			
			boolean isAuthCodeCorrect = authCode.equals(authCodeInSession);
			
			if(!isAuthCodeCorrect) {
//				insertIntoLoginHist();
//				PUB 후처리 이력적재
//				return error
			}
			
//			String userId = (String)session.getAttribute("user-id");
			String accessToken = genAccessToken(clientId);
			String refreshToken = getRefreshToken(clientId);
			
//			insertRefreshToken(userId, client.getClientId(), refreshToken)
//			PUB 후처리 이력적재
//			return error
			
			// 토큰 return
			List<String> tokens = new ArrayList<String>();
			
			tokens.add(accessToken);
			tokens.add(refreshToken);
			
			return tokens;
		}
		
		else if(grantType.equals("client_credentials")) {
//			String userId = (String)session.getAttribute("user-id");
			String accessToken = genAccessToken(clientId);
			
//			PUB 후처리 이력적재
//			return error
			
			List<String> tokens = new ArrayList<String>();
			
			tokens.add(accessToken);
			
			return tokens;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Access Token 생성
	 * @throws JOSEException 
	 * @throws NoSuchAlgorithmException 
	 */
	public String genAccessToken(String clientId) throws JOSEException {
		// JWK To PrivateKey To signature
		JWKey jwkey = jwkService.findJWKeyById(AuthConstants.JWK_KID);
		PrivateKey privateKey = jwkey.getJwk().toRSAKey().toPrivateKey();

		// Create RSA Signer
		JWSSigner signer = null;
		signer = new RSASSASigner(privateKey);
		
		// 토큰 검증정보 추출
		TokenVertifyInfo tokenVertifyInfo = findTokenVertifyInfoById(clientId);
		
		String issuer = tokenVertifyInfo.getIssuer();
		String subject = tokenVertifyInfo.getSubject();
		
		Date expiredTime = new Date(new Date().getTime() + 60 * 1000 * 5);
		
		JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder()
			.claim("clientId", clientId)
			.issuer(issuer)
			.subject(subject)
			.expirationTime(expiredTime)
			.build();

		// make access token with claim set
		SignedJWT accessToken = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), jwtClaimSet);
		// Apply the RSA-256 protection
		accessToken.sign(signer);

				
		// Serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		String accessTokenSerialized = accessToken.serialize();
					
		return accessTokenSerialized;
	}
	
	/**
	 * Refresh Token 생성
	 */
	public String getRefreshToken(String clientId) throws JOSEException {
		// JWK To PrivateKey To signature
		JWKey jwkey = jwkService.findJWKeyById(AuthConstants.JWK_KID);
		PrivateKey privateKey = jwkey.getJwk().toRSAKey().toPrivateKey();
				
		// Create RSA Signer
		JWSSigner signer = null;
		signer = new RSASSASigner(privateKey);
		
		// 토큰 검증정보 추출
		TokenVertifyInfo tokenVertifyInfo = findTokenVertifyInfoById(clientId);
		
		String issuer = tokenVertifyInfo.getIssuer();
		String subject = tokenVertifyInfo.getSubject();
		
		Date expiredTime = new Date(new Date().getTime() + 60 * 1000 * 5);
		
		JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder()
			.claim("clientId", clientId)
			.issuer(issuer)
			.subject(subject)
			.expirationTime(expiredTime)
			.build();

		// make access token with claim set
		SignedJWT refreshToken = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), jwtClaimSet);
		refreshToken.sign(signer);
				
		// Serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		String accessTokenSerialized = refreshToken.serialize();
					
		return accessTokenSerialized;
	}
	
	/**
	 * 토큰 검증정보 추출
	 */
	public TokenVertifyInfo findTokenVertifyInfoById(String clientId) {
		Optional<TokenVertifyInfo> tokenVertifyInfo = tokenVertifyInfoDao.findById(clientId);
		
		return tokenVertifyInfo.orElseThrow(() -> new NoSuchElementException());
	}
	
	/**
	 * 토큰 재발급 서비스 구현
	 */
	public String reIssueToken(String userId, String clientId, String refreshToken) {
		// Refresh Token 검증
		boolean isRefreshTokenCorrect = verifyRefreshToken(userId, clientId, refreshToken);
	
		String newAccessToken = "";
		
		return newAccessToken;
	}
	
	boolean verifyRefreshToken(String userId, String clientId, String refreshToken) {
		
		return true;
	}
}
