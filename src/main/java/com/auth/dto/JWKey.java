package com.auth.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.KeyPair;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nimbusds.jose.jwk.JWK;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="SLC_AUTH_KEY")
public class JWKey implements Serializable {
	@Column(name="AUTH_KEY_SEQ_NO")
	private long seq;
	
	@Column(name="KEY_ID")
	private String kid;
	
	@Column(name="RSA_KEY")
	private byte[] rsaKeyPair;
	
	@Column(name="JWK")
	private JWK jwk;
	
	@Column(name="REG_DT")
	private String registerDate;
	
	@Column(name="REG_USR")
	private String registerUser;
	
	@Column(name="UDP_DT")
	private String updateDate;
	
	@Column(name="UDP_DT")
	private String updateUser;
	
	@Column(name="DEL_DT")
	private String deleteDate;
	
	@Column(name="DEL_DT")
	private String deleteUser;
	
	@Column(name="USE_YN")
	private String useYN;
	
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
