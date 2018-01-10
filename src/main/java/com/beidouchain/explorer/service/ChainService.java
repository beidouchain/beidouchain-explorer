package com.beidouchain.explorer.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.beidouchain.explorer.exception.BeidouchainException;

@Service
public class ChainService extends BaseService{	

	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getInfo() throws BeidouchainException{
		
		String method  = "getinfo";
		Object retObj = execute(method, null);
		if(retObj != null && verifyInstance(retObj, Map.class)){
			Map<String, Object>  map = (Map<String, Object>)retObj;
			return map;
		}
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getBlockChainParams() throws BeidouchainException{
		
		String method  = "getblockchainparams";
		Object retObj = execute(method, null);
		if(retObj != null && verifyInstance(retObj, Map.class)){
			Map<String, Object>  map = (Map<String, Object>)retObj;
			return map;
		}
		return null;
	}	
}