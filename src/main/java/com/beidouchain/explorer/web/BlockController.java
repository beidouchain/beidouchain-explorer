package com.beidouchain.explorer.web;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.service.BlockService;
import com.beidouchain.explorer.service.HomePageService;
import com.beidouchain.explorer.utils.DateUtils;

@Controller
public class BlockController {	
	
	@Autowired
	private BlockService blockService;
	
	@Autowired
	private HomePageService  homePageService;
	
	@RequestMapping("/block")
	public String block(String hash,Model model){
		try {
			Map<String, Object> blockMap = blockService.getblock(hash, 1);
			model.addAttribute("map", blockMap);
			long time = (Integer)blockMap.get("time") * 1000l;
			model.addAttribute("time", DateUtils.formatDateTime(new Date(time)));	
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "block";
	}
	
	@RequestMapping("/blocks")
	public String blocks(@RequestParam(defaultValue="0") int end,Model model){
		
		try {
			
			int blockCount = homePageService.getHomePage().getChainBlockHeight();
			
			if(end < 1){
				end = blockCount;
			}
			
			int start = end - 19;
			if(start < 0){
				start = 0;
			}
			List<Map<String, Object>> blockList = blockService.listblocks(start, end, false);
			Collections.reverse(blockList);
			for(Map<String, Object> map : blockList){
				long time = (Integer)map.get("time") * 1000l;
				map.put("time", DateUtils.formatDateTime(new Date(time)));
				map.put("age",DateUtils.pastDays(new Date(time)));			
			}
			model.addAttribute("end", end);	
			model.addAttribute("blockList", blockList);	
			model.addAttribute("blockCount", blockCount);	
			
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "blocks";
	}	
	
	@RequestMapping("/test")
	public String test(Model model){
		int time = 1506560517;			
		model.addAttribute("time", time);	
		return "test";
	}	
}
