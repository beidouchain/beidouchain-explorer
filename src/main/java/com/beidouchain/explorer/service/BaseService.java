package com.beidouchain.explorer.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidouchain.explorer.exception.BeidouchainException;

@Service
public class BaseService {
	

	protected Logger logger = LoggerFactory.getLogger(getClass());	
	
	@Autowired
	private HttpService service;
	
	public Object execute(String method,List<Object> params) throws BeidouchainException {		
		return service.execute(method, params);
	}

	@SuppressWarnings("rawtypes")
	protected boolean verifyInstance(Object obj, Class TheClass) {
		return TheClass.isInstance(obj);
	}		
}
