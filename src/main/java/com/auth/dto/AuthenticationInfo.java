package com.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationInfo {
	private String authorizationCode;
	private String grantType;
}
