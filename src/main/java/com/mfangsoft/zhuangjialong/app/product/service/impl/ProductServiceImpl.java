package com.mfangsoft.zhuangjialong.app.product.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationEntityMapper;
import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationImageEntityMapper;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationModel;
import com.mfangsoft.zhuangjialong.app.jumptoweb.mapper.BasePathMapper;
import com.mfangsoft.zhuangjialong.app.jumptoweb.service.impl.JumpServiceImpl;
import com.mfangsoft.zhuangjialong.app.main.model.IntroduceProductModel;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.NewProductCoreModelMapper;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.newproductcore.model.NewProductCoreModel;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerCollectionEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.product.model.AppProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ClassProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.product.model.ProductDetails;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.product.model.SalesProperty;
import com.mfangsoft.zhuangjialong.app.product.model.Salesinfo;
import com.mfangsoft.zhuangjialong.app.product.model.SelectPropertiesModel;
import com.mfangsoft.zhuangjialong.app.product.service.ProductService;
import com.mfangsoft.zhuangjialong.app.promotion.model.AppPromotionTypeEnum;
import com.mfangsoft.zhuangjialong.app.promotion.model.Promotion_type;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.DistanceUtil;
import com.mfangsoft.zhuangjialong.common.utils.MySolrDoc;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdImgEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdSalesAttrEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
import com.mfangsoft.zhuangjialong.integration.region.service.RegionService;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月12日
* 
*/
@Service(value="appProduct")
public class ProductServiceImpl extends SuperCore implements ProductService{

	@Autowired
	private BrandNewProductEntityMapper brandProductEntityMapper;
	@Autowired
	private CustomerCollectionEntityMapper customerCollectionEntityMapper;
	@Autowired
	private EvaluationEntityMapper evaluationEntityMapper;
	@Autowired
	private ShopEntityMapper shopEntityMapper;
	@Autowired
	private RegionService regionServiceImpl;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	@Autowired
	BuildClassEntityMapper buildClassEntityMapper;
	@Autowired
	BrandProdSalesAttrEntityMapper brandProdSalesAttrEntityMapper;
	@Autowired
	BrandProdImgEntityMapper brandProdImgEntityMapper;
	@Autowired
	NewProductCoreModelMapper newProductCoreModelMapper;
	@Autowired
	EvaluationImageEntityMapper evaluationImageEntityMapper;
	@Autowired
	UnionPromotionMapper unionPromotionMapper;
	@Autowired
	BasePathMapper basePathMapper;
	@Autowired
	IntroduceProductEntityMapper introduceProductEntityMapper;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	
	@Override
	public ClassProductModel getDefaultPushByClassForPage(Page<Map<String, Object>> page) {
     super.initProduct();
     ClassProductModel classProductModel = new ClassProductModel();
     Map<String, Object> map = (Map<String, Object>) page.getParam();
     Integer class_id=(Integer)map.get("class_id");
     //添加品类名
	  classProductModel.setClass_name(buildClassEntityMapper.selectByPrimaryKey((long)class_id).getName());
	  query.setStart((page.getPage()-1)*page.getPageSize());
	  query.setRows(page.getPageSize());
	  query.setFields("product_id,image_url,product_name,min_price,min_prom_price,sale_num");
	 /* StringBuffer sb = new StringBuffer();
	  Integer[] spaces = page.getArrModel().getSpaces();
	  for (int i = 0; i < spaces.length; i++) {
		sb.append("value_ids:"+spaces[i]+" OR ");
	}
	  Integer[] styles = page.getArrModel().getStyles();
	  for (int i = 0; i < styles.length; i++) {
		  if(i<styles.length-1){
		  sb.append("value_ids:"+styles[i]+" OR ");
		  }else{
		  sb.append("value_ids:"+styles[i]);
		  }
	}
	  query.setQuery(sb.toString());*/
	  query.setQuery("*:*");
	  query.addFilterQuery("class_id:"+map.get("class_id"));
	  query.addFilterQuery("region_code:*"+map.get("region_code")+"*");
	  query.addSort("product_id",ORDER.desc);
	  query.addFilterQuery("state:1");
	  query.addFilterQuery("-(brand_state:0)");
	  query.addFilterQuery("partner_state:2");
	//搜索
		try {
			response=solr.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//获取结果
		List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
		  //添加活动类型
        for (Map<String, Object> product : products) {
        	
        	Long product_id = Long.parseLong(product.get("product_id").toString());
        	
        List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(product_id);
        product.put("promotion_types", promotionTypes);
        
        if(promotionTypes == null || !promotionTypes.contains(AppPromotionTypeEnum.secKill.getTypeValue())){
			// 折扣工具
        	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(product_id);
        	
			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), product_id);
			if(zhekou != null){
				String price = product.get("min_prom_price").toString();
				product.put("min_prom_price", NumberUtil.round2(zhekou.getDiscount() * Double.parseDouble(price)));
			}
		}
        }
		//添加总条数
		 Integer total =(int)response.getResults().getNumFound();
      page.setTotal(total);
	
