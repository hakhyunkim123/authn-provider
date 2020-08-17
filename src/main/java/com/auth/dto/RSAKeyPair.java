package com.auth.dto;

import java.security.KeyPair;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="slc-rsa-keypair")
public class RSAKeyPair {
	private KeyPair RSAKeyPair;
	private String kid;
}
