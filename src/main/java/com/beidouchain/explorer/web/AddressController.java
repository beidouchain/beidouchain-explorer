package com.beidouchain.explorer.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beidouchain.explorer.entity.Asset;
import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.service.AddressService;
import com.beidouchain.explorer.service.AssetService;
import com.beidouchain.explorer.service.PermissionsService;
import com.beidouchain.explorer.utils.StringUtils;

@Controller
public class AddressController {	
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private PermissionsService  permissionsService;
	
	@Autowired
	private AssetService assetService;
	
	@RequestMapping("/address")
	public String streams(String address,Model model){
		
		if(StringUtils.isBlank(address)){
			return "error";
		}
		
		try {
			model.addAttribute("address", address);
			model.addAttribute("plist", permissionsService.listpermissions(address));
			addressService.importaddress(address);
			List<Map<String,Object>> list = assetService.listassets();
			List<Asset> assets = addressService.getaddressbalances(address);
			for(Asset asset : assets){
				for(Map<String,Object> map:list){
					if(asset.getName().equalsIgnoreCase(String.valueOf(map.get("name")))){
						asset.setUnits((Double)map.get("units"));
					}
				}
			}
			model.addAttribute("assets", assets);
		} catch (BeidouchainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "address";
	}		
}
