package com.mfangsoft.zhuangjialong.app.newproductcore.mapper;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年8月10日
* 
*/

public class SuperCore {
        public static SolrClient solr ;
        public static QueryResponse response;
        public static SolrQuery query ;
         //初始化product_core
         public static void initProduct(){
	    	 solr = new HttpSolrClient.Builder(SysConstant.product_core).build();
	    	 response=null;
		    query = new SolrQuery();
         }
         
}
