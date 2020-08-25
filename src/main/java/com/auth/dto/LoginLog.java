package com.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="SLC_LOGIN_LOG")
public class LoginLog {
	
	@Column(name="AUTH_SEQ_NO")
	private long seq;
	
	@Column(name="USR_NM")
	private String userId;
	
	@Column(name="IP_ADDR")
	private String ipAddr;
	
	@Column(name="AUTH_DT")
	private String authDateTime;
	
	@Column(name="AUTH_RST")
	private String authResult;
	
	@Column(name="TKN_INFO")
	private String tokenInfo;
	
	@Column(name="TKN_ISSU_DT")
	private String tokenIssueDateTime;
	
	@Column(name="TKN_REISSU_DT")
	private String tokenReissueDateTime;
}
