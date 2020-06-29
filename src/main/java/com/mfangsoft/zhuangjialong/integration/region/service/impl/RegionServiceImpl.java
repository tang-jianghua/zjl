package com.mfangsoft.zhuangjialong.integration.region.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.integration.region.mapper.RegionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;
import com.mfangsoft.zhuangjialong.integration.region.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionEntityMapper entityMapper;
	@Override
	public List<RegionEntity> getProvince() {
		// TODO Auto-generated method stub
		return entityMapper.select();
	}
	
	
	@Override
	public List<RegionEntity> getCity(String code) {
		// TODO Auto-generated method stub
		return entityMapper.selectCity(code);
	}

	@Override
	public List<RegionEntity> getCounty(String code) {
		// TODO Auto-generated method stub
		return entityMapper.selectCounty(code);
	}


	@Override
	public Map<String, RegionEntity> getName(String code) {
		// TODO Auto-generated method stub
		
		Map<String, RegionEntity> map= new HashMap<>();
		
		if(isProvince(code)){
			map.put("province", entityMapper.selectNameByCode(code));
			
		}else if(isCity(code)){
			map.put("province", entityMapper.selectProvinceNameByCode(code));
			map.put("city", entityMapper.selectNameByCode(code));
			
		}else{
			
			map.put("province", entityMapper.selectProvinceNameByCode(code));
			map.put("city", entityMapper.selectCityNameByCode(code));
			map.put("area", entityMapper.selectNameByCode(code));
		}
		
		
		return map;
	}
	

	@Override
	public String getWholeAddress(String code) {
		
		String region = null;
		
		if(isProvince(code)){
			region = entityMapper.selectRegionByCode(code);
			
		}else if(isCity(code)){
			String provinceCode = code.substring(0,2)+"0000";
			String province = entityMapper.selectRegionByCode(provinceCode);
			if("110000".equals(provinceCode)|| "120000".equals(provinceCode)|| "310000".equals(provinceCode)|| "500000".equals(provinceCode)){
				region=province;
			}else{
			String city = entityMapper.selectRegionByCode(code);
			region = province + city; 
			}
		}else{
			String provinceCode = code.substring(0,2)+"0000";
			String province = entityMapper.selectRegionByCode(provinceCode);
			String area = entityMapper.selectRegionByCode(code);
			if("110000".equals(provinceCode)|| "120000".equals(provinceCode)|| "310000".equals(provinceCode)|| "500000".equals(provinceCode)){
				region=province+area;
			}else{
				String cityCode = code.substring(0, 4)+"00";
				String city = entityMapper.selectRegionByCode(cityCode);
				region = province + city + area;
		}
		
		}
		return region;
	}
	
	// 判断是否省或者直辖市
	private boolean isProvince(String code) {
		String last = code.substring(2);
		if ("0000".equalsIgnoreCase(last)) {
			return true;
		}
		return false;

	}

	// 判断是否地级市
	private boolean isCity(String code) {
		String last = code.substring(4);
		if ("00".equalsIgnoreCase(last)) {
			return true;
		}
		return false;
	}


	@Override
	public String selectreginName(String region_code) {
		// TODO Auto-generated method stub
		
		String str[]=region_code.split(",");
		
		if(str.length>1){
			StringBuffer bu = new StringBuffer();
			
			bu.append(entityMapper.selectreginName(str[0])+",");
			bu.append(entityMapper.selectreginName(str[1])+",");
			bu.append(entityMapper.selectreginName(region_code.substring(12, region_code.length())));
			
			return bu.toString();
		}else{
			return entityMapper.selectreginName(region_code);
		}
		
		
		
	}
}
