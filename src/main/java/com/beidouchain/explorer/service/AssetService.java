package com.beidouchain.explorer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.beidouchain.explorer.exception.BeidouchainException;

@Service
public class AssetService extends BaseService{	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> listassets() throws BeidouchainException{
		String method  = "listassets";		
		Object retObj = execute(method, null);
		if(retObj != null && verifyInstance(retObj, List.class)){
			List<Map<String,Object>>  list = (List<Map<String,Object>>)retObj;
			return list;
		}
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> assetref(String ref) throws BeidouchainException{
		String method  = "listassets";		
		List<Object> params = new ArrayList<Object>();
		params.add(ref);
		Object retObj = execute(method, params);
		if(retObj != null && verifyInstance(retObj, List.class)){
			List<Map<String,Object>>  list = (List<Map<String,Object>>)retObj;
			return list;
		}
		return null;
	}	
}