package com.beidouchain.explorer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beidouchain.explorer.StartExplorer;
import com.beidouchain.explorer.exception.BeidouchainException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=StartExplorer.class)
//@WebAppConfiguration
public class HomePageServiceTest {
	
	@Autowired
	private HomePageService service;
	
	@Autowired
	private BlockService blockService;
	
	@Test
	public void test1() throws BeidouchainException {
//		Map<String, Object> transMap = blockService.getblock("722", 4);
//		service.AddTransaction(transMap);
	}	
	
	@Test
	public void test2() {
		long start = System.currentTimeMillis();
		Object[] transactionArray = service.getTransactionArray();
//		for(Object o : transactionArray){
//			Transaction transaction = (Transaction)o;
//			System.out.println(transaction);
//		}
		long end = System.currentTimeMillis();
		System.out.println(end -start);
	}	

}