		page.setParam(null);
		page.setArrModel(null);
		page.setData(products);
		classProductModel.setResultPage(page);
	return classProductModel;
	}
	
	
	@Override
	public ClassProductModel getNewProductByCondition(Page<Map<String, Object>> page) {
		super.initProduct();
		ClassProductModel classProductModel = new ClassProductModel();
		  Map<String, Object> map = (Map<String, Object>) page.getParam();
		  Integer sd =  (Integer) map.get("select_id");
		  Integer class_id=(Integer)map.get("class_id");
		  if(class_id!=null){
			  //添加品类名
			  classProductModel.setClass_name(buildClassEntityMapper.selectByPrimaryKey((long)class_id).getName());
		  }
		 query.setStart((page.getPage()-1)*page.getPageSize());
		  query.setRows(page.getPageSize());
		  query.setFields("product_id,image_url,product_name,min_price,min_prom_price,sale_num");
		  query.setQuery("*:*");
		  query.addFilterQuery("class_id:"+class_id);
		  if(map.get("region_code") !=null){
			  query.addFilterQuery("region_code:*"+map.get("region_code")+"*");
		  }
		  //添加最低价格条件
		  if(map.get("min_price") != null && !map.get("min_price").equals("")){
			   String  min_price= map.get("min_price").toString();
			  query.addFilterQuery("min_prom_price:["+min_price+" TO *]");
		  }
		  //添加最高价格条件
		  if(map.get("max_price") != null && !map.get("max_price").equals("")){
			   String  max_price= map.get("max_price").toString();
			  query.addFilterQuery("min_prom_price:[* TO "+max_price+"]");
		  }
		  //添加品牌数组
		  if(page.getArrModel().getBrand_id().length>0){
			  StringBuffer brand_id = new StringBuffer();
				for (int i = 0; i < page.getArrModel().getBrand_id().length; i++) {
					if(i <page.getArrModel().getBrand_id().length-1){
						brand_id.append("brand_id:"+page.getArrModel().getBrand_id()[i]+" OR ");
					}else{
						brand_id.append("brand_id:"+page.getArrModel().getBrand_id()[i]);
					}
				}
				
				query.addFilterQuery(brand_id.toString());
		  }
		  //添加筛选属性
			  String[][] value_ids = page.getArrModel().getValue_ids();
			  
				for (int i = 0; i < value_ids.length; i++) {
					
					if(value_ids[i].length>0){
					StringBuffer attr_value = new StringBuffer();
					for (int j = 0; j < value_ids[i].length; j++) {
						
						if(j <value_ids[i].length-1){
							attr_value.append("attr_value:"+value_ids[i][j]+" OR ");
						}else{
							attr_value.append("attr_value:"+value_ids[i][j]);
						}
					}
					query.addFilterQuery(attr_value.toString());
					} 
				}
				query.addFilterQuery("state:1");
				query.addFilterQuery("-(brand_state:0)");
				  query.addFilterQuery("partner_state:2");
				  switch (sd){
				   case 1:
						  query.addSort("sort",ORDER.asc);break;
				   case 2:
		                 query.addSort("sale_num",ORDER.desc);break;
				   case 3:
					   query.addSort("new_time",ORDER.desc);break;
				   case 4:
						if(map.get("price_sort") == null){
							return null;
						}
						
						if("1".equals(map.get("price_sort")+"")){
							query.addSort("min_prom_price", ORDER.asc);
						}else{
							query.addSort("min_prom_price", ORDER.desc);
						}
						
				   }
		  try {
			response=solr.query(query);
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//获取结果
			List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
			  //添加活动类型
            for (Map<String, Object> product : products) {
            	Long product_id = Long.parseLong(product.get("product_id").toString());
            List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(product_id);
            product.put("promotion_types", promotionTypes);
            
            if(promotionTypes == null || !promotionTypes.contains(AppPromotionTypeEnum.secKill.getTypeValue())){
    			// 折扣工具
            	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(product_id);
            	
    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), product_id);
    			if(zhekou != null){
    				String price = product.get("min_prom_price").toString();
    				if(StringUtils.isNotEmpty(price)){
    					product.put("min_prom_price", NumberUtil.round2(zhekou.getDiscount() * Double.parseDouble(price)));
    				}
    			}
    		}
            }
			//添加总条数
			 Integer total =(int)response.getResults().getNumFound();
	      page.setTotal(total);
		
			page.setParam(null);
			page.setArrModel(null);
			page.setData(products);
			classProductModel.setResultPage(page);
		return classProductModel;
		  
	}

	
	@Override
	public Boolean addCollectProductOrBrand(CustomerCollectionEntity param) {
		if(param.getProduct_id() == null || param.getCustomer_id() == null){
			return false;
		}
		if(param.getBrand_id() != null){
			return false;
		}
		CustomerCollectionEntity collectionEntity = customerCollectionEntityMapper.selectIfCollected(param);
		param.setCreate_time(new Date());
		if(collectionEntity == null){
			int insertSelective = customerCollectionEntityMapper.insertSelective(param);
			if(insertSelective > 0){
				return true;
			}else{
				return false;
			}
		}else {
			collectionEntity.setState(1);
			int updateByPrimaryKeySelective = customerCollectionEntityMapper.updateByPrimaryKeySelective(collectionEntity);
			if(updateByPrimaryKeySelective >0){
				return true;
			}else{
				return false;
			}
		}
	}


	@Override
	public Boolean updateCollectProductOrBrand(CustomerCollectionEntity param) {
		param.setState(2);
		if(param.getProduct_id() == null || param.getCustomer_id() == null){
			return false;
		}
		int i = customerCollectionEntityMapper.updateByCustomerId(param);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public List<Shop> selectShopInfoByProductId(Product param){
			List<Shop> shops = shopEntityMapper.selectShopInfoByProductId(param.getProduct_id());
			for (int i = 0; i < shops.size(); i++) {
				Shop shop = shops.get(i);
				String address = regionServiceImpl.getWholeAddress(shop.getRegion_code());
				shop.setAddress(address+shop.getAddress());
				shop.setRegion_code(null);
				if( param.getLbs()!=null){
					shop.setDistance(DistanceUtil.getDistance(shop.getLbs(), param.getLbs()));
				}else{
					shop.setDistance(0.0);
				}
				shop.setLbs(null);
			}
			Collections.sort(shops, new Comparator<Shop>() {
				@Override
				public int compare(Shop shop1, Shop shop2) {
					  if(shop1.getDistance()>shop2.getDistance()){
						   return 1;
						  }else if(shop1.getDistance()<shop2.getDistance()){
						   return -1;
						  }else{
							  return 0;
						  }
				}
			});
			return shops;
	}

	
	

	/*@Override
	public SelectPropertiesModel getSelectProperties(AppProductModel param) {
		Long class_id = brandProductEntityMapper.selectClassIdById(param.getId());
		SelectPropertiesModel selectPropertiesModel =new SelectPropertiesModel();
		selectPropertiesModel.setName("颜色");
		//初始化product_core
		SolrClient solr = new HttpSolrClient.Builder(SysConstant.product_sales_core).build();
		QueryResponse response = null;
		SolrQuery query = new SolrQuery();
		query.setQuery("product_id:"+param.getId());
		List<Map<String, Object>> color_models = new ArrayList<>();
		if(class_id == 1|| class_id == 4||class_id == 6||class_id == 8||class_id == 9){
			  query.setFields("id,color_model");
			  try {
				response=solr.query(query);
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  color_models =MySolrDoc.toJson(response.getResults());
		}else{
			  query.setFields("color_model");
			  try {
				response=solr.query(query);
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 color_models = MySolrDoc.toJson(response.getResults());
			  Set<Map<String, Object>> set = new HashSet<>();
			  for (int i = 0; i < color_models.size(); i++) {
				  set.add(color_models.get(i));
			}
			  color_models.clear();
			  Iterator<Map<String, Object>> iterator = set.iterator();
			  while(iterator.hasNext()){
				  color_models.add(iterator.next());
			  }
		}
		selectPropertiesModel.setColor_models(color_models);
		return selectPropertiesModel;
		
	}*/

	/*@Override
	public SelectPropertiesModel getSelectPropertiesByColor(Salesinfo param) {
		
		//初始化product_core
				SolrClient solr = new HttpSolrClient.Builder(SysConstant.product_sales_core).build();
				QueryResponse response = null;
				SolrQuery query = new SolrQuery();
				query.setQuery("product_id:"+param.getProduct_id());
				query.addFilterQuery("color_model:\""+param.getColor_model()+"\"");
				query.addField("id,isline_name,standard,capacity,length");
				try {
					response = solr.query(query);
				} catch (SolrServerException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              List<Map<String,Object>> list = MySolrDoc.toJson(response.getResults());
              SelectPropertiesModel selectPropertiesModel = new SelectPropertiesModel();
              if(list.get(0).get("isline_name")!=null){
            	  selectPropertiesModel.setName("是否包踢脚线");
            	  for (int i = 0; i < list.size(); i++) {
            		  list.get(i).put("name",  list.get(i).get("isline_name"));
            		  list.get(i).remove("isline_name");
				}
              }
              if(list.get(0).get("standard")!=null){
            	  selectPropertiesModel.setName("规格");
            	  for (int i = 0; i < list.size(); i++) {
            		  list.get(i).put("name",  list.get(i).get("standard"));
            		  list.get(i).remove("standard");
				}
              }
              if(list.get(0).get("capacity")!=null){
            	  selectPropertiesModel.setName("容量");
            	  for (int i = 0; i < list.size(); i++) {
            		  list.get(i).put("name",  list.get(i).get("capacity"));
            		  list.get(i).remove("capacity");
				}
              }
              if(list.get(0).get("length")!=null){
            	  selectPropertiesModel.setName("长度");
            	  for (int i = 0; i < list.size(); i++) {
            		  list.get(i).put("name",  list.get(i).get("length"));
            		  list.get(i).remove("length");
				}
              }
              selectPropertiesModel.setColor_models(list);
				
		return selectPropertiesModel;
	}*/
	@Override
	public Map<String, Object> getProperties(SalesProperty param) {
		 Map<String, Object> appSalesAttrById = brandProdSalesAttrEntityMapper.getAppSalesAttrById(param.getId());
		 Double price = brandProductEntityMapper.selectSecKillPriceByProductId(param.getProduct_id());
		 if(price != null){
			 appSalesAttrById.replace("price", price);
		 }
		 return appSalesAttrById;
	}

	/*@Override
	public Map<String, Object> selectProperties(SalesProperty param) {
	
		//初始化product_core
		  SolrClient solr = new HttpSolrClient.Builder(SysConstant.product_sales_core).build();
		  QueryResponse response = null;
		  SolrQuery query = new SolrQuery();
		  query.setQuery("id:"+param.getId());
		  query.addFilterQuery("product_id:"+param.getProduct_id());
		  query.setFields("stock,price,color_img");
		  try {
			response=solr.query(query);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> result = MySolrDoc.toOneJson(response.getResults());
		result.put("new_price", result.get("price"));
	return result;
	}*/



	@Override
	public List<SelectPropertiesModel> getNewProductConditions(BuildClass param) {
		List<SelectPropertiesModel> list = brandProductEntityMapper.selectAttrsByRegoinCodeClassId(param);
		for (int i = 0; i < list.size(); i++) {
			SelectPropertiesModel selectPropertiesModel = list.get(i);
			List<Salesinfo> conditions = selectPropertiesModel.getConditions();
			for (int j = 0; j < conditions.size(); j++) {
				Salesinfo salesinfo = conditions.get(j);
				salesinfo.setStyle_num(i);
			}
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getProductInfo(Map<String, Long> param) {
		super.initProduct();
		  Long id = param.get("product_id");
		  query.setQuery("product_id:"+id);
		  query.addFilterQuery("state:1");
			if(param.get("select_id") == 1){
				query.setFields("description");
				try {
					response = solr.query(query);
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
				}
				Map<String, Object> oneJson = MySolrDoc.toOneJson(response.getResults());
				return oneJson;  //图文详情
			
			}else if(param.get("select_id") == 2){
				query.setFields("case_images");
				try {
					response = solr.query(query);
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
				}
				Map<String, Object> oneJson = MySolrDoc.toOneJson(response.getResults());
				return oneJson;	//案例展示
				
			}else if(param.get("select_id") == 3){
				Map<String, Object> map = new HashMap<>();
				query.setFields("brand_name,color,model,standard,unit");
				try {
					response = solr.query(query);
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
				}
				Map<String, Object> oneJson = MySolrDoc.toOneJson(response.getResults());
				List<SelectPropertiesModel> params = new ArrayList<>();
				if(oneJson.get("brand_name")!=null){
					SelectPropertiesModel  brand= new SelectPropertiesModel();
					brand.setName("品牌");
					brand.setParam(oneJson.get("brand_name").toString());
					params.add(brand);
				}
				if(oneJson.get("unit")!=null){
					SelectPropertiesModel  unit= new SelectPropertiesModel();
					unit.setName("计价单位");
					unit.setParam(oneJson.get("unit").toString());
					params.add(unit);
				}
				if(oneJson.get("color")!=null){
					SelectPropertiesModel  colors= new SelectPropertiesModel();
					colors.setName("颜色");
					colors.setParam(oneJson.get("color").toString());
					params.add(colors);
				}
				if(oneJson.get("model")!=null){
					SelectPropertiesModel  models= new SelectPropertiesModel();
					models.setName("型号");
					models.setParam(oneJson.get("model").toString());
					params.add(models);
				}
				if(oneJson.get("standard")!=null){
					SelectPropertiesModel  standard= new SelectPropertiesModel();
					standard.setName("规格");
					standard.setParam(oneJson.get("standard").toString());
					params.add(standard);
				}
				List<SelectPropertiesModel> selectAttrsByProductId = brandProductEntityMapper.selectAttrsByProductId(id);
				for (int i = 0; i < selectAttrsByProductId.size(); i++) {
					SelectPropertiesModel  attr= new SelectPropertiesModel();
					attr.setName(selectAttrsByProductId.get(i).getName());
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < selectAttrsByProductId.get(i).getConditions().size(); j++) {
						sb.append(selectAttrsByProductId.get(i).getConditions().get(j).getValue()+" ");
					}
					attr.setParam(sb.toString());
					params.add(attr);
				}
				map.put("params", params);
				return map;
				
			}
		return null;
	}

	@Override
	public Map<String, Object> selectProductInfo(Map<String, Long> param) {
		
		 //初始化product_core
		  SolrClient solr = new HttpSolrClient.Builder(SysConstant.product_core).build();
		  QueryResponse response = null;
		  SolrQuery query = new SolrQuery();
		  Long id = param.get("product_id");
		  query.setQuery("product_id:"+id);
		  
			if(param.get("select_id") == 1){
				query.setFields("description");
				try {
					response = solr.query(query);
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
				}
				return MySolrDoc.toOneJson(response.getResults());  //图文详情
			
			}else if(param.get("select_id") == 2){
				query.setFields("case_images");
				try {
					response = solr.query(query);
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
				}
				return MySolrDoc.toOneJson(response.getResults());	//案例展示
				
			}else if(param.get("select_id") == 3){
				query.setFields("spaces,craft,haspicture_name,madein,main_material,style,standard,brand_name,"
						+ "unit,material_package_name,color,model,thickness,tail_standard,size,texture,open_mode,gloss,coating,ispalette_name,spray_type_name,capacity,length");
				try {
					response = solr.query(query);
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
				}
				Map<String, Object> result = new HashMap<>();
				Map<String, Object> map = MySolrDoc.toOneJson(response.getResults());
				List<SelectPropertiesModel> params = new ArrayList<>();
				if(map.get("spaces")!=null){
				SelectPropertiesModel  spaces= new SelectPropertiesModel();
				spaces.setName("适用空间");
				spaces.setParam(map.get("spaces").toString());
				params.add(spaces);
				}
				if(map.get("craft")!=null){
					SelectPropertiesModel  craft= new SelectPropertiesModel();
					craft.setName("面层工艺");
					craft.setParam(map.get("craft").toString());
					params.add(craft);
				}
				if(map.get("haspicture_name")!=null){
					SelectPropertiesModel  haspicture= new SelectPropertiesModel();
					haspicture.setName("是否有图案");
					haspicture.setParam(map.get("haspicture_name").toString());
					params.add(haspicture);
				}
				if(map.get("madein")!=null){
					SelectPropertiesModel  madeIn= new SelectPropertiesModel();
					madeIn.setName("产地");
					madeIn.setParam(map.get("madein").toString());
					params.add(madeIn);
				}
				if(map.get("main_material")!=null){
					SelectPropertiesModel  main_material= new SelectPropertiesModel();
					main_material.setName("主材");
					main_material.setParam(map.get("main_material").toString());
					params.add(main_material);
				}
				if(map.get("style")!=null){
					SelectPropertiesModel  style= new SelectPropertiesModel();
					style.setName("风格");
					style.setParam(map.get("style").toString());
					params.add(style);
				}
				if(map.get("standard")!=null){
					SelectPropertiesModel  standard= new SelectPropertiesModel();
					standard.setName("规格");
					standard.setParam(map.get("standard").toString());
					params.add(standard);
				}
				if(map.get("brand_name")!=null){
					SelectPropertiesModel  brand= new SelectPropertiesModel();
					brand.setName("品牌");
					brand.setParam(map.get("brand_name").toString());
					params.add(brand);
				}
				if(map.get("unit")!=null){
					SelectPropertiesModel  unit= new SelectPropertiesModel();
					unit.setName("计价单位");
					unit.setParam(map.get("unit").toString());
					params.add(unit);
				}
				if(map.get("material_package_name")!=null){
					SelectPropertiesModel  material_package= new SelectPropertiesModel();
					material_package.setName("辅料套餐");
					material_package.setParam(map.get("material_package_name").toString());
					params.add(material_package);
				}
				if(map.get("color")!=null){
					SelectPropertiesModel  colors= new SelectPropertiesModel();
					colors.setName("颜色");
					colors.setParam(map.get("color").toString());
					params.add(colors);
				}
				if(map.get("model")!=null){
					SelectPropertiesModel  models= new SelectPropertiesModel();
					models.setName("型号");
					models.setParam(map.get("model").toString());
					params.add(models);
				}
				if(map.get("thickness")!=null){
					SelectPropertiesModel  thickness= new SelectPropertiesModel();
					thickness.setName("厚度");
					thickness.setParam(map.get("thickness").toString());
					params.add(thickness);
				}
				if(map.get("tail_standard")!=null){
					SelectPropertiesModel  standard= new SelectPropertiesModel();
					standard.setName("规格");
					standard.setParam(map.get("tail_standard").toString());
					params.add(standard);
				}
				if(map.get("size")!=null){
					SelectPropertiesModel  size= new SelectPropertiesModel();
					size.setName("大小");
					size.setParam(map.get("size").toString());
					params.add(size);
				}
				if(map.get("texture")!=null){
					SelectPropertiesModel  texture= new SelectPropertiesModel();
					texture.setName("纹理");
					texture.setParam(map.get("texture").toString());
					params.add(texture);
				}
				if(map.get("open_mode")!=null){
					SelectPropertiesModel  open_mode= new SelectPropertiesModel();
					open_mode.setName("打开方式");
					open_mode.setParam(map.get("open_mode").toString());
					params.add(open_mode);
				}
				if(map.get("gloss")!=null){
					SelectPropertiesModel  gloss= new SelectPropertiesModel();
					gloss.setName("涂料光泽");
					gloss.setParam(map.get("gloss").toString());
					params.add(gloss);
				}
				if(map.get("coating")!=null){
					SelectPropertiesModel  coating= new SelectPropertiesModel();
					coating.setName("涂层类别");
					coating.setParam(map.get("coating").toString());
					params.add(coating);
				}
				if(map.get("ispalette_name")!=null){
					SelectPropertiesModel  ispalette_name= new SelectPropertiesModel();
					ispalette_name.setName("是否可调色");
					ispalette_name.setParam(map.get("ispalette_name").toString());
					params.add(ispalette_name);
				}
				if(map.get("spray_type_name")!=null){
					SelectPropertiesModel  spray_type_name= new SelectPropertiesModel();
					spray_type_name.setName("喷漆方式");
					spray_type_name.setParam(map.get("spray_type_name").toString());
					params.add(spray_type_name);
				}
				if(map.get("capacity")!=null){
					SelectPropertiesModel  capacity= new SelectPropertiesModel();
					capacity.setName("容量");
					capacity.setParam(map.get("capacity").toString());
					params.add(capacity);
				}
				if(map.get("length")!=null){
					SelectPropertiesModel  length= new SelectPropertiesModel();
					length.setName("长度");
					length.setParam(map.get("capacity").toString());
					params.add(length);
				}
				 result.put("params", params);
				return result;
			}
		
		return null;
	}

	@Override
	public Page<Map<String, Object>> getProductSelectionForPage(Page<Map<String, Object>> page) {
		super.initProduct();
	    Map<String, Object> map = (Map<String, Object>) page.getParam();
	    Integer sd =  (Integer) map.get("select_id");
		  query.setStart((page.getPage()-1)*page.getPageSize());
		  query.setRows(page.getPageSize());
		  query.setFields("product_id,image_url,product_name,min_price,min_prom_price,sale_num");
		  String searchcontent;
		  query.setQuery("*:*");
		  query.addFilterQuery("state:1");
		  query.addFilterQuery("-(brand_state:0)");
		  query.addFilterQuery("partner_state:2");
		  //添加搜索字段条件
		  if(!map.get("searchcontent").equals("")){
			   searchcontent = map.get("searchcontent").toString();
			   String[] split = searchcontent.split(" ");
			   StringBuffer sb = new StringBuffer();
			   for (int i = 0; i < split.length; i++) {
				   if(!"".equals(split[i])){
				   if(i <split.length-1 ){
						  sb.append("product_name:*"+split[i]+"* OR ");
					  }else{
						  sb.append("product_name:*"+split[i]+"*");
					  }
			}
			   }
			   query.addFilterQuery(sb.toString());
		  }
		 
		  query.addFilterQuery("region_code:*"+map.get("region_code")+"*");
   switch (sd){
   case 1:
	/*   StringBuffer sb = new StringBuffer();
	   Integer[] spaces = page.getArrModel().getSpaces();
	   for (int i = 0; i < spaces.length; i++) {
		   sb.append("value_ids:"+spaces[i]+" OR ");
	   }
	   Integer[] styles = page.getArrModel().getStyles();
	   for (int i = 0; i < styles.length; i++) {
		   if(i<styles.length-1){
			   sb.append("value_ids:"+styles[i]+" OR ");
		   }else{
			   sb.append("value_ids:"+styles[i]);
		   }
	   }
	   	for (IntroduceProductModel introduceProductModel : list) {
			List<Map<String, Object>> products = introduceProductEntityMapper.selectProductIdsByIntroduceId(introduceProductModel.getId());
			StringBuffer sb=new StringBuffer();
           for (int i = 0; i < products.size(); i++) {
        	   if(i<products.size()-1){
       		sb.append("product_id: ").append(products.get(i).get("product_id").toString()).append(" OR ");
        	   }else{
        	sb.append("product_id: ").append(products.get(i).get("product_id").toString());
        	   }
		}
	   query.addFilterQuery(sb.toString());
	   StringBuffer s = new StringBuffer();
		  Long[] class_ids = page.getArrModel().getClass_ids();
		  for (int i = 0; i < class_ids.length; i++) {
			  if(i<class_ids.length-1){
				  s.append("class_id:"+class_ids[i]+" OR ");
				  }else{
					  s.append("class_id:"+class_ids[i]);
				  }
		}
		  query.addFilterQuery(s.toString());
		  query.addSort("product_id",ORDER.asc);break;*/
	   if(map.get("searchcontent").equals("")){
		List<Long> list = introduceProductEntityMapper.selectIntroduceProductIds(map.get("region_code")+"");
		if(list.size()>0){
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
	        	   if(i<list.size()-1){
	       		sb.append("product_id: ").append(list.get(i).toString()).append(" OR ");
	        	   }else{
	        	sb.append("product_id: ").append(list.get(i).toString());
	        	   }
		}
	   query.addFilterQuery(sb.toString());
		}
		}else{
			query.addSort("product_id", ORDER.desc);
		}
		break;
   case 2:
	   query.addSort("sale_num",ORDER.desc);break;
   case 3:
	   query.addSort("new_time",ORDER.desc);break;
   case 4:
		if(map.get("price_sort") == null){
			return null;
		}
		
		if("1".equals(map.get("price_sort")+"")){
			query.addSort("min_prom_price", ORDER.asc);
		}else{
			query.addSort("min_prom_price", ORDER.desc);
		}
		
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
			List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
			  //添加活动类型
            for (Map<String, Object> product : products) {
            	
            	Long product_id = Long.parseLong(product.get("product_id").toString());
            	
            List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(product_id);
            product.put("promotion_types", promotionTypes);
            
            if(promotionTypes == null || !promotionTypes.contains(AppPromotionTypeEnum.secKill.getTypeValue())){
    			// 折扣工具
            	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(product_id);
            	
    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), product_id);
    			if(zhekou != null){
    				String price = product.get("min_prom_price").toString();
    				product.put("min_prom_price", NumberUtil.round2(zhekou.getDiscount() * Double.parseDouble(price)));
    			}
    		}
            
            }
			//添加总条数
			 Integer total =(int)response.getResults().getNumFound();
	      page.setTotal(total);
		
			page.setParam(null);
			page.setArrModel(null);
			page.setData(products);
			return page;
	}


	@Override
	public List<String> getColorModelByProduct_id(AppProductModel param) {
		return  brandProdSalesAttrEntityMapper.getColorModelByProductId(param.getId());
	}


	@Override
	public List<BrandProdSalesAttrEntity> getStandardByProduct_id(BrandProdSalesAttrEntity param) {
		return brandProdSalesAttrEntityMapper.getStandardByProductId(param);
	}


