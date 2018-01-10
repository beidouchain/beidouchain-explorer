package com.beidouchain.explorer.entity;

public class Vin {
	
	private String preTxid;
	
	private int preIndex;
	
	private String address;
	
	private String scriptSig;
	
	private String desc;

	public String getPreTxid() {
		return preTxid;
	}

	public void setPreTxid(String preTxid) {
		this.preTxid = preTxid;
	}

	public int getPreIndex() {
		return preIndex;
	}

	public void setPreIndex(int preIndex) {
		this.preIndex = preIndex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getScriptSig() {
		return scriptSig;
	}

	public void setScriptSig(String scriptSig) {
		this.scriptSig = scriptSig;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}	
}
