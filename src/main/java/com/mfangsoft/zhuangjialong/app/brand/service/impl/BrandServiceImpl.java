package com.mfangsoft.zhuangjialong.app.brand.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandEnModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandNewProduct;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandScore;
import com.mfangsoft.zhuangjialong.app.brand.model.RegionModel;
import com.mfangsoft.zhuangjialong.app.brand.model.RegionWithLetter;
import com.mfangsoft.zhuangjialong.app.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.app.jumptoweb.mapper.BasePathMapper;
import com.mfangsoft.zhuangjialong.app.jumptoweb.service.impl.JumpServiceImpl;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerCollectionEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.promotion.model.AppPromotionTypeEnum;
import com.mfangsoft.zhuangjialong.app.promotion.model.Promotion_type;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.DistanceUtil;
import com.mfangsoft.zhuangjialong.common.utils.MySolrDoc;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandBannerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandMainProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brandclassify.mapper.BrandClassifyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseInfoEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseOneEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseTwoEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseOneEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.prepayment.mapper.PrepayValueMapper;
import com.mfangsoft.zhuangjialong.integration.prepayment.model.PrepayValue;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.region.mapper.RegionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.region.service.RegionService;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月12日
* 
*/
@Service
public class BrandServiceImpl extends SuperCore implements BrandService{
    
	@Autowired
	private BuildClassEntityMapper buildClassEntityMapper; 
	@Autowired
	private BrandEntityMapper brandEntityMapper;
	@Autowired
	private CustomerCollectionEntityMapper customerCollectionEntityMapper;
	@Autowired
	private BrandBannerEntityMapper brandBannerEntityMapper;
	@Autowired
	private BrandClassifyEntityMapper brandClassifyEntityMapper;
	@Autowired
	private BrandNewProductEntityMapper brandProductEntityMapper;
	@Autowired
	private EnterpriseInfoEntityMapper enterpriseInfoEntityMapper;
	@Autowired
	private ShopEntityMapper shopEntityMapper;
	@Autowired
	private RegionService brandServiceImpl;
	@Autowired
	private PrepayValueMapper prepayValueMapper;
	@Autowired
	private RegionEntityMapper regionEntityMapper;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	@Autowired
    BrandMainProductEntityMapper brandMainProductEntityMapper;
	@Autowired
	BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	BasePathMapper basePathMapper;
	@Autowired
	EnterpriseOneEntityMapper enterpriseOneEntityMapper;
	@Autowired
	EnterpriseTwoEntityMapper enterpriseTwoEntityMapper;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	
	public List<BuildClassEntity> selectClassesByRegion(Map<String, String> map) {
		
		return  buildClassEntityMapper.selectClassesByRegion(map);
	}

