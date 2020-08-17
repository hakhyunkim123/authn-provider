package com.auth.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "slc-tkn-vld-info")
public class TokenVertifyInfo {
	String clientId;
	String issuer;
	String subject;
}
