package com.mfangsoft.zhuangjialong.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月25日
* 
*/

public  class MySolrDoc{
    
	
	public static List<Map<String, Object>> toJson(SolrDocumentList documentList){
	
	  List<Map<String, Object>> products = new ArrayList<>();
		 for (SolrDocument solrDocument : documentList) {
			 Map<String, Object> result =new HashMap<>();
			 Collection<String> fieldNames = solrDocument.getFieldNames();
			 for (String fieldName : fieldNames) {
				 result.put(fieldName, solrDocument.get(fieldName));
			}
			 products.add(result);
	}
		 return products;
}
	
	public static Map<String, Object> toOneJson(SolrDocumentList documentList){
		
		
				 Map<String, Object> result =new HashMap<>();
				 Collection<String> fieldNames = documentList.get(0).getFieldNames();
				 for (String fieldName : fieldNames) {
					 result.put(fieldName, documentList.get(0).get(fieldName));
				}
			 return result;
	}
	
}