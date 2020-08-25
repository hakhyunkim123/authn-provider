package com.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SLC_TKN_VALID_INFO")
public class TokenVertifyInfo {
	@Column(name="TKN_VALID_INFO_SEQ_NO")
	private long seq;
	
	@Column(name="ISS")
	private String issuer;	
	
	@Column(name="SUB")
	private String subject;
	
	@Column(name="CLIENT_ID")
	private String clientId;
	
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
	
}
