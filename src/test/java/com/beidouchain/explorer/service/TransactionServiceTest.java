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
public class TransactionServiceTest {
	@Autowired
	private TransactionService transService;
	@Test
	public void test1() {
		try {
			System.out.println(transService.getrawtransaction("85eaa3708632ad850b79c4a942e91dda94af8590173f9c78979f9f0ac5430ad6"));
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}
