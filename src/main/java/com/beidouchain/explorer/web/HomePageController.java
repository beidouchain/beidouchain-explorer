package com.beidouchain.explorer.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.service.ChainService;
import com.beidouchain.explorer.service.HomePageService;
import com.beidouchain.explorer.utils.StringUtils;

@Controller
public class HomePageController {	
	
	@Autowired
	private HomePageService  homePageService;
	
	@Autowired
	private ChainService chainService;
	
	@RequestMapping("/")
	public String homepage(Model model){
		model.addAttribute("homePage", homePageService.getHomePage());	
		List<Object> transList  = Arrays.asList(homePageService.getTransactionArray());
		Collections.reverse(transList);
		model.addAttribute("transList", transList);
		return "index";
	}	
	
	@RequestMapping("/updateHomePage")
	public String updateHomePage(Model model){
		model.addAttribute("homePage", homePageService.getHomePage());	
		List<Object> transList  = Arrays.asList(homePageService.getTransactionArray());
		Collections.reverse(transList);
		model.addAttribute("transList", transList);
		return "updateHomePage";
	}	
	
	
	@RequestMapping("/chain")
	public String chain(Model model){
		
		try {
			model.addAttribute("homePage", homePageService.getHomePage());
			model.addAttribute("chainMap", chainService.getInfo());
			model.addAttribute("chainparamMap", chainService.getBlockChainParams());
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "chain";
	}
	
	@RequestMapping("/search")
	public String search(String str,Model model){
		
		//主页
		if(StringUtils.isBlank(str)){
			return "redirect:/";
		}
		
		//主链名字
		if(homePageService.getHomePage().getChainName().equalsIgnoreCase(str)){
			return "redirect:/";
		}
		
		//区块高度
		if(StringUtils.isNumeric(str)){
			return "redirect:block?hash=" +str;
		}
		
		//地址长度
		if(str.length() == 38){
			return "redirect:address?address=" +str;
		}
		
		//交易
		if(str.length() == 64){
			return "redirect:tx?txId=" +str;
		}
		return "redirect:/";
	}
}
