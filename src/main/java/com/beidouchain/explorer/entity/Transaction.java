package com.beidouchain.explorer.entity;

import java.util.Date;
import java.util.Set;

import com.beidouchain.explorer.utils.DateUtils;

public class Transaction {

	private String txid;
	
	private Set<String> type;
	
	private int confirmation;
	
	private int createTime;

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public Set<String> getType() {
		return type;
	}

	public void setType(Set<String> type) {
		this.type = type;
	}

	public int getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(int confirmation) {
		this.confirmation = confirmation;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
	public String getFormatAge(){
		
		Date date = new Date(createTime * 1000l);
		
		long pastMinutes = DateUtils.pastMinutes(date);
				
		if(pastMinutes < 60){
			return " < " + pastMinutes + " minutes";
		}
		
		long pastHours = DateUtils.pastHour(date);
		
		if(DateUtils.pastHour(date) < 24){
			return " < " + pastHours + " hours ";
		}
		
		return DateUtils.pastDays(new Date(createTime * 1000l),"#") + " days";
		
	}	

	@Override
	public String toString() {
		return "Transaction [txid=" + txid + ", type=" + type + ", confirmation=" + confirmation + ", createTime="
				+ createTime + "]";
	}	
	
	
}