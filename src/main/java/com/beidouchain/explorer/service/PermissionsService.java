package com.beidouchain.explorer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.beidouchain.explorer.exception.BeidouchainException;

@Service
public class PermissionsService extends BaseService{	

	
	@SuppressWarnings("rawtypes")
	public List<String> listpermissions(String address) throws BeidouchainException{		
		String method = "listpermissions";	
		List<Object> params = new ArrayList<Object>();
		params.add("all");
		params.add(address);		
		Object rtnObj = execute(method, params);
		List<String> pList = new ArrayList<String>();
		if(rtnObj!= null && verifyInstance(rtnObj, List.class) && ((List)rtnObj).size() > 0){
			@SuppressWarnings("unchecked")
			List<Map<String, String>> rtnList = (List<Map<String, String>>)rtnObj;
			for(Map<String, String> map : rtnList){
				pList.add(map.get("type"));
			}
		}
		return pList;
	}
}