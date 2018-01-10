package com.beidouchain.explorer.service;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beidouchain.explorer.StartExplorer;
import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.utils.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=StartExplorer.class)
//@WebAppConfiguration
public class BlockServiceTest {
	@Autowired
	private BlockService service;
	@Test
	public void test1() {
		try {
			System.out.println(service.getblock("722", 4));
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	@Test
	public void test2(){
		String str = "53504b624730450221009b336c5626af60e258c7b59e33c16b0090d3672a895c5059501f31624465d03602205ee95fa060fa137d9775189757f19c3ee6ffd6974ea96f343be29c8b500ff26a022103a1e8e3744861cd3ea61137af4dc6f0d9a8e2f44534333cd984aa8aabb7d7df23";
		byte[] b = StringUtils.hexStringToBytes(str);
		try {
			System.out.println(new String(b,"gbk"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
