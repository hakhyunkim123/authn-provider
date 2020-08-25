package com.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="SLC_REFRESH_TKN_INFO")
public class RefreshToken {
	@Column(name="REFRESH_TKN_SEQ_NO")
	private long seq;
	
	@Column(name="USR_ID")
	private String userId;
	
	@Column(name="CLIENT_ID")
	private String clientId;
	
	@Column(name="REFRESH_TKN")
	private String refreshToken;

	@Column(name="REG_DT")
	private String issueDate;
}
