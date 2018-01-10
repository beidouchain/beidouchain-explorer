package com.beidouchain.explorer.entity;

import java.util.Date;

import com.beidouchain.explorer.utils.DateUtils;

public class HomePage {
	
	private String chainName; //区块链名字
	
	private int chainBlockHeight; //区块链高度
	
	private int port;  //区块链连接端口
	
	private int txoutNum;
	
	private int assetNum;
	
	private int streamNum;
	
	private int peerNum; //连接节点数量
	
	private long started;

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}			
	
	public int getChainBlockHeight() {
		return chainBlockHeight;
	}

	public void setChainBlockHeight(int chainBlockHeight) {
		this.chainBlockHeight = chainBlockHeight;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getTransactionsNum() {
		return getChainBlockHeight() + getTxoutNum();
	}

	public int getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(int assetNum) {
		this.assetNum = assetNum;
	}

	public int getStreamNum() {
		return streamNum;
	}

	public void setStreamNum(int streamNum) {
		this.streamNum = streamNum;
	}

	public int getPeerNum() {
		return peerNum;
	}

	public void setPeerNum(int peerNum) {
		this.peerNum = peerNum;
	}
	
	public void setStarted(long started) {
		this.started = started;
	}
	
	public String getFormatStarted(){
		return DateUtils.formatDate(new Date(started));
	}
	
	public String getFormatAge(){
		return DateUtils.pastDays(new Date(started),".#");
	}	
		

	public int getTxoutNum() {
		return txoutNum;
	}

	public void setTxoutNum(int txoutNum) {
		this.txoutNum = txoutNum;
	}

	@Override
	public String toString() {
		return "HomePage [chainName=" + chainName + ", chainBlockHeight=" + chainBlockHeight + ", port=" + port
				+ ", transactionsNum=" + getTransactionsNum() + ", assetNum=" + assetNum + ", streamNum=" + streamNum
				+ ", peerNum=" + peerNum + ", started=" + getFormatStarted() + " ,ages = "+ getFormatAge() +"]";
	}

	public static void main(String[] args) {
//		Date date = new Date(1506559868000l);
//		long currute = System.currentTimeMillis();
//		System.out.println(date);
//		float diff = currute - 1506559868000l;
//		System.out.println(diff);
//		float day = 24*60*60*1000f;
//		System.out.println(diff/day);
	}
}
