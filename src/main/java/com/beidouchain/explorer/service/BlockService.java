package com.beidouchain.explorer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.beidouchain.explorer.entity.Transaction;
import com.beidouchain.explorer.exception.BeidouchainException;

@Service
public class BlockService extends BaseService{
		
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getblock(String heightOrHash,int verbose) throws BeidouchainException{
		
		String method  = "getblock";
		List<Object> params = new ArrayList<Object>();
		params.add(heightOrHash);
		params.add(verbose);
		Object retObj = execute(method, params);
		if(retObj != null && verifyInstance(retObj, Map.class)){
			Map<String, Object>  map = (Map<String, Object>)retObj;
			return map;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>>  listblocks(int start,int end,boolean verbose) throws BeidouchainException{
		
		String method  = "listblocks";
		List<Object> params = new ArrayList<Object>();
		params.add(start + "-" + end);
		params.add(verbose);
		Object retObj = execute(method, params);
		if(retObj != null && verifyInstance(retObj, List.class)){
			return (List<Map<String,Object>>)retObj;
		}
		return null;
	}	
	
	public int  getblockcount()throws BeidouchainException{
		String method  = "getblockcount";		
		Object retObj = execute(method, null);
		return (Integer)retObj;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactionListByBlock(String heightOrHash,int verbose) throws BeidouchainException{		
		
		List<Transaction> transactionList  = new ArrayList<Transaction>();
		Map<String, Object> transMap = getblock(heightOrHash, verbose);
		if(transMap ==null){
			return transactionList;
		}
		
		int confirmations = 0;
		String txid = null;
		int createTime = 0;
		List<Object> data = null;
		
		if(verifyInstance(transMap.get("confirmations"), Integer.class)){
			confirmations = (Integer)transMap.get("confirmations");
		}
		
		if(verifyInstance(transMap.get("time"), Integer.class)){
			createTime = (Integer)transMap.get("time");
		}
		
		if(verifyInstance(transMap.get("tx"),List.class)){
			
			List<Map<String,Object>> txList = (List<Map<String,Object>>)transMap.get("tx");
			
			for(Map<String,Object> tx:txList){
				
				//处理vin vout
				if(verifyInstance(tx.get("vin"), List.class) && verifyInstance(tx.get("vout"), List.class)){
					
					List<Map<String,Object>> vinList  = (List<Map<String,Object>>)tx.get("vin");
					
					List<Map<String,Object>> voutList = (List<Map<String,Object>>)tx.get("vout");					
					
					txid = (String)tx.get("txid");	
					
					if(vinList.size() == 0){
						continue;
					}
					
					if(vinList.size() == 1 && vinList.get(0).get("coinbase") !=null){
						logger.info("txid:" + txid + ",vin:coinbase ignore");
						continue;
					}
				
					Transaction transaction = new Transaction();
					transaction.setTxid(txid);
					transaction.setConfirmation(confirmations);
					transaction.setCreateTime(createTime);
					Set<String> typeSet = new TreeSet<String>();
					for(Map<String,Object> vout:voutList){
						List<Object> assetList = (List<Object>)vout.get("assets");
						List<Object> permissionList = (List<Object>)vout.get("permissions");
						if(assetList != null && assetList.size() > 0){
							typeSet.add("Asset");
						}
						if(permissionList != null && permissionList.size() > 0){
							typeSet.add("Permissions");
						}
					}
					data = (List<Object>)tx.get("data");
					if(data != null && data.size() > 0){
						typeSet.add("Metadata");
					}
					Map<String,Object> createMap = (Map<String,Object>)tx.get("create");
					if(createMap != null && createMap.size() > 0){
						typeSet.add("create" + createMap.get("type"));
					}
					transaction.setType(typeSet);
					transactionList.add(transaction);
					logger.info("txid:" + txid + " add");
				}
			}
		}
		return transactionList;
	}
}