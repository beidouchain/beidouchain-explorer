package com.beidouchain.explorer.entity;

import java.util.List;
import java.util.UUID;

//	String json = "{\"jsonrpc\": \"1.0\", \"id\":\"uuid\", \"method\": \"createkeypairs\", \"params\": [] }";
public class BeidouChainRPCParams{
	
	private String jspnrpc;
	
	private String id;
	
	private String method;
	
	private List<Object> params;
	

	public BeidouChainRPCParams() {
		jspnrpc = "1.0";
		id = UUID.randomUUID().toString();
	}

	public String getJspnrpc() {
		return jspnrpc;
	}

	public void setJspnrpc(String jspnrpc) {
		this.jspnrpc = jspnrpc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
}