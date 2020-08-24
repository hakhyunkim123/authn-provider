package com.auth.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.KeyPair;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.nimbusds.jose.jwk.JWK;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="slc-jwk")
public class JWKey implements Serializable {
	private String kid;
	private byte[] rsaKeyPair;
	private JWK jwk;
	
	public byte[] serializeRSAKeyPair(KeyPair keyPair) {
		
		byte[] serializedRSAKeyPair = null;
		
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			try(ObjectOutputStream oos = new ObjectOutputStream(baos)) {
				oos.writeObject(keyPair);
				
				serializedRSAKeyPair = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return serializedRSAKeyPair;
	}
	
	public static KeyPair deserializeRSAKeyPair(byte[] serializedJWKey) {
		KeyPair deserializedRSAKeyPair = null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedJWKey)){
			try (ObjectInputStream ois = new ObjectInputStream(bais)) {
				Object objectRSAKeyPair = ois.readObject();
				deserializedRSAKeyPair = (KeyPair) objectRSAKeyPair;
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		if(deserializedJWKey == null) {
//			
//		}
		
		return deserializedRSAKeyPair;
	}
}
