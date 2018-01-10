package com.beidouchain.explorer.entity;

import java.util.Map;

public class BeidouChainRPCResult{
	
	private Object result;
	
	private Map<String, Object> error;
	
	private String id;
	
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Map<String, Object> getError() {
		return error;
	}
	public void setError(Map<String, Object> error) {
		this.error = error;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}