	@Override
	public List<BrandEntity>  selectRegionBrand(BrandEntity param){
		return  brandEntityMapper.selectRegionBrand(param);
		
	}
	@Override
	public List<BrandEnModel> selectRegionBrandForEn(Map<String, Object> map) {
		
		/*//获取到当地的品牌
		List<BrandModel> brands = brandEntityMapper.selectRegionBrandByClassId(map);
		//将首字母删重
		Set<String> set = new HashSet<>();
		for (int i = 0; i < brands.size(); i++) {
			//获取品牌的首字母
			String first_letter = ChineseCharToEnUtil.getFirstLetter(brands.get(i).getBrand_name());
			brands.get(i).setFirst_letter(first_letter);
			set.add(first_letter);
		}
		Iterator<String> iterator = set.iterator();
		
		//按首字母封装品牌集合
		List<BrandEnModel> brandEnModels = new ArrayList<>();
		while(iterator.hasNext()){
			BrandEnModel brandEnModel = new BrandEnModel();
			brandEnModel.setFirst_letter(iterator.next());
			List<BrandModel> brandModels = new ArrayList<>();
			for(BrandModel brandModel:brands){
				if(brandModel.getFirst_letter().equals(brandEnModel.getFirst_letter())){
					brandModels.add(brandModel);
				}
			}
			brandEnModel.setBrands(brandModels);
			brandEnModels.add(brandEnModel);
		}
		//删除品牌模型的首字母
		for(BrandModel brandModel:brands){
			brandModel.setFirst_letter(null);
		}
		
		//将首字母排序
		Collections.sort(brandEnModels,new Comparator<BrandEnModel>() {
		    @Override
		    public int compare(BrandEnModel o1, BrandEnModel o2) {
		    	return o1.getFirst_letter().compareTo(o2.getFirst_letter());
		    }
		});*/
		return brandEntityMapper.selectBrandEnModelsByClassId(map);
	}
	@Override
	public List<BrandModel> selectRegionBrandByClassId(Map<String, Object> map) {
		return brandEntityMapper.selectRegionBrandByClassId(map);
	}
	@Override
	public Boolean addCollectProductOrBrand(CustomerCollectionEntity param) {
		if(param.getBrand_id() == null || param.getCustomer_id() == null){
			return false;
		}
		if(param.getProduct_id() != null){
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
		if(param.getBrand_id() == null || param.getCustomer_id() == null){
			return false;
		}
		param.setState(2);
		int i = customerCollectionEntityMapper.updateByCustomerId(param);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public BrandScore selectTotalScoreByBrandId(Map<String, Long> map) {
		BrandScore brandScore = brandEntityMapper.selectTotalScoreByBrandId(map);
		if(brandScore.getEvaluation_num() == 0){
			brandScore.setTotalQuality(5.0);
			brandScore.setTotalService(5.0);
		}else{
			Double quality =Math.floor(brandScore.getQuality()/brandScore.getEvaluation_num()*10)/10;
			Double qualityInt =Math.floor(brandScore.getQuality()/brandScore.getEvaluation_num());
			if(quality-qualityInt >= 0 && quality-qualityInt < 0.4){
				brandScore.setTotalQuality(qualityInt);
			}else{
				brandScore.setTotalQuality(qualityInt+0.5);
			}
			
			Double service =Math.floor(brandScore.getService()/brandScore.getEvaluation_num()*10)/10;
			Double serviceInt =Math.floor(brandScore.getService()/brandScore.getEvaluation_num());
			if(service-serviceInt >= 0 && service-serviceInt < 0.4){
				brandScore.setTotalService(serviceInt); 
			}else{
				brandScore.setTotalService(serviceInt+0.5);
			}
		}
		return brandScore;
	}

	@Override
	public BrandModel selectBrandHead(Map<String, Long> param) {
		
		BrandScore brandScore = this.selectTotalScoreByBrandId(param);
		BrandModel brandModel = brandEntityMapper.selectBrandHead(param);
		brandModel.setRedbag_num(brandCouponsEntityMapper.selectRedBagsForBrandMain(param.get("brand_id")).size());
		brandModel.setCounpons_num(brandCouponsEntityMapper.selectCouponsForBrandMain(param.get("brand_id")).size());
		brandModel.setQuality(brandScore.getTotalQuality());
		brandModel.setService(brandScore.getTotalService());
		brandModel.setBg_image(SysConstant.image_bath_path+"upload/image/brand_bg_image_"+param.get("brand_id")+".png");
		ObjectMapper o = new ObjectMapper();
		String url=null;
		try {
			url = JumpServiceImpl.BATH_PRE+basePathMapper.selectByPrimaryKey(JumpServiceImpl.BRAND_MAIN_CODE)+"?result="+ URLEncoder.encode(o.writeValueAsString(param), "UTF-8");
		} catch (UnsupportedEncodingException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		brandModel.setBrand_main_url(url);//获取品牌首页链接
		if(param.get("customer_id") != null){
			if(brandEntityMapper.selectBrandWhetherCollected(param)>0){
				brandModel.setState(1);
			}else{
				brandModel.setState(2);
			}
		}else{
			brandModel.setState(2);
		}
		return brandModel;
	}
	
	
	@Override
	public BrandModel getBrandMain(CustomerCollectionEntity param) {
		BrandModel brandModel = new BrandModel();
		List<Long> product_ids = brandMainProductEntityMapper.selectProductIdByBrandId(param.getBrand_id());
		super.initProduct();
		query.setFields("product_id,image_url,product_name,min_price,sale_num");
		  query.setQuery("*:*");
		  
		//获取结果
			List<Map<String, Object>>  products =new ArrayList<>();
		  
			//添加产品id数组
			if(product_ids !=null &&product_ids.size()>0){
				StringBuffer pds = new StringBuffer();
				for (int i = 0; i < product_ids.size(); i++) {
					if(i <product_ids.size()-1){
						pds.append("product_id:"+product_ids.get(i)+" OR ");
					}else{
						pds.append("product_id:"+product_ids.get(i));
					}
				}
				query.addFilterQuery(pds.toString());
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
			products = MySolrDoc.toJson( response.getResults());
			}
			brandModel.setProducts(products);
			
			brandModel.setBanner(brandBannerEntityMapper.selectByBrandId(param.getBrand_id()));
			
			
			return brandModel;
	}
	@Override
	public BrandModel selectBrandMain(CustomerCollectionEntity param) {
		BrandModel brandModel = new BrandModel();
		List<Long> product_ids = brandMainProductEntityMapper.selectProductIdByBrandId(param.getBrand_id());
		
		 //初始化solr
		  SolrClient solr = new HttpSolrClient.Builder(SysConstant.product_core).build();
		  QueryResponse response = null;
		  SolrQuery query = new SolrQuery();
		  query.setFields("product_id,image_url,product_name,min_price,sale_num");
		  query.setQuery("*:*");
		  
		//获取结果
			List<Map<String, Object>>  products =new ArrayList<>();
		  
			//添加产品id数组
			if(product_ids !=null &&product_ids.size()>0){
				StringBuffer pds = new StringBuffer();
				for (int i = 0; i < product_ids.size(); i++) {
					if(i <product_ids.size()-1){
						pds.append("product_id:"+product_ids.get(i)+" OR ");
					}else{
						pds.append("product_id:"+product_ids.get(i));
					}
				}
				query.addFilterQuery(pds.toString());
				
				//搜索
				try {
					response=solr.query(query);
				} catch (SolrServerException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//获取结果
			products = MySolrDoc.toJson( response.getResults());
				
				  //添加销量
				for (int i = 0; i < products.size(); i++) {
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
				
			}
	
		
		/*Long class_id = brandEntityMapper.selectClassIdByBrandId(param.getBrand_id());
		if(class_id == 1L){
		 products = brandEntityMapper.selectBrandMainWallpaperProducts(param.getBrand_id());
		}else if(class_id == 2L){
			 products = brandEntityMapper.selectBrandMainFloorProducts(param.getBrand_id());
		}else if(class_id == 3L){
			 products = brandEntityMapper.selectBrandMainTileProducts(param.getBrand_id());
		}else if(class_id == 4L){
			 products = brandEntityMapper.selectBrandMainDoorProducts(param.getBrand_id());
		}else if(class_id == 5L){
			 products = brandEntityMapper.selectBrandMainPaintProducts(param.getBrand_id());
		}else{
			 products = brandEntityMapper.selectBrandMainMoreProducts(param.getBrand_id());
		}
		for (int i = 0; i < products.size(); i++) {
			ListProductModel product = products.get(i);
			product.setMin_prom_price(product.getMin_price());
			Promotion_type promotion_type=new Promotion_type();
			promotion_type.setId(1);
			Promotion_type promotion_type2=new Promotion_type();
			promotion_type2.setId(6);
			List<Promotion_type> promotion_types =new ArrayList<>();
			promotion_types.add(promotion_type);
			promotion_types.add(promotion_type2);
			product.setPromotion_types(promotion_types);
		}*/
		brandModel.setProducts(products);
		
		brandModel.setBanner(brandBannerEntityMapper.selectByBrandId(param.getBrand_id()));
		
		
		return brandModel;
	}
	


	@Override
	public List<BrandClassifyEntity> selectBrandClassifyAndSeries(BrandEntity param) {
		return	brandClassifyEntityMapper.selectBrandClassifyAndSeries(param.getId());
	}
	
	@Override
	public Page<BrandNewProduct> getNewProductByBrandIdForPage(Page<BrandNewProduct> page) {
		List<BrandNewProduct> list = brandProductEntityMapper.selectNewProductByBrandIdForPage(page);
		for (int i = 0; i <list.size(); i++) {
			List<ProductListModel> products = list.get(i).getProducts();
			for (int j = 0; j < products.size(); j++) {
				List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(products.get(j).getProduct_id());
				promotionTypes.removeAll(Collections.singleton(null));
				products.get(j).setPromotion_types(promotionTypes);
				
				if(promotionTypes == null || !promotionTypes.contains(AppPromotionTypeEnum.secKill.getTypeValue())){
	    			// 折扣工具
	            	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(products.get(j).getProduct_id());
	            	
	    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), products.get(j).getProduct_id());
	    			if(zhekou != null){
	    				Double price = zhekou.getDiscount() * products.get(j).getMin_prom_price();
	    				products.get(j).setMin_prom_price(NumberUtil.round(price, 2, BigDecimal.ROUND_HALF_DOWN));
	    				
	    			}
	    		}
				
			}
		}
		page.setParam(null);
		page.setData(list);
		return page;
	}
/*	@Override
	public Page<BrandNewProduct>  selectNewProductByBrandIdForPage(Page<BrandNewProduct> page) {
		Map<String, Integer> map = (Map<String, Integer>)page.getParam();
		Long brand_id =(long)map.get("brand_id");
		Long class_id = brandEntityMapper.selectClassIdByBrandId(brand_id);
		List<BrandNewProduct> brandNewProducts =new ArrayList<>();
		if(class_id == 1){
			brandNewProducts = brandProductEntityMapper.selectNewWallpaperProductByBrandIdForPage(page);
		}else if(class_id == 2){
			brandNewProducts = brandProductEntityMapper.selectNewFloorProductByBrandIdForPage(page);
		}else if(class_id == 3){
			brandNewProducts = brandProductEntityMapper.selectNewTileProductByBrandIdForPage(page);
		}else if(class_id == 4){
			brandNewProducts = brandProductEntityMapper.selectNewDoorProductByBrandIdForPage(page);
		}else if(class_id == 5){
			brandNewProducts = brandProductEntityMapper.selectNewPaintProductByBrandIdForPage(page);
		}else {
			brandNewProducts = brandProductEntityMapper.selectNewMoreProductByBrandIdForPage(page);
		}
		
		for (int i = 0; i < brandNewProducts.size(); i++) {
			List<ListProductModel> products = brandNewProducts.get(i).getProducts();
		
		for (int j = 0; j < products.size(); j++) {
			ListProductModel product = products.get(j);
			if(product.getSale_num() == null){
				product.setSale_num(0L);
			}
			product.setMin_prom_price(product.getMin_price());
			Promotion_type promotion_type=new Promotion_type();
			promotion_type.setId(1);
			Promotion_type promotion_type2=new Promotion_type();
			promotion_type2.setId(6);
			List<Promotion_type> promotion_types =new ArrayList<>();
			promotion_types.add(promotion_type);
			promotion_types.add(promotion_type2);
			product.setPromotion_types(promotion_types);
		}
		}
		page.setParam(null);
		page.setData(brandNewProducts);
		return page;
	}*/

	@Override
	public String selectEnterpriseAboutByBrandId(BrandModel param) {
		param.setType(0);
		return enterpriseInfoEntityMapper.selectEnterpriseHtmlByBrandId(param);
	}

	@Override
	public String selectEnterpriseDevelopmentByBrandId(BrandModel param) {
		param.setType(1);
		return enterpriseInfoEntityMapper.selectEnterpriseHtmlByBrandId(param);
	}

	@Override
	public List<EnterpriseOneEntity> selectEnterpriseCaseByBrandId(BrandModel param) {
		param.setType(2);
		return enterpriseOneEntityMapper.selectEnterpriseOneByBrandId(param);
	}

	@Override
	public List<EnterpriseOneEntity> selectEnterpriseHonorByBrandId(BrandModel param) {
		param.setType(3);
		return enterpriseOneEntityMapper.selectEnterpriseOneByBrandId(param);
	}

	@Override
	public List<EnterpriseOneEntity> selectEnterpriseMienByBrandId(BrandModel param) {
		param.setType(4);
		return enterpriseOneEntityMapper.selectEnterpriseOneByBrandId(param);
	}

	@Override
	public String selectHotLineByBrandId(Map<String, Object> param) {
		return brandEntityMapper.selectHotLineByBrandId(param);
	}

	@Override
	public List<Shop> selectShopInfoByBrandId(ShopEntity param) {
		List<Shop> shops = shopEntityMapper.selectShopInfoByBrandId(param);
		for (int i = 0; i < shops.size(); i++) {
			/*	Shop shop = shops.get(i);
	        	String address = brandServiceImpl.getWholeAddress(shop.getRegion_code());
			shop.setAddress(address+shop.getAddress());*/
			shops.get(i).setRegion_code(null);
			if(param.getLbs()!=null){
				shops.get(i).setDistance(DistanceUtil.getDistance(shops.get(i).getLbs(), param.getLbs()));
			}else{
				shops.get(i).setDistance(0.0);
			}
			shops.get(i).setLbs(null);
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

	
	

	@Override
	public List<PrepayValue> selectAllPrepayValues() {
		return prepayValueMapper.selectAll();
	}

	@Override
	public List<RegionModel> selectServiceRegion() {
	/*List<RegionWithLetter> cities = new ArrayList<>();
	cities.addAll(regionEntityMapper.selectRegionWithLetterForMunicipality());
	cities.addAll(regionEntityMapper.selectRegionWithLetterNotForMunicipality());*/
		return regionEntityMapper.selectOpenRegion();
	}
	@Override
	public Page<Map<String, Object>> getAllProductForPage(Page<Map<String, Object>> page) {
		super.initProduct();
		Map<String, Object> map = (Map<String, Object>)page.getParam();
		Integer sd =(Integer)map.get("select_id") ;
		query.setStart((page.getPage()-1)*page.getPageSize());
		  query.setRows(page.getPageSize());
		  query.setFields("product_id,image_url,product_name,min_price,min_prom_price,sale_num");
		  query.setQuery("*:*");
		  String searchcontent;
		  query.addFilterQuery("state:1");
		  query.addFilterQuery("-(brand_state:0)");
		  query.addFilterQuery("partner_state:2");
		  //添加搜索字段条件
		  if(!map.get("searchcontent").equals("")){
			   searchcontent = map.get("searchcontent").toString();
			  StringBuffer sb = new StringBuffer();
			  String[] split = searchcontent.split(" ");
			  
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
		  query.addFilterQuery("brand_id:"+map.get("brand_id") );
		  if(page.getArrModel().getIds().length>0){
				StringBuffer ids = new StringBuffer();
			  for (int i = 0; i < page.getArrModel().getIds().length; i++) {
				if(i<page.getArrModel().getIds().length-1){
					ids.append("series_ids:"+page.getArrModel().getIds()[i]+" OR ");
				}else{
					ids.append("series_ids:"+page.getArrModel().getIds()[i]);
				}
			}
			  
			  query.addFilterQuery(ids.toString());
			   
		  }
		  
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
			   query.addFilterQuery(sb.toString());*/
				  query.addSort("product_id",ORDER.desc);break;
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
            List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(Long.valueOf(product.get("product_id")+""));
            product.put("promotion_types", promotionTypes);
            
            if(promotionTypes == null || !promotionTypes.contains(AppPromotionTypeEnum.secKill.getTypeValue())){
    			// 折扣工具
            	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(Long.valueOf(product.get("product_id")+""));
            	
    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), Long.valueOf(product.get("product_id")+""));
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
	public List<RegionWithLetter> selectServiceCityRegion() {
		List<RegionWithLetter> cities = new ArrayList<>();
			cities.addAll(regionEntityMapper.selectCityRegionWithLetterForMunicipality());
			cities.addAll(regionEntityMapper.selectCityRegionWithLetterNotForMunicipality());
			return cities;
	}

	@Override
	public boolean selectWhetherInRegion(Map<String, String> param) {
		return regionEntityMapper.inRegion(param);
	}

	@Override
	public BrandModel getSqlBrandMain(CustomerCollectionEntity param) {
		BrandModel brandModel = new BrandModel();
		List<ProductListModel> list = brandEntityMapper.selectBrandMainProducts(param.getBrand_id());
		 for (int i = 0; i < list.size(); i++) {
			 List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(list.get(i).getProduct_id());
				list.get(i).setPromotion_types(promotionTypes);
				
				if(promotionTypes == null || !promotionTypes.contains(AppPromotionTypeEnum.secKill.getTypeValue())){
					
					// 折扣工具
	    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(param.getBrand_id(), list.get(i).getProduct_id());
	    			if(zhekou != null){
	    				list.get(i).setMin_prom_price(NumberUtil.round(zhekou.getDiscount() * list.get(i).getMin_price(),2,BigDecimal.ROUND_HALF_DOWN));
	    			}
				}
			}
			brandModel.setProductList(list);
			
			brandModel.setBanner(brandBannerEntityMapper.selectByBrandId(param.getBrand_id()));
			
		return brandModel;
	}

	/*@Override
	public Page<ProductListModel> getSqlAllProductForPage(Page<ProductListModel> page) {
		Map<String, String> map = (Map<String, String>) page.getParam();
		long time1 = System.currentTimeMillis();
		System.out.println("time1:"+time1);
		 StringBuffer sb = new StringBuffer();
		 if(page.getArrModel().getIds().length>0){
			 sb.append(" RIGHT JOIN ( SELECT product_id FROM t_biz_brand_prod_series WHERE  series_id IN (");
			 for (int i = 0; i < page.getArrModel().getIds().length; i++) {
				 if(i<page.getArrModel().getIds().length-1){
					 sb.append(page.getArrModel().getIds()[i]+","); 
				 }else{
					 sb.append(page.getArrModel().getIds()[i]+")) series on p.id = series.product_id"); 
				 }
			}
			 map.put("sql", sb.toString());
			 page.setParam(map);
		 }
		 
		 
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
			
		long time2 = System.currentTimeMillis();
		System.out.println("time2:"+(time2-time1));
		List<ProductListModel> products = brandEntityMapper.selectBrandAllProductsForPage(page);
		long time3 = System.currentTimeMillis();
		System.out.println("time3:"+(time3-time2));
		  for (int i = 0; i < products.size(); i++) {
			  products.get(i).setPromotion_types(brandProductEntityMapper.selectPromotionTypesByProductId(products.get(i).getProduct_id()));
			}
			long time4 = System.currentTimeMillis();
			System.out.println("time4:"+(time4-time3));
			 page.setData(products);
		page.setArrModel(null);
		page.setParam(null);
		return page;
	}*/

	
	@Override
	public List<EnterpriseTwoEntity> selectEnterpriseTwoByOneId(Long one_id) {
		return	enterpriseTwoEntityMapper.selectEnterpriseTwoByOneId(one_id);
	}

	@Override
	public List<Brand> selectBrandsByRegionCodeAndClassIds(Map<String, Object> param) {
		return brandEntityMapper.selectBrandByRegionCodeAndClassIds(param);
	}



}
