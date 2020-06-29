package com.mfangsoft.zhuangjialong.app.unionpromotion.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.promotion.model.Promotion_type;
import com.mfangsoft.zhuangjialong.app.unionpromotion.service.UnionPromService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.MySolrDoc;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionCustomerMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月21日
* 
*/
@Service
public class UnionPromServiceImpl extends SuperCore implements UnionPromService{

	@Autowired
	UnionPromotionMapper unionPromotionMapper;
	
	@Autowired
	BrandEntityMapper brandEntityMapper;
	
	@Autowired
	UnionCustomerMapper unionCustomerMapper;
	
	@Autowired
	UnionProductMapper unionProductMapper;
	
	@Autowired
	BrandNewProductEntityMapper brandProductEntityMapper;
	
	@Override
	public List<UnionPromotion> selectUnionList(Map<String, String> map) {
		
		return	unionPromotionMapper.selectUnionList(map);
		
	}

	@Override
	public UnionPromotion selectUnionDetails(UnionCustomer param) {
		UnionPromotion unionPromotion = unionPromotionMapper.selectUnionDetails(param.getPromotion_id());
		
		if(unionPromotion != null){
			String images = unionPromotion.getImages();
			unionPromotion.setTopImages(images.split(","));
			unionPromotion.setImages(null);
			if(param.getCustomer_id() !=null && unionCustomerMapper.selectWitherHasCash(param) >0){
				unionPromotion.setCash_state(true);
			}else{
				unionPromotion.setCash_state(false);
			}
		}
		return unionPromotion;
	}

	@Override
	public UnionPromotion selectCashDetails(UnionCustomer param) {
		UnionPromotion unionPromotion = unionPromotionMapper.selectCashDetails(param.getPromotion_id());
		if(unionPromotion != null){
//			unionPromotion.setDetail(unionPromotion.getDetails().split(";"));
//			unionPromotion.setDetails(null);
			unionPromotion.setBrands(null);
			if(param.getCustomer_id() !=null && unionCustomerMapper.selectWitherHasCash(param) >0){
				unionPromotion.setCash_state(true);
			}else{
				unionPromotion.setCash_state(false);
			}
		}
		return unionPromotion;
	}

