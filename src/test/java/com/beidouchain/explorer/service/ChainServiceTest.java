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
public class ChainServiceTest {
	@Autowired
	private ChainService service;
	@Test
	public void test1() {
		try {
			System.out.println(service.getInfo());
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}
