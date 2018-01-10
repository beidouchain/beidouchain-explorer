package com.beidouchain.explorer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beidouchain.explorer.entity.Asset;
import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.utils.JsonMapper;

@Service
public class AddressService extends BaseService{
	
		
	public List<Asset> getaddressbalances(String address) throws BeidouchainException{
		String method  = "getaddressbalances";
		List<Object> params = new ArrayList<Object>();
		params.add(address);
		Object rtnObj = null;
		rtnObj = execute(method, params);
		List<Asset> addressBalanceAssets = new ArrayList<Asset>();
		if(verifyInstance(rtnObj, List.class)){			
			String str = JsonMapper.getInstance().toJson(rtnObj);
			addressBalanceAssets = JsonMapper.getInstance().fromJson(str, JsonMapper.getInstance().createCollectionType(List.class, Asset.class));
		}		
		return addressBalanceAssets;
	}	
	
	public void importaddress(String address) throws BeidouchainException{		
		String method  = "importaddress";
		List<Object> params = new ArrayList<Object>();
		params.add(address);
		execute(method, params);
	}
}