package com.mfangsoft.zhuangjialong.app.DIYdesign.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.DIYdesign.model.ClassBrandModel;
import com.mfangsoft.zhuangjialong.app.DIYdesign.service.DIYService;
import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.common.utils.HttpClientUtil;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年7月21日
 * 
 */
@Service
public class DIYServiceImpl extends SuperCore implements DIYService {

	@Autowired
	BuildClassEntityMapper buildClassEntityMapper;
	@Autowired
	BrandNewProductEntityMapper brandProductEntityMapper;

	@Override
	public List<ClassBrandModel> getDIYClassBrand(Map<String, String> param) {
		// 获取品类及品牌数组
		List<ClassBrandModel> list = buildClassEntityMapper.selectDIYClassesByRegion(param);

		// 封装魔方请求参数
		StringBuffer brandStr = new StringBuffer();
		for (ClassBrandModel classBrandModel : list) {
			for (Brand brand : classBrandModel.getBrands()) {
				brandStr.append(brand.getBrand_name() + ",");
			}
		}
		if (brandStr.length() == 0) {
			return list;
		}

		Map<String, String> map = new HashMap<>();
		map.put("brandNameStr", brandStr.toString().substring(0, brandStr.toString().length() - 1));
		
		//请求魔方
		String result = null;
		try {
			result = HttpClientUtil.sendPost("http://101.200.207.12:8999/api/brand/getBrandID", map);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//转化请求结果
		ObjectMapper json = new ObjectMapper();
		List<Map<String, Object>> readValue;
		List<ClassBrandModel> brandModels = new ArrayList<>();
		try {
			readValue = (List<Map<String, Object>>) json.readValue(result, List.class);
			
			//过滤
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).getBrands().size(); j++) {
					int m = 0;
					for (Map<String, Object> mofangBrandModel : readValue) {
						if (mofangBrandModel.get("brandName") == null) {
							continue;
						}
						String brandName = mofangBrandModel.get("brandName").toString();
						if (brandName.equals(list.get(i).getBrands().get(j).getBrand_name())) {
							m = 1;
							break;
						}
					}
					if (m == 0) {
						list.get(i).getBrands().remove(j);
					}
				}
				
				if(list.get(i).getBrands().size()!=0){
					brandModels.add(list.get(i));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return brandModels;
	}

	@Override
	public Long getProductIdByModel(Map<String, String> param) {

		/*
		 * super.initProduct(); query.setFields("product_id");
		 * query.setQuery("*:*");
		 * query.addFilterQuery("region_code:*"+param.get("region_code")+"*");
		 * query.addFilterQuery("model:\""+param.get("model")+"\"");
		 * query.addFilterQuery("brand_name:\""+param.get("brand_name")+"\"");
		 * query.addFilterQuery("state:1"); try { response = solr.query(query);
		 * } catch (SolrServerException | IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * if(response.getResults().getNumFound()>0){ Map<String, Object>
		 * oneJson = MySolrDoc.toOneJson(response.getResults()); return
		 * oneJson.get("product_id").toString(); } return null;
		 */

		return brandProductEntityMapper.selectProductIdByModel(param);

	}

}
