package com.auth.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "slc-client")
public class Client {
	private String clientId;
	private String clientSecret;
	private String grantType;
	private String redirectUrl;
}