/*	@Override
	public ClassProductModel getSqlDefaultPushByClassForPage(Page<ProductListModel> page) {
		ClassProductModel classProductModel = new ClassProductModel();
		Map<String, Object> map = (Map<String, Object>) page.getParam();
	     Integer class_id=(Integer)map.get("class_id");
	     //添加品类名
		  classProductModel.setClass_name(buildClassEntityMapper.selectByPrimaryKey((long)class_id).getName());
		  List<ProductListModel> list = brandProductEntityMapper.selectDefaultProductsForPage(page);
		  for (int i = 0; i < list.size(); i++) {
				list.get(i).setPromotion_types(brandProductEntityMapper.selectPromotionTypesByProductId(list.get(i).getProduct_id()));
			}
			 page.setData(list);
		page.setArrModel(null);
		page.setParam(null);
		classProductModel.setResultPage(page);
		return classProductModel;
	}*/


	/*@Override
	public ClassProductModel getSqlProductByCondition(Page<ProductListModel> page) {
		ClassProductModel classProductModel = new ClassProductModel();
		Map<String, Object> map = (Map<String, Object>) page.getParam();
		Integer class_id = (Integer) map.get("class_id");
		StringBuffer sb = new StringBuffer();
		if (page.getArrModel().getValue_ids().length > 0) {
			List<StringBuffer> list = new ArrayList<>();
			for (int i = 0; i < page.getArrModel().getValue_ids().length; i++) {
				if (page.getArrModel().getValue_ids()[i].length > 0) {
					StringBuffer stb = new StringBuffer();
					stb.append("(");
					for (int j = 0; j < page.getArrModel().getValue_ids()[i].length; j++) {
						if (j < page.getArrModel().getValue_ids()[i].length - 1) {
							stb.append(page.getArrModel().getValue_ids()[i][j] + ",");
						} else {
							stb.append(page.getArrModel().getValue_ids()[i][j] + ")");
						}
					}
					list.add(stb);
				}
			}
			if (list.size() == 1) {
				sb.append(" RIGHT JOIN ( SELECT product_id FROM t_biz_brand_prod_attrvalue WHERE  attr_value IN "
						+ list.get(0) + ")  pa ON p.product_id = pa.product_id");
			} else if (list.size() > 1) {
				sb.append(" RIGHT JOIN ( SELECT a0.product_id FROM");
				for (int i = 0; i < list.size(); i++) {
					sb.append(" ( SELECT product_id FROM t_biz_brand_prod_attrvalue WHERE  attr_value IN " + list.get(i)
							+ ") a" + i);
					if (i < list.size() - 1) {
						sb.append(",");
					}
				}
				
				for (int i = 1; i < list.size(); i++) {
					if(list.size()==2){
						sb.append(" where a0.product_id =a1.product_id ) pa ON p.product_id = pa.product_id");
					}else{
					if(i == 1){
						sb.append(" where a0.product_id =a1.product_id");
					}else if(i>1 && i<list.size()-1 ){
						sb.append(" and a0.product_id =a" + i + ".product_id");
					}else if (i == list.size() - 1) {
						sb.append(" and a0.product_id =a" + i + ".product_id ) pa ON p.product_id = pa.product_id");
					}
					}
				}
			}
		}
		map.put("sql", sb.toString());
		if (page.getArrModel().getBrand_id().length > 0) {
			map.put("brand_id", "");
		}
		page.setParam(map);
		// 添加品类名
		classProductModel.setClass_name(buildClassEntityMapper.selectByPrimaryKey((long) class_id).getName());
		List<ProductListModel> list = brandProductEntityMapper.selectClassProductsForPage(page);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPromotion_types(
					brandProductEntityMapper.selectPromotionTypesByProductId(list.get(i).getProduct_id()));
		}
		page.setData(list);
		page.setArrModel(null);
		page.setParam(null);
		classProductModel.setResultPage(page);
		return classProductModel;
	}*/