	@Override
	public boolean addCash(UnionCustomer param) {
		if(param.getPromotion_id() == null || param.getCustomer_id() == null){
			return false;
		}
		UnionCustomer unionCustomer = unionCustomerMapper.selectByEntity(param);
		if(unionCustomer!=null){
			return false;
		}
		param.setCreate_time(new Date());
		param.setState(1);
		int i = unionCustomerMapper.insertSelective(param);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public Page<Map<String, Object>> getUnionProducts(Page<Map<String, Object>> page) {
		List<Long> product_ids = unionProductMapper.selectUnionProductIds(page);
	  super.initProduct();
	  query.setStart((page.getPage()-1)*page.getPageSize());
	  query.setRows(page.getPageSize());
	  query.setFields("product_id,image_url,product_name,min_price,sale_num,promotion_types");
	  query.setQuery("*:*");
	  query.addFilterQuery("state:1");
	  query.addFilterQuery("-(brand_state:0)");
	  query.addFilterQuery("partner_state:2");
	  if(product_ids.size()>0){
		  StringBuffer ids =new StringBuffer();
		  for (int i = 0; i < product_ids.size(); i++) {
			  if(i<product_ids.size()-1){
       ids.append("product_id:"+product_ids.get(i)+" OR ");
			  }else{
       ids.append("product_id:"+product_ids.get(i));	  
			  }
		}
		  query.addFilterQuery(ids.toString());
	  }else{
		  page.setTotal(0);
		  page.setParam(null);
		  page.setData(new ArrayList<Map<String, Object>>());
		return page;
	  }
	
	//搜索
		try {
			response=solr.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//获取结果
		List<Map<String, Object>>products = MySolrDoc.toJson( response.getResults());
		
		//添加总条数
		 Integer total =(int)response.getResults().getNumFound();
        page.setTotal(total);
	
					page.setParam(null);
					page.setData(products);
                    
					return page;
	}
	@Override
	public Page<Map<String, Object>> queryunionproducts(Page<Map<String, Object>> page) {
		List<Long> product_ids = unionProductMapper.selectUnionProductIds(page);
		  //初始化solr
		  SolrClient solr = new HttpSolrClient.Builder(SysConstant.product_core).build();
		  QueryResponse response = null;
		  SolrQuery query = new SolrQuery();
		  query.setStart((page.getPage()-1)*page.getPageSize());
		  query.setRows(page.getPageSize());
		  query.setFields("product_id,image_url,product_name,min_price,sale_num");
		  query.setQuery("*:*");
		  query.addFilterQuery("state:1");
		  query.addFilterQuery("-(brand_state:0)");
		  query.addFilterQuery("partner_state:2");
		  if(product_ids.size()>0){
			  StringBuffer ids =new StringBuffer();
			  for (int i = 0; i < product_ids.size(); i++) {
				  if(i<product_ids.size()-1){
	       ids.append("product_id:"+product_ids.get(i)+" OR ");
				  }else{
	       ids.append("product_id:"+product_ids.get(i));	  
				  }
			}
			  query.addFilterQuery(ids.toString());
		  }else{
			  page.setTotal(0);
			  page.setParam(null);
			  page.setData(new ArrayList<Map<String, Object>>());
			return page;
		  }
		
		//搜索
			try {
				response=solr.query(query);
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//获取结果
			List<Map<String, Object>>products = MySolrDoc.toJson( response.getResults());
			
			//添加总条数
			 Integer total =(int)response.getResults().getNumFound();
	        page.setTotal(total);
		
		    //添加销量
						for (int i = 0; i < products.size(); i++) {
							
						   if(products.get(i).get("sale_num") == null){
							   products.get(i).put("sale_num", 0);
						   }
							Map<String, Object> product = products.get(i);
						    product.put("min_prom_price", product.get("min_price"));
							Promotion_type promotion_type=new Promotion_type();
							promotion_type.setId(1);
							Promotion_type promotion_type2=new Promotion_type();
							promotion_type2.setId(6);
							List<Promotion_type> promotion_types =new ArrayList<>();
							promotion_types.add(promotion_type);
							promotion_types.add(promotion_type2);
							product.put("promotion_types", promotion_types);
						}
						page.setParam(null);
						page.setData(products);
                        
						return page;
		
		
		/*Long class_id = brandEntityMapper.selectClassIdByBrandId(brand_id);
		List<ListProductModel> products = new ArrayList<>();
		if(class_id == 1){
			products = unionProductMapper.selectUnionWallpaperForPage(page);
		}else if(class_id == 2){
			products = unionProductMapper.selectUnionFloorForPage(page);
		}else if(class_id == 3){
			products = unionProductMapper.selectUnionTileForPage(page);
		}else if(class_id == 4){
			products = unionProductMapper.selectUnionDoorForPage(page);
		}else if(class_id == 5){
			products = unionProductMapper.selectUnionPaintForPage(page);
		}
		for (int i = 0; i < products.size(); i++) {
			ListProductModel product = products.get(i);
			if(product.getSale_num() == null){
				product.setSale_num(0L);
			}
			Promotion_type promotion_type=new Promotion_type();
			promotion_type.setId(6);
			List<Promotion_type> promotion_types =new ArrayList<>();
			promotion_types.add(promotion_type);
			product.setPromotion_types(promotion_types);
		}
		page.setParam(null);
        page.setData(products);
		return page;*/
	}

	@Override
	public Page<ProductListModel> getSqlUnionProducts(Page<ProductListModel> page) {
		List<ProductListModel> list = unionProductMapper.selectUnionProductsForPage(page);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPromotion_types(brandProductEntityMapper.selectPromotionTypesByProductId(list.get(i).getProduct_id()));
		}
		page.setData(list);
		page.setArrModel(null);
		page.setParam(null);
		return page;
	}

}
