package com.beidouchain.explorer.entity;

import java.util.HashMap;
import java.util.Map;

public class Asset {
	
	private String name;
	
	private String assetref;
	
	private double qty;	
	
	private double units;
	
	public String getAssetref() {
		return assetref;
	}
	public void setAssetref(String assetref) {
		this.assetref = assetref;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	
	public Map<String, Double> toParamMap(){
		Map<String, Double> map = new HashMap<String, Double>();
		map.put(name, qty);
		return map;
	}
	public double getUnits() {
		return units;
	}
	public void setUnits(double units) {
		this.units = units;
	}
	
	public double getIssueraw() {
		return qty/units;
	}
	
	
}