package com.auth.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Base64.Decoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dto.Client;
import com.auth.repository.ClientDao;
import com.auth.service.ClientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {
	@Autowired
	ClientDao clientDao;
	
	public HashMap<String, String> decodeClientCredentials(String clientCredentials) throws UnsupportedEncodingException {
		
		Decoder decoder = Base64.getDecoder();
		
		String decodedClientCredentials = new String(decoder.decode(clientCredentials), "UTF-8");
	
		String clientInfo = decodedClientCredentials.split(" ")[1];
		
		String[] clientIdAndSecret = clientInfo.split(":");

		String clientId = clientIdAndSecret[0];
		String clientSecret = clientIdAndSecret[1];
		
		HashMap<String, String> clientIdAndSecretMap = new HashMap<String, String>();
		
		clientIdAndSecretMap.put("client-id", clientId);
		clientIdAndSecretMap.put("client-secret", clientSecret);
		
		return clientIdAndSecretMap;
	}
	
	public boolean vertifyClient(String clientId, String clientSecret) throws UnsupportedEncodingException {
		
		Client clientInDB = findByClientId(clientId);
		
		return clientId.equals(clientInDB.getClientId()) && clientSecret.equals(clientInDB.getClientSecret());	
	}
	
	public Client findByClientId(String clientId) {
		Optional<Client> client = clientDao.findById(clientId);
		
		return client.orElseThrow(() -> new NoSuchElementException());
	}
	
	public void registerClient(Client client) {
		clientDao.save(client);
	}
}
