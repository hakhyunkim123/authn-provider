package com.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "slc-client")
public class Client {
	@Column(name="CLIENT_SEQ_NO")
	private long seq;
	
	@Column(name="CLIENT_ID")
	private String clientId;
	
	@Column(name="CLIENT_SECRET")
	private String clientSecret;
	
	@Column(name="REDIRECT_URL")
	private String redirectUrl;
	
	@Column(name="JOB_NM")
	private String jobName;
	
	@Column(name="DNS_ADDR")
	private String dnsAddr;
	
	@Column(name="IP_ADDR")
	private String ipAddr;
	
	@Column(name="PORT")
	private String port;
	
	@Column(name="GRANT_TYPE")
	private String grantType;
	
	@Column(name="CLIENT_DESC")
	private String clientDesc;
	
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
