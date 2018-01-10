package com.beidouchain.explorer.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.beidouchain.explorer.entity.HomePage;
import com.beidouchain.explorer.entity.Transaction;
import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.service.AssetService;
import com.beidouchain.explorer.service.BlockService;
import com.beidouchain.explorer.service.ChainService;
import com.beidouchain.explorer.service.HomePageService;
import com.beidouchain.explorer.service.StreamService;
import com.beidouchain.explorer.service.TransactionService;

public class MyServiceInit implements ApplicationListener<ContextRefreshedEvent> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		long start = System.currentTimeMillis();
		System.out.println("start");
		
		ChainService chainService = event.getApplicationContext().getBean(ChainService.class);
		BlockService blockService = event.getApplicationContext().getBean(BlockService.class);
		StreamService streamService = event.getApplicationContext().getBean(StreamService.class);
		AssetService assetService = event.getApplicationContext().getBean(AssetService.class);
		TransactionService transactionService = event.getApplicationContext().getBean(TransactionService.class);
		HomePageService homePageService = event.getApplicationContext().getBean(HomePageService.class);
		HomePage homePage = homePageService.getHomePage();		
		
		try {
			Map<String, Object> map = chainService.getInfo(); 
			if(map != null){	
				
				homePage.setChainName(map.get("chainname").toString());
				
				if(Integer.class.isInstance(map.get("port"))){
					homePage.setPort((Integer)map.get("port"));
				}
				
				if(Integer.class.isInstance(map.get("blocks"))){
					homePage.setChainBlockHeight((Integer)map.get("blocks"));
				}
				
				if(Integer.class.isInstance(map.get("connections"))){
					homePage.setPeerNum((Integer)map.get("connections"));
				}
			}else{
				logger.warn("北斗链初始化失败！homepage 设置失败");
			}
			
			List<Map<String,Object>> assetList = assetService.listassets();
			if(assetList !=null){
				homePage.setAssetNum(assetList.size());
			}
			
			List<Object> streamList = streamService.liststreams();
			if(streamList !=null){
				homePage.setStreamNum(streamList.size());
			}
			
			Map<String, Object> genesisBlock = blockService.getblock("0", 1);
			if(genesisBlock != null && Integer.class.isInstance(genesisBlock.get("time"))){
				Integer time = (Integer)genesisBlock.get("time");
				homePage.setStarted(time * 1000l);
			}		

			Map<String, Object> txMap = transactionService.gettxoutsetinfo();
			if(txMap != null && Integer.class.isInstance(txMap.get("txouts"))){
				Integer txouts = (Integer)txMap.get("txouts");
				homePage.setTxoutNum(txouts);
			}
			
			//最近的十次交易
			int lastedBlock = homePage.getChainBlockHeight(); //本地最新的block
			List<Map<String,Object>> blockList = null;
			Map<String,Object> transMap = null;
			String blockHash = null;
			final int FTECH_NUM = 20; //一次取多少数据
			int startBlock = 0;
			int endBlock = lastedBlock;
			List<Transaction> transList = new ArrayList<Transaction>();	
				
		initBlock :	
			while(true){
				
				if(endBlock <= 0){
					break;
				}
				
				startBlock = (endBlock - FTECH_NUM);
				if(startBlock < 0){
					startBlock = 0;
				}				
				
				blockList = blockService.listblocks(startBlock, endBlock, false);//每次取20个区块		
				
				if(blockList == null){
					break;
				}
				
				for (int i = blockList.size(); i > 0; i--) {
					
					transMap = blockList.get(i - 1);					
					Object txCount = transMap.get("txcount");
					//忽略coinbase交易 block第一个交易为coinbase交易
					if(txCount != null && Integer.class.isInstance(txCount) && (Integer)txCount > 1){
						blockHash = transMap.get("hash").toString();
						List<Transaction> tempList = blockService.getTransactionListByBlock(blockHash, 4);
						if(tempList.size() > 0){
							transList.addAll(tempList);
						}					
						
						if(transList.size() >=10){
							logger.info("startBlock:" + startBlock + ",endBlock:" + endBlock + ",totalSuccess:" + transList.size());
							break initBlock;
						}
					}
				}	
				logger.info("startBlock:" + startBlock + ",endBlock:" + endBlock + ",totalSuccess:" + transList.size());
				endBlock = startBlock -1;
			}
			//老的数据最新插入
			Collections.reverse(transList);
			for(Transaction transaction : transList){
				homePageService.AddTransaction(transaction);
			}
		} catch (BeidouchainException e) {
			logger.error("北斗链初始化失败！" + e.getReason());
		} catch(Exception e){
			logger.error("北斗链初始化失败！" + e.getMessage());
		}
		long end = System.currentTimeMillis();
		System.out.println("end:" + (end-start));
	}


}
