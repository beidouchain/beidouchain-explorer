package com.beidouchain.explorer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RPCServerConfig {
	
	@Value("${rpcIp}")
	private String rpcIp;
	@Value("${rpcPort}")
	private String rpcPort;
	@Value("${rpcUser}")
	private String rpcUser;
	@Value("${rpcPassword}")
	private String rpcPassword;
	

	public String getRpcIp() {
		return rpcIp;
	}

	public void setRpcIp(String rpcIp) {
		this.rpcIp = rpcIp;
	}	

	public String getRpcPort() {
		return rpcPort;
	}

	public void setRpcPort(String rpcPort) {
		this.rpcPort = rpcPort;
	}

	public String getRpcUser() {
		return rpcUser;
	}

	public void setRpcUser(String rpcUser) {
		this.rpcUser = rpcUser;
	}

	public String getRpcPassword() {
		return rpcPassword;
	}

	public void setRpcPassword(String rpcPassword) {
		this.rpcPassword = rpcPassword;
	}	
}
