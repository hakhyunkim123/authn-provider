package com.auth.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.auth.dto.Client;

public interface ClientService {
	
	public boolean vertifyClient(String clientId, String clientSecret) throws UnsupportedEncodingException;
	
	HashMap<String, String> decodeClientCredentials(String clientCredentials) throws UnsupportedEncodingException;
	
	public Client findByClientId(String clientId);
}
