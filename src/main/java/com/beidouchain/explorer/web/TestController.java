package com.beidouchain.explorer.web;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beidouchain.explorer.service.HomePageService;

@RestController
public class TestController {
	
	@Autowired
	private HomePageService service;
	
	@RequestMapping("/mem")
	public String test(){
		DecimalFormat df = new DecimalFormat("0.00") ;  
        //显示JVM总内存  
        long totalMem = Runtime.getRuntime().totalMemory()/1024/1024;  
        //显示JVM尝试使用的最大内存  
        long maxMem = Runtime.getRuntime().maxMemory()/1024/1024;  
        //空闲内存  
        long freeMem = Runtime.getRuntime().freeMemory()/1024/1024; 
		return "总内存：" + df.format(totalMem) + ",最大内存：" +df.format(maxMem) +" , 空闲内存：" +df.format(freeMem) ;
	}	
	@RequestMapping("/index11")
	public String homepage(){
		//System.out.println(service + service.getBeidouChain().getChainName());
		//service.getBeidouChain().setChainName("index");
		return "ok";
	}
	
	@RequestMapping("/index21")
	public String homepage2(){
		//System.out.println(service + service.getBeidouChain().getChainName());
		//service.getBeidouChain().setChainName("index2");
		return "ok2";
	}
}
