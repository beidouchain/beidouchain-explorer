package com.beidouchain.explorer.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidouchain.explorer.entity.Vin;
import com.beidouchain.explorer.entity.Vout;
import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.service.TransactionService;
import com.beidouchain.explorer.utils.DateUtils;
import com.beidouchain.explorer.utils.StringUtils;

@Controller
public class TransactionController {	
	
	@Autowired
	private TransactionService transService;
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/tx")
	public String tx(String txId,Model model){
		if(StringUtils.isBlank(txId)){
			return "error";
		}
		try {
			Map<String, Object> transMap = transService.getrawtransaction(txId);			
			long blocktime = (Integer)transMap.get("blocktime") * 1000l;
			model.addAttribute("blockhash", transMap.get("blockhash"));
			model.addAttribute("blocktime", DateUtils.formatDateTime(new Date(blocktime)));
			model.addAttribute("confirmations", transMap.get("confirmations"));			
			model.addAttribute("txid", txId);
			List<Vin> vinList =  new ArrayList<Vin>();
			List<Object> vins = (List<Object>)transMap.get("vin");
			for(Object o:vins){
				Vin vin = new Vin();
				Map<String,Object> map = (Map<String,Object>)o;
				if(map.get("coinbase") !=null){
					vin.setDesc("coinbase : " + map.get("coinbase"));
				}else{
					String preTxId = map.get("txid").toString();
					int preIndex = (Integer)map.get("vout");
					Map<String,Object> scriptSigMap = (Map<String,Object>)map.get("scriptSig");
					vin.setPreTxid(preTxId);
					vin.setPreIndex(preIndex);
					String asm = (String)scriptSigMap.get("asm");
					if(asm != null){
						vin.setScriptSig(asm.substring(0,20));
					}
					Map<String, Object> preVoutMap = transService.getrawtransaction(preTxId);
					List<Map<String,Object>> preVoutList = (List<Map<String,Object>>)preVoutMap.get("vout");
					Map<String,Object> preTargetVoutMap = preVoutList.get(preIndex);
					Map<String,Object> preTargetVoutscriptPubKey = (Map<String,Object>)preTargetVoutMap.get("scriptPubKey");
					String preTargetVoutType = preTargetVoutscriptPubKey.get("type").toString();
					if(preTargetVoutType.equalsIgnoreCase("pubkeyhash")){					
						String address = transService.getAddress(preTargetVoutMap);
						vin.setAddress(address);
						vin.setDesc(transService.getDesc(preTargetVoutMap));
					}
				}
				
				vinList.add(vin);
			}
			List<Vout> voutList = new ArrayList<Vout>();
			List<Map<String,Object>> vouts = (List<Map<String,Object>>)transMap.get("vout");
			for(Map<String,Object> voutMap:vouts){
				Map<String,Object> voutscriptPubKey = (Map<String,Object>)voutMap.get("scriptPubKey");
				String voutType = voutscriptPubKey.get("type").toString();	
				Vout vout = new Vout();
				vout.setN((Integer)voutMap.get("n"));
				vout.setScriptPubkey(voutscriptPubKey.get("asm").toString());
				if(voutType.equalsIgnoreCase("pubkeyhash")){					
					String address = transService.getAddress(voutMap);
					vout.setAddress(address);
					vout.setDesc(transService.getDesc(voutMap));
				}
				if(voutType.equalsIgnoreCase("nulldata")){	
					List<String> dataList = (List<String>)transMap.get("data");
					if(dataList != null && dataList.size() > 0){
						String hex = dataList.get(0);
						byte[] b = StringUtils.hexStringToBytes(hex);
						try {
							String metaData = new  String(b,"utf-8");
							vout.setDesc("Metadata:" + metaData);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Map<String, Object> issueMap = (Map<String, Object>)transMap.get("issue");
					if(issueMap != null && issueMap.size() > 0){
						StringBuilder sb = new StringBuilder();
						sb.append("<div class=\"panel panel-default panel-danger\">");
						sb.append("<div class=\"panel-body\" style=\"margin-bottom: -20px;\">");
						sb.append("New Issuance Metadata:");
						sb.append("<table class=\"table table-bordered table-condensed\">");	
						sb.append("<tr>");	
						sb.append("<td>Asset name</td>");	
						sb.append("<td>" + issueMap.get("name") + "</td>");	
						sb.append("</tr>");
						sb.append("<tr>");	
						sb.append("<td>Open to follow-on issuance</td>");	
						sb.append("<td>" + issueMap.get("open") + "</td>");	
						sb.append("</tr>");	
						sb.append("<tr>");	
						sb.append("<td>Quantity multiple</td>");	
						sb.append("<td>" + issueMap.get("multiple") + "</td>");	
						sb.append("</tr></table></div></div>");
						vout.setDesc(sb.toString());
					}
					
					List<Map<String,Object>> voutInputcacheList = (List<Map<String,Object>>)voutMap.get("inputcache");
					if(voutInputcacheList!= null && voutInputcacheList.size() > 0){
						StringBuilder sb = new StringBuilder();
						sb.append("<div class=\"panel panel-default panel-danger\">");
						sb.append("<div class=\"panel-body\" style=\"margin-bottom: -20px;\">");
						sb.append("Input Cache:");
						sb.append("<table class=\"table table-bordered table-condensed\">");	
						for(Map<String, Object> inputcacheMap:voutInputcacheList){
							sb.append("<tr>");	
							sb.append("<td>" + inputcacheMap.get("vin") +"</td>");	
							sb.append("<td>" + inputcacheMap.get("asm") + "</td>");	
							sb.append("</tr>");
						}
						sb.append("</table></div></div>");
						vout.setDesc(sb.toString());
					}
												
				}
				voutList.add(vout);
			}
			model.addAttribute("vin", vinList);
			model.addAttribute("vout",voutList);
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "tx";
	}
	
	
	@RequestMapping("/txjson")
	@ResponseBody
	public Map<String, Object> txJson(String txId){
		
		Map<String, Object> retMap = null;
		try {
			retMap = transService.getrawtransaction(txId);
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retMap;
	}
	
	@RequestMapping("/txraw")
	@ResponseBody
	public String txraw(String txId){
		
		String result = null;
		try {
			result = transService.gethextransaction(txId);
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