/*	@Override
	public Page<ProductListModel> getSqlProductSelectionForPage(Page<ProductListModel> page) {
		Map<String, String> map = (Map<String, String>) page.getParam();
		String string = map.get("searchcontent");
		List<String> list =new ArrayList<>();
		if(string != ""){
			String[] split = string.split(" ");
			for (int i = 0; i < split.length; i++) {
				if(!"".equals(split[i])){
					list.add(split[i]);
				}
			}
			page.getArrModel().setProduct_name(list);
		}
		List<ProductListModel> products = brandProductEntityMapper.selectProductSelectionForPage(page);
		 for (int i = 0; i < products.size(); i++) {
			 products.get(i).setPromotion_types(brandProductEntityMapper.selectPromotionTypesByProductId(products.get(i).getProduct_id()));
			}
			 page.setData(products);
			page.setArrModel(null);
			page.setParam(null);
		return page;
	}*/


	@Override
	public ProductDetails getSqlProductDetails(CustomerCollectionEntity param) {
		BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(param.getProduct_id());
		ProductDetails productDetails = brandProductEntityMapper.selectProductDetailsByProductId(param.getProduct_id());
		productDetails.setImages(brandProdImgEntityMapper.selectImgByProductId(param.getProduct_id()));
		productDetails.setPromotion_types(brandProductEntityMapper.selectPromotionTypesByProductId(param.getProduct_id()));//1折扣券 2 折扣 3满减 4秒杀 5一口价 6联盟
		if(productDetails.getPromotion_types() == null || !productDetails.getPromotion_types().contains(AppPromotionTypeEnum.secKill.getTypeValue())){
			// 折扣工具
						PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(),
								param.getProduct_id());
						if(zhekou != null){
							String price = productDetails.getPrice();
							if(price.contains("-")){
								String[] str_price = price.split("-");
								Double p1 = zhekou.getDiscount() * Double.parseDouble(str_price[0]);
								Double p2 = zhekou.getDiscount() * Double.parseDouble(str_price[1]);
								productDetails.setMin_promotion_price(p1.intValue() + "-" + p2.intValue());
							}else{
								productDetails.setMin_promotion_price(NumberUtil.round2(zhekou.getDiscount() * Double.parseDouble(price)));
							}
						}
		}
		
		if(productDetails.getPromotion_types().contains(6)){
			UnionPromotion unionPromotion = unionPromotionMapper.selectPromotionByProductId(param.getProduct_id());
			productDetails.setUnion_promotion_id(unionPromotion.getId());
			productDetails.setUnion_title(unionPromotion.getName());
		}
		
		EvaluationModel evaluationModel = evaluationEntityMapper.selectOneGoodByProductId(param.getProduct_id());
		if(evaluationModel!=null){
		evaluationModel.setEva_images(evaluationImageEntityMapper.selectImagesByEvaluationId(evaluationModel.getId()));
		productDetails.setEvaluation(evaluationModel);
		}
			productDetails.setEvaluation_num(evaluationEntityMapper.selectAllEvaluationNumByProductId(param.getProduct_id()));
			String url =null;
			ObjectMapper o = new ObjectMapper();
			try {
				url=JumpServiceImpl.BATH_PRE+basePathMapper.selectByPrimaryKey(JumpServiceImpl.PROD_DETAILS_CODE)+ "?result="+ URLEncoder.encode(o.writeValueAsString(param), "UTF-8");
			} catch (UnsupportedEncodingException | JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			productDetails.setProduct_detail_url(url);
			if(param.getCustomer_id() != null){
				if(customerCollectionEntityMapper.selectIfCollecting(param) != null){
					productDetails.setCollect_state(1);
				}else{
					productDetails.setCollect_state(2);
				}
			}else{
					productDetails.setCollect_state(2);
					}
		
		return productDetails;
	}

	@Override
	public Map<String, Object> getSqlProductInfo(CustomerCollectionEntity param) {
		Map<String, Object> map = new HashMap<>();
     switch (param.getSelect_id()){
     case 1:
    	 map.put("description", brandProductEntityMapper.selectDescriptionByProductId(param.getProduct_id())) ;  break;
     case 2:
    	 map.put("case_images", brandProdImgEntityMapper.selectCaseImgByProductId(param.getProduct_id()));break;
     case 3:
    	 NewProductCoreModel newProductCoreModel = newProductCoreModelMapper.selectProductCoreByProductId(param.getProduct_id());
    	 List<SelectPropertiesModel> params = new ArrayList<>();
			if(newProductCoreModel.getBrand_name()!=null){
				SelectPropertiesModel  brand= new SelectPropertiesModel();
				brand.setName("品牌");
				brand.setParam(newProductCoreModel.getBrand_name());
				params.add(brand);
			}
			if(newProductCoreModel.getUnit()!=null){
				SelectPropertiesModel  unit= new SelectPropertiesModel();
				unit.setName("计价单位");
				unit.setParam(newProductCoreModel.getUnit());
				params.add(unit);
			}
			List<String> colorsByProductId = brandProdSalesAttrEntityMapper.getColorsByProductId(param.getProduct_id());
			if(colorsByProductId.size()>0){
				SelectPropertiesModel  colors= new SelectPropertiesModel();
				colors.setName("颜色");
				colors.setParam(colorsByProductId.toString());
				params.add(colors);
			}
			List<String> modelByProductId = brandProdSalesAttrEntityMapper.getModelByProductId(param.getProduct_id());
			if(modelByProductId.size()>0){
				SelectPropertiesModel  models= new SelectPropertiesModel();
				models.setName("型号");
				models.setParam(modelByProductId.toString());
				params.add(models);
			}
			
			List<String> standardsByProductId = brandProdSalesAttrEntityMapper.getStandardsByProductId(param.getProduct_id());
			
			if(standardsByProductId.size()>0){
				SelectPropertiesModel  standard= new SelectPropertiesModel();
				standard.setName("规格");
				standard.setParam(standardsByProductId.toString());
				params.add(standard);
			}
			List<SelectPropertiesModel> selectAttrsByProductId = brandProductEntityMapper.selectAttrsByProductId(param.getProduct_id());
			for (int i = 0; i < selectAttrsByProductId.size(); i++) {
				SelectPropertiesModel  attr= new SelectPropertiesModel();
				attr.setName(selectAttrsByProductId.get(i).getName());
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < selectAttrsByProductId.get(i).getConditions().size(); j++) {
					sb.append(selectAttrsByProductId.get(i).getConditions().get(j).getValue()+" ");
				}
				attr.setParam(sb.toString());
				params.add(attr);
			}
			map.put("params", params);
     }
		
		return map;
	}



	}
	
