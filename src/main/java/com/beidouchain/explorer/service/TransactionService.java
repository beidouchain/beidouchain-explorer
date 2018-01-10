package com.beidouchain.explorer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.utils.StringUtils;

@Service
public class TransactionService extends BaseService{
		
	@SuppressWarnings("unchecked")
	public Map<String, Object>  gettxoutsetinfo() throws BeidouchainException{
		
		String method  = "gettxoutsetinfo";		
		Object retObj = execute(method, null);
		if(retObj != null && verifyInstance(retObj, Map.class)){
			Map<String, Object>  map = (Map<String, Object>)retObj;
			return map;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getrawtransaction (String txid) throws BeidouchainException{
		String method = "getrawtransaction";
		List<Object> params = new ArrayList<Object>();
		params.add(txid);
		params.add(1);
		Object retObj = execute(method, params);
		if(retObj != null && verifyInstance(retObj, Map.class)){
			Map<String, Object>  map = (Map<String, Object>)retObj;
			return map;
		}
		return null;
	}	
	
	public String gethextransaction (String txid) throws BeidouchainException{
		String method = "getrawtransaction";
		List<Object> params = new ArrayList<Object>();
		params.add(txid);
		params.add(0);
		Object retObj = execute(method, params);		
		return retObj.toString();
	}	
	
	@SuppressWarnings("unchecked")
	public String getDesc(Map<String, Object> map){	
		
		StringBuilder sb = new StringBuilder();		
		List<Map<String, Object>> voutAssetList = (List<Map<String, Object>>)map.get("assets");
		if(voutAssetList != null){
			for(Map<String, Object> assetMap:voutAssetList){
				if("issuefirst".equalsIgnoreCase(assetMap.get("type").toString())){
					sb.append("Issue   " + assetMap.get("raw") + " raw units of new asset " + assetMap.get("name"));
				}else{

					sb.append(assetMap.get("qty") + " units of asset " + assetMap.get("name"));
				}
			}
		}
		List<Map<String, Object>> voutPermissionList = (List<Map<String, Object>>)map.get("permissions");
		if(voutPermissionList != null && voutPermissionList.size() >0){
			Map<String, Object> voutPermissionMap = voutPermissionList.get(0);
			long startBlock = 0; 
			Object sk = voutPermissionMap.get("startblock");
			if(Integer.class.isInstance(sk)){
				startBlock = (Integer)sk;
			}
			if(Long.class.isInstance(sk)){
				startBlock = (Long)sk;
			}
			long endBlock = 0;
			Object ek = voutPermissionMap.get("endblock");
			if(Integer.class.isInstance(ek)){
				endBlock = (Integer)ek;
			}
			if(Long.class.isInstance(ek)){
				endBlock = (Long)ek;
			}
			if(startBlock == endBlock){
				sb.append("Revoke permission to ");
			}else{
				sb.append("Grant permission to ");
			}
			if((Boolean)voutPermissionMap.get("connect")){
				sb.append("	connect ");
			}
			if((Boolean)voutPermissionMap.get("send")){
				sb.append("	send ");
			}
			if((Boolean)voutPermissionMap.get("receive")){
				sb.append("	receive ");
			}
			if((Boolean)voutPermissionMap.get("create")){
				sb.append("	create ");
			}
			if((Boolean)voutPermissionMap.get("connect")){
				sb.append("	connect ");
			}
			if((Boolean)voutPermissionMap.get("issue")){
				sb.append("	issue ");
			}
			if((Boolean)voutPermissionMap.get("admin")){
				sb.append("	admin ");
			}
			if((Boolean)voutPermissionMap.get("activate")){
				sb.append("	activate ");
			}
		}		
		return sb.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public String getAddress(Map<String, Object> map){
		
		StringBuilder sb = new StringBuilder();
		Map voutscriptPubKey = (Map)map.get("scriptPubKey");
		String address =((List)voutscriptPubKey.get("addresses")).get(0).toString();
		if(StringUtils.isBlank(address)){
			address = "None";
		}
		sb.append(address);
		return sb.toString();
	}
}