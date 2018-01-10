package com.beidouchain.explorer.entity;

public class Vout {
	
	private String address;
	
	private String scriptPubkey;
	
	private String desc;
	
	private int n;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getScriptPubkey() {
		return scriptPubkey;
	}

	public void setScriptPubkey(String scriptPubkey) {
		this.scriptPubkey = scriptPubkey;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}	
}
