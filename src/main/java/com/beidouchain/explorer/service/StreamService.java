package com.beidouchain.explorer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beidouchain.explorer.exception.BeidouchainException;

@Service
public class StreamService extends BaseService{	
	
	
	@SuppressWarnings("unchecked")
	public List<Object> liststreams() throws BeidouchainException{
		
		String method  = "liststreams";
		List<Object> params = new ArrayList<Object>();
		params.add("*");
		params.add(true);
		Object retObj = execute(method, params);
		if(retObj != null && verifyInstance(retObj, List.class)){
			return ((List<Object>)retObj);
		}
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Object> liststreams(String streamName) throws BeidouchainException{
		
		String method  = "liststreams";
		List<Object> params = new ArrayList<Object>();
		params.add(streamName);
		params.add(true);
		Object retObj = execute(method, params);
		if(retObj != null && verifyInstance(retObj, List.class)){
			return ((List<Object>)retObj);
		}
		return null;
	}	
}