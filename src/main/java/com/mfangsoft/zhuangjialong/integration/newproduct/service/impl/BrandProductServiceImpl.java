package com.mfangsoft.zhuangjialong.integration.newproduct.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.app.salesnum.mapper.SalesNumEntityMapper;
import com.mfangsoft.zhuangjialong.app.salesnum.model.SalesNumEntity;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BaseBrandProdImgEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BaseBrandProdSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdAttrValueEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdImgEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdSalesAttrEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdImgEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductService;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildEnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
@Service()
public class BrandProductServiceImpl extends SuperCore implements BrandProductService {

	@Autowired
	private BrandProdSalesAttrEntityMapper prodSalesAttrEntityMapper;
	@Autowired
	private BaseBrandProdSeriesEntityMapper baseBrandProdSeriesEntityMapper;
	@Autowired
	private   BrandNewProductEntityMapper brandProductEntityMapper;
	@Autowired
	private  BaseBrandProdImgEntityMapper baseBrandProdImgEntityMapper;
	@Autowired 
	private  BrandProdAttrValueEntityMapper prodAttrValueEntityMapper;
	@Autowired
	private SalesNumEntityMapper salesNumEntityMapper;
	@Autowired
	private  ProductCoreService productCoreService;
	@Autowired
	private BrandProdAttrValueEntityMapper brandProdAttrValueEntityMapper;
	@Autowired
	private BrandProdImgEntityMapper brandProdImgEntityMapper;
	
	private  final  static  Logger  log =LoggerFactory.getLogger(BrandProductServiceImpl.class);
	
	
	@Override
	public Boolean addBrandProduct(BrandProductEntity brandProductEntity) {
		
		// TODO Auto-generated method stub
		UserEntity u = UserContext.getCurrentUser();
		BrandEntity brandEntity =null;
		BuildEnterpriseEntity enterpriseEntity =null;
		BuildClassEntity buildClassEntity =null;
		PartnerEntity partnerEntity =null;
		 if (u.getUser_type() == UserType.BRAND.getIndex()) {
			 brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			 enterpriseEntity = brandEntity.getBuildEnterpriseEntity();
			 buildClassEntity = enterpriseEntity.getBuildClassEntity();
			 partnerEntity = brandEntity.getPartnerEntity();
			if(brandEntity != null && brandEntity.getBrand_idintify_type() != null && brandEntity.getBrand_idintify_type() == 1){
				brandProductEntity.setSell_type(SysConstant.seller_type_online);
			}
			
		}
	
	   this.popuBrandProductEntity(brandProductEntity);
	   brandProductEntity.setCreate_time(new Date());
	   
	   brandProductEntityMapper.insertSelective(brandProductEntity);
	   Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	   SolrInputDocument document = new SolrInputDocument();
	   document.addField("product_id", brandProductEntity.getId()+"");//id
	   //System.out.println(brandProductEntity.getId());
	   document.addField("product_name", brandProductEntity.getProduct_title());//name
	   //System.out.println(brandProductEntity.getProduct_title());
	   document.addField("description", brandProductEntity.getDescription());//详情
	   //System.out.println(brandProductEntity.getDescription());
	   document.addField("brand_id", brandProductEntity.getBrand_id());//品牌id
	   document.addField("brand_state", 1);//品牌状态
	   document.addField("partner_state", 2);//合伙人状态
	   //System.out.println(brandProductEntity.getBrand_id());
	   document.addField("hot_line", brandEntity.getHot_line());//品牌热线
	   //System.out.println(brandEntity.getHot_line());
		document.addField("enterprise_id", brandProductEntity.getEnterprise_id());
		//System.out.println(brandProductEntity.getEnterprise_id());
		document.addField("brand_name", enterpriseEntity.getName());//品牌名称
		//System.out.println(enterpriseEntity.getName());
		document.addField("brand_imgurl", enterpriseEntity.getImgurl());//品牌图片
		//System.out.println(enterpriseEntity.getImgurl());
		document.addField("class_id", brandProductEntity.getClass_id());//品类
		//System.out.println(brandProductEntity.getClass_id());
		document.addField("class_name", buildClassEntity.getName());//品类名
		//System.out.println(buildClassEntity.getName());
		document.addField("citypartner_id", brandProductEntity.getPartner_id());//城市合伙人
		//System.out.println(brandProductEntity.getPartner_id());
		document.addField("region_code", partnerEntity.getRegion_id());//区域
		//System.out.println(partnerEntity.getRegion_id());
		document.addField("state", brandProductEntity.getState());//状态
		//System.out.println(brandProductEntity.getState());
	   document.addField("create_time", DateUtils.formatDate_(brandProductEntity.getCreate_time()));//创建时间
	   //System.out.println(brandProductEntity.getCreate_time());
		if(brandProductEntity.getNew_time()!=null){
			document.addField("new_time", DateUtils.formatDateYmd(brandProductEntity.getNew_time()));//新品时间
		}
	   //System.out.println(brandProductEntity.getNew_time());
	   document.addField("isnew", brandProductEntity.getIsnew());//是否新品
	   //System.out.println(brandProductEntity.getIsnew());
	   document.addField("sort", brandProductEntity.getProduct_sort());//产品排序
	   //System.out.println(brandProductEntity.getProduct_sort());
	   document.addField("unit", brandProductEntity.getProduct_unit());//计价单位
	   //System.out.println( brandProductEntity.getProduct_unit());
	   document.addField("sales_model", brandProductEntity.getSales_model());//销售方式
	   //System.out.println( brandProductEntity.getSales_model());
		document.addField("percent", brandProductEntity.getPercent());//百分比
		//System.out.println( brandProductEntity.getPercent());
		
	   List<Long> series_ids = new ArrayList<>();
		
	  List<BaseBrandProdSeriesEntity>	baseBrandProdSeriesEntities =brandProductEntity.getBaseBrandProdSeriesEntities();
	  
	  for (BaseBrandProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
		  
		  
		  
		  
		  baseBrandProdSeriesEntity.setProduct_id(brandProductEntity.getId());
		  
		  baseBrandProdSeriesEntityMapper.insertSelective(baseBrandProdSeriesEntity);
		  series_ids.add(baseBrandProdSeriesEntity.getSeries_id());
	  }
	  document.addField("series_ids", series_ids);//系列id
	  List<String> images = new ArrayList<>();
	  List<String> case_images = new ArrayList<>();
	  List<BaseBrandProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
	  String img_url = null;
	  for(int i=0;i<baseBrandProdImgEntities.size();i++){
		  BaseBrandProdImgEntity baseBrandProdImgEntity =baseBrandProdImgEntities.get(i);
		  
		  baseBrandProdImgEntity.setProduct_id(brandProductEntity.getId());
		  baseBrandProdImgEntity.setImg_sort(i);
		  baseBrandProdImgEntityMapper.insertSelective(baseBrandProdImgEntity);
		  
		  if(baseBrandProdImgEntities.get(i).getType()==1){
			  images.add(baseBrandProdImgEntities.get(i).getImgurl());
			  if(img_url==null){
				  img_url = baseBrandProdImgEntities.get(i).getImgurl();
			  }
		  }else{
			  case_images.add(baseBrandProdImgEntities.get(i).getImgurl());
		  }
	  }
	  document.addField("images", images);//轮播图
	  //System.out.println( images);
	  document.addField("case_images", case_images);//案例图
	  //System.out.println( case_images);
	  document.addField("image_url", img_url);//案例图
	  //System.out.println( img_url);
	  List<BaseBrandProdSalesAttrEntity> prodSalesAttrEntities=brandProductEntity.getServerprodSalesAttrEntity();
	  List<Double> price=new ArrayList<>();
	  List<String> color = new ArrayList<>();
	  List<String> color_model = new ArrayList<>();
	  List<String> model = new ArrayList<>();
	  for (BaseBrandProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
		  price.add(brandProdSalesAttrEntity.getPrice());
		  color.add(brandProdSalesAttrEntity.getColor());
		  model.add(brandProdSalesAttrEntity.getModel());
		  color_model.add(brandProdSalesAttrEntity.getColor()+" "+brandProdSalesAttrEntity.getModel());
		  brandProdSalesAttrEntity.setData_flag("ins");
		   
		  brandProdSalesAttrEntity.setProduct_id(brandProductEntity.getId());
		  prodSalesAttrEntityMapper.insertSelective(brandProdSalesAttrEntity);
	  }
	  document.addField("min_price", Collections.min(price));
	  //System.out.println( Collections.min(price));
	  document.addField("min_prom_price", Collections.min(price));
	  document.addField("max_price", Collections.max(price));
	  //System.out.println( Collections.max(price));
	  document.addField("color", color);
	  //System.out.println( color);
	  document.addField("color_model", color_model);
	  //System.out.println( color_model);
		document.addField("model", model);
		//System.out.println( model);
	 List<BrandProdAttrValueEntity> brandProdAttrValueEntities= brandProductEntity.getBrandProdAttrValueEntities();
	 
	 for (BrandProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
		 brandProdAttrValueEntity.setClass_id(brandProductEntity.getClass_id());
		 brandProdAttrValueEntity.setProduct_id(brandProductEntity.getId());
		 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
	}
		Map<String, String> arrtMap = brandProdAttrValueEntityMapper.selectValuesByProductIdForProductCore(brandProductEntity.getId());
	 document.addField("attr_value", arrtMap.get("attr_value").split(","));
		document.addField("attr_ids", arrtMap.get("attr_ids").split(","));
		document.addField("attr_names", arrtMap.get("attr_names").split(","));
		document.addField("value_ids", arrtMap.get("value_ids").split(","));
	 BrandProdAttrValueName attrValueName=brandProductEntityMapper.selectproductAttrValueNameByProductId(brandProductEntity.getId());
	 RedisUtils.getRedisTemplate().opsForHash().put("brand_product_attr_value_name", brandProductEntity.getId()+"", attrValueName);
	
	 
	 SalesNumEntity salesNumEntity = new SalesNumEntity();
	 
	 salesNumEntity.setProduct_id(brandProductEntity.getId());
	 
	 
	 Random r= new Random();
	 
	 int  l=r.nextInt(100);
	 
	 salesNumEntity.setSales_num(new Long(l));
	 
	 salesNumEntityMapper.insertSelective(salesNumEntity);
	 document.addField("sale_num", salesNumEntity.getSales_num().intValue());
	 //System.out.println( salesNumEntity.getSales_num().intValue());
	 docs.add(document);
	 super.initProduct();
		try {
			UpdateResponse add = solr.add(docs);
			solr.commit();
			if(add.getStatus()!=0){
				System.out.println("添加solr错误信息："+add.getStatus());
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void popuBrandProductEntity(BrandProductEntity brandProductEntity){
		
		UserEntity userEntity = UserContext.getCurrentUser();
		
		if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			BrandEntity  brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			brandProductEntity.setBrand_id(brandEntity.getId());
			
			brandProductEntity.setEnterprise_id(brandEntity.getPartnerEntity().getEnterpriseEntity().getId());
			brandProductEntity.setBuild_brand_id(brandEntity.getBuildEnterpriseEntity().getId());
			brandProductEntity.setPartner_id(brandEntity.getCitypartner_id());
			brandProductEntity.setClass_id(brandEntity.getBuildEnterpriseEntity().getClass_id());
			
		}
		
	}

	
	@Override
	public Boolean modfiyBrandProduct(BrandProductEntity brandProductEntity) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		UserEntity u = UserContext.getCurrentUser();
		BrandEntity brandEntity =null;
		BuildEnterpriseEntity enterpriseEntity =null;
		BuildClassEntity buildClassEntity =null;
		PartnerEntity partnerEntity =null;
		 if (u.getUser_type() == UserType.BRAND.getIndex()) {
			 brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			 enterpriseEntity = brandEntity.getBuildEnterpriseEntity();
			 buildClassEntity = enterpriseEntity.getBuildClassEntity();
			 partnerEntity = brandEntity.getPartnerEntity();
			if(brandEntity != null && brandEntity.getBrand_idintify_type() != null && brandEntity.getBrand_idintify_type() == 1){
				brandProductEntity.setSell_type(SysConstant.seller_type_online);
			}
			
		}
		
		   this.popuBrandProductEntity(brandProductEntity);
		   Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		   SolrInputDocument document = new SolrInputDocument();
		   document.addField("product_id", brandProductEntity.getId()+"");//id
		   //System.out.println(brandProductEntity.getId());
		   document.addField("product_name", brandProductEntity.getProduct_title());//name
		   //System.out.println(brandProductEntity.getProduct_title());
		   document.addField("description", brandProductEntity.getDescription());//详情
		   //System.out.println(brandProductEntity.getDescription());
		   document.addField("brand_id", brandProductEntity.getBrand_id());//品牌id
		   document.addField("brand_state", 1);//品牌状态
		   document.addField("partner_state", 2);//合伙人状态
		   //System.out.println(brandProductEntity.getBrand_id());
		   document.addField("hot_line", brandEntity.getHot_line());//品牌热线
		   //System.out.println(brandEntity.getHot_line());
			document.addField("enterprise_id", brandProductEntity.getEnterprise_id());
			//System.out.println(brandProductEntity.getEnterprise_id());
			document.addField("brand_name", enterpriseEntity.getName());//品牌名称
			//System.out.println(enterpriseEntity.getName());
			document.addField("brand_imgurl", enterpriseEntity.getImgurl());//品牌图片
			//System.out.println(enterpriseEntity.getImgurl());
			document.addField("class_id", brandProductEntity.getClass_id());//品类
			//System.out.println(brandProductEntity.getClass_id());
			document.addField("class_name", buildClassEntity.getName());//品类名
			//System.out.println(buildClassEntity.getName());
			document.addField("citypartner_id", brandProductEntity.getPartner_id());//城市合伙人
			//System.out.println(brandProductEntity.getPartner_id());
			document.addField("region_code", partnerEntity.getRegion_id());//区域
			//System.out.println(partnerEntity.getRegion_id());
			if(brandProductEntity.getNew_time()!=null){
				document.addField("new_time", DateUtils.formatDateYmd(brandProductEntity.getNew_time()));//新品时间
			}
		   //System.out.println(brandProductEntity.getNew_time());
		   document.addField("isnew", brandProductEntity.getIsnew());//是否新品
		   //System.out.println(brandProductEntity.getIsnew());
		   document.addField("sort", brandProductEntity.getProduct_sort());//产品排序
		   //System.out.println(brandProductEntity.getProduct_sort());
		   document.addField("unit", brandProductEntity.getProduct_unit());//计价单位
		   //System.out.println( brandProductEntity.getProduct_unit());
		   document.addField("sales_model", brandProductEntity.getSales_model());//销售方式
		   //System.out.println( brandProductEntity.getSales_model());
			document.addField("percent", brandProductEntity.getPercent());//百分比
			//System.out.println( brandProductEntity.getPercent());
			
		   List<Long> series_ids = new ArrayList<>();
		   BaseBrandProductEntity  productEntity =brandProductEntityMapper.selectByPrimaryKey(brandProductEntity.getId());
		   
		   brandProductEntity.setState(productEntity.getState());
		   
		   brandProductEntity.setCreate_time(productEntity.getCreate_time());
		   brandProductEntity.setTmpflag(productEntity.getTmpflag());
		   document.addField("state", productEntity.getState());//状态
		   //System.out.println(brandProductEntity.getState());
		   if(productEntity.getCreate_time()!=null){
			   document.addField("create_time", DateUtils.formatDate_(productEntity.getCreate_time()));//创建时间
		   }
		   //System.out.println(brandProductEntity.getCreate_time());
		   brandProductEntityMapper.updateByPrimaryKeyWithBLOBs(brandProductEntity);
			
		  List<BaseBrandProdSeriesEntity>	baseBrandProdSeriesEntities =brandProductEntity.getBaseBrandProdSeriesEntities();
		  baseBrandProdSeriesEntityMapper.deleteProdSeriesByProductId(brandProductEntity.getId());
		  for (BaseBrandProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
			  
			  
				  baseBrandProdSeriesEntity.setProduct_id(brandProductEntity.getId());
				  baseBrandProdSeriesEntityMapper.insertSelective(baseBrandProdSeriesEntity);
				  series_ids.add(baseBrandProdSeriesEntity.getSeries_id());
		  }
		  document.addField("series_ids", series_ids);//系列id
		  List<String> images = new ArrayList<>();
		  List<String> case_images = new ArrayList<>();
		  List<BaseBrandProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
		  String img_url = null;
		  brandProdImgEntityMapper.deleteByProductId(brandProductEntity.getId());
		  for(int i=0;i<baseBrandProdImgEntities.size();i++){
			  BaseBrandProdImgEntity baseBrandProdImgEntity =baseBrandProdImgEntities.get(i);
			  baseBrandProdImgEntity.setImg_sort(i);
				  baseBrandProdImgEntity.setProduct_id(brandProductEntity.getId());
				  baseBrandProdImgEntityMapper.insertSelective(baseBrandProdImgEntity);
			  
			  if(baseBrandProdImgEntities.get(i).getType()==1){
				  images.add(baseBrandProdImgEntities.get(i).getImgurl());
				  if(img_url==null){
					  img_url = baseBrandProdImgEntities.get(i).getImgurl();
				  }
			  }else{
				  case_images.add(baseBrandProdImgEntities.get(i).getImgurl());
			  }
		  }
		  document.addField("images", images);//轮播图
		  //System.out.println( images);
		  document.addField("case_images", case_images);//案例图
		  //System.out.println( case_images);
		  document.addField("image_url", img_url);//案例图
		  //System.out.println( img_url);
		  
		  List<Double> price=new ArrayList<>();
		  List<String> color = new ArrayList<>();
		  List<String> color_model = new ArrayList<>();
		  List<String> model = new ArrayList<>();
		  
		
		  
		  List<BaseBrandProdSalesAttrEntity> prodSalesAttrEntities=brandProductEntity.getServerprodSalesAttrEntity();
		  
		  for (BaseBrandProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
			  
			  if(brandProdSalesAttrEntity.getId()==null){
			 
			brandProdSalesAttrEntity.setData_flag("ins");
			  brandProdSalesAttrEntity.setProduct_id(brandProductEntity.getId());
			  prodSalesAttrEntityMapper.insertSelective(brandProdSalesAttrEntity);
			  }else{
				  brandProdSalesAttrEntity.setData_flag("upd");
				  prodSalesAttrEntityMapper.updateByPrimaryKeySelective(brandProdSalesAttrEntity);
			  }
			  price.add(brandProdSalesAttrEntity.getPrice());
			  color.add(brandProdSalesAttrEntity.getColor());
			  model.add(brandProdSalesAttrEntity.getModel());
			  color_model.add(brandProdSalesAttrEntity.getColor()+" "+brandProdSalesAttrEntity.getModel());
		  }
		  document.addField("min_price", Collections.min(price));
		  //System.out.println( Collections.min(price));
		  document.addField("min_prom_price", Collections.min(price));
		  document.addField("max_price", Collections.max(price));
		  //System.out.println( Collections.max(price));
		  document.addField("color", color);
		  //System.out.println( color);
		  document.addField("color_model", color_model);
		  //System.out.println( color_model);
			document.addField("model", model);
			//System.out.println( model);
		  prodAttrValueEntityMapper.deleteProdattValueByProductId(brandProductEntity.getId());
		 List<BrandProdAttrValueEntity> brandProdAttrValueEntities= brandProductEntity.getBrandProdAttrValueEntities();
		 
		 for (BrandProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
			 
			 brandProdAttrValueEntity.setProduct_id(brandProductEntity.getId());
			 
			 if(brandProdAttrValueEntity.getAttr_id().longValue()==1L){
				 brandProdAttrValueEntity.setId(null);
				 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
				
			 }else{
				 
				 BrandProdAttrValueEntity attrValueEntity =prodAttrValueEntityMapper.selectProdattrValueByProductIdAndAttrId(brandProdAttrValueEntity);
				 
				 if(attrValueEntity==null){
					 brandProdAttrValueEntity.setId(null);
					 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
				 }else{
					 
					 brandProdAttrValueEntity.setId(attrValueEntity.getId());
					 prodAttrValueEntityMapper.updateByPrimaryKeySelective(brandProdAttrValueEntity);
				 }
				 
				
			 }
			
			 
		}
		 Map<String, String> arrtMap = brandProdAttrValueEntityMapper.selectValuesByProductIdForProductCore(brandProductEntity.getId());
		 document.addField("attr_value", arrtMap.get("attr_value").split(","));
			document.addField("attr_ids", arrtMap.get("attr_ids").split(","));
			document.addField("attr_names", arrtMap.get("attr_names").split(","));
			document.addField("value_ids", arrtMap.get("value_ids").split(","));
		 
		 BrandProdAttrValueName attrValueName=brandProductEntityMapper.selectproductAttrValueNameByProductId(brandProductEntity.getId());
		 
		 //RedisUtils.getRedisTemplate().opsForHash().delete("brand_product_attr_value_name", brandProductEntity.getId()+"");
		 RedisUtils.getRedisTemplate().opsForHash().put("brand_product_attr_value_name", brandProductEntity.getId()+"", attrValueName);
		 SalesNumEntity salesNumEntity = salesNumEntityMapper.selectByProductId(brandProductEntity.getId());
		 if(salesNumEntity!=null){
		 document.addField("sale_num", salesNumEntity.getSales_num().intValue());
		 }else{
			 Random r= new Random();
               int  l=r.nextInt(100);
			 salesNumEntity.setSales_num(new Long(l));
			 salesNumEntityMapper.insertSelective(salesNumEntity);
			document.addField("sale_num", salesNumEntity.getSales_num().intValue());
		 }
		 //System.out.println( salesNumEntity.getSales_num().intValue());
		 docs.add(document);
		 super.initProduct();
			try {
				UpdateResponse delete = solr.deleteById(brandProductEntity.getId()+"");
				solr.commit();
				if(delete.getStatus()!=0){
					System.out.println("添加solr错误信息："+delete.getStatus());
				}
				UpdateResponse add = solr.add(docs);
				solr.commit();
				if(add.getStatus()!=0){
					System.out.println("添加solr错误信息："+add.getStatus());
				}
			} catch (SolrServerException | IOException e) {
				e.printStackTrace();
				return false;
			}
		
		return true;
	}

	@Override
	public BrandProductEntity getBrandProductById(Long id) {
		// TODO Auto-generated method stub
		

		BrandProductEntity  brandProductEntity =brandProductEntityMapper.selectAllByPrimaryKey(id);
		
		//brandProductEntity.setBrandProdAttrValueEntities();
		
		List<BrandProdAttrValueEntity> brandProdAttrValueEntities=prodAttrValueEntityMapper.selectProdattrValueByProductId(id);
		
		
		List<ClassAttrEntity> attrEntities=(List<ClassAttrEntity>) RedisUtils.getRedisTemplate().opsForHash().get("class_attr", brandProductEntity.getClass_id()+"");
		for (BrandProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
			
			//BrandProdAttrValueEntity attrValueEntity = new BrandProdAttrValueEntity();
			if(attrEntities != null){
			for (ClassAttrEntity classAttrEntity : attrEntities) {
				
				if(brandProdAttrValueEntity.getAttr_id().longValue()==classAttrEntity.getId().longValue())
				{
					brandProdAttrValueEntity.setAttrEntity(classAttrEntity);
					
					//attrValueEntity.setAttr_value(brandProdAttrValueEntity.getAttr_value());
					
					
				}
			}
			}
			
			//attrValueEntities.add(attrValueEntity);
			
		}
		
		brandProductEntity.setBrandProdAttrValueEntities(brandProdAttrValueEntities);
		brandProductEntity.setProductViewAttrAndVaule(brandProductEntityMapper.selectViewbyproduct(id));
		brandProductEntity.setProdSalesAttrEntity(prodSalesAttrEntityMapper.getBrandProdSalesAttrByProductId(id));
		return brandProductEntity;
	}

	@Override
	public Page<Map<String, Object>> getBrandProductForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		this.popuPage(page);
		Long t=System.currentTimeMillis();
		
		page.setData(brandProductEntityMapper.selectBrandProductForPage(page));
//		List<Map<String, Object>> lss= page.getData();
//		for (Map<String, Object> map : lss) {
//			
//			BrandProdAttrValueName attrValueName=getAttrValueName(map.get("id").toString());
//			
//			if(attrValueName!=null){
//				log.info("redis data");
//				map.put("space", attrValueName.getSpace());
//				
//				map.put("style", attrValueName.getStyle());
//				map.put("madin", attrValueName.getMadin());
//			}else{
//				log.info(" no redis data");
//				BrandProdAttrValueName as =brandProductEntityMapper.selectproductAttrValueNameByProductId(new Long(map.get("id").toString()));
//				
//				if(as!=null){
//				map.put("space", as.getSpace());
//				
//				map.put("style", as.getStyle());
//				map.put("madin", as.getMadin());
//				}
//			}
//			
//			
//			
//		}
		
		return page;
	}
	
	
	
	private BrandProdAttrValueName  getAttrValueName (String id){
		
		
		try {
			return (BrandProdAttrValueName) RedisUtils.getRedisTemplate().opsForHash().get("brand_product_attr_value_name", id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean removeBrandProdSales(Long id) {
		// TODO Auto-generated method stub
		
		Long product_Id = prodSalesAttrEntityMapper.getProductIdBySaleId(id);
		prodSalesAttrEntityMapper.updateProdSalesAttr(id);
		  List<Double> price=new ArrayList<>();
		  List<String> color = new ArrayList<>();
		  List<String> color_model = new ArrayList<>();
		  List<String> model = new ArrayList<>();
		  List<BrandProdSalesAttrEntity> prodSalesAttrEntities = prodSalesAttrEntityMapper.selectProductSalesByProductId(product_Id);
		  
		  for (BaseBrandProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
			  price.add(brandProdSalesAttrEntity.getPrice());
			  color.add(brandProdSalesAttrEntity.getColor());
			  model.add(brandProdSalesAttrEntity.getModel());
			  color_model.add(brandProdSalesAttrEntity.getColor()+" "+brandProdSalesAttrEntity.getModel());
		  }
		  super.initProduct();
		  SolrInputDocument document = new SolrInputDocument();
		  Map<String, Object> priceMap = new HashMap<>();
		  Map<String, Object> maxPriceMap = new HashMap<>();
		  Map<String, Object> colorMap = new HashMap<>();
		  Map<String, Object> color_modelMap = new HashMap<>();
		  Map<String, Object> modelMap = new HashMap<>();
		  
		  document.addField("product_id", product_Id+"");
		  
		  priceMap.put("set", Collections.min(price));
		  maxPriceMap.put("set", Collections.max(price));
		  colorMap.put("set", color);
		  color_modelMap.put("set", color_model);
		  modelMap.put("set", model);
		  document.addField("min_price", priceMap);
		  //System.out.println( Collections.min(price));
		  document.addField("min_prom_price", priceMap);
		  document.addField("max_price", maxPriceMap);
		  //System.out.println( Collections.max(price));
		  document.addField("color", colorMap);
		  //System.out.println( color);
		  document.addField("color_model", color_modelMap);
		  //System.out.println( color_model);
			document.addField("model", modelMap);
			//System.out.println( model);
			try {
				solr.add(document);
				solr.commit();
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
	}

	@Override
	public Page<Map<String, Object>> getBrandDevProductListForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		page.setData(brandProductEntityMapper.getBrandDevProductListForPage(page));
		return page;
	}
	
	private void popuPage(Page<Map<String, Object>> page){
		
		Map<String,Object> map;
		
		if(page.getParam()==null){
			
			map= new HashMap<>();
			
		}else{
			
			map=(Map<String, Object>) page.getParam();
		}
		
		UserEntity userEntity = UserContext.getCurrentUser();
		if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue())
		{
			
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			
			map.put("brand_id", brandEntity.getId());
		}
		if(userEntity.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue())
		{
			
			EnterpriseEntity brandEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
			map.put("build_brand_id", brandEntity.getBuildEnterpriseEntity().getId());
		}
		
		if(userEntity.getUser_type().intValue()==UserType.PARTNER.getIndex().intValue())
		{
			
			PartnerEntity partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", partnerEntity.getId());
			
		}
		
	}

	

	
	@Override
	public Boolean updateBrandProduct(List<Long> list, Integer state) {
		// TODO Auto-generated method stub
		
		for (Long long1 : list) {
			Map<String,Object> map = new HashMap<>();
			
			map.put("state", state);
			map.put("id", long1);
			brandProductEntityMapper.updateBrandProduct(map);
		}
		
		return true;
	}

	@Override
	public BrandProdAttrValueName selectproductAttrValueNameByProductId(Long id) {
		// TODO Auto-generated method stub
		return brandProductEntityMapper.selectproductAttrValueNameByProductId(id);
	}

	@Override
	public List<BrandProdAttrValueName> selectproductAttrValueName() {
		// TODO Auto-generated method stub
		return brandProductEntityMapper.selectproductAttrValueName();
	}

	@Override
	public Boolean deleteProduct(Long id) {
		// TODO Auto-generated method stub
		
		brandProductEntityMapper.deleteByPrimaryKey(id);
		
		brandProductEntityMapper.deleteProdAttrvalue(id);
		
		brandProductEntityMapper.deleteProdImage(id);
		
		brandProductEntityMapper.deleteProdsalesattr(id);
		
		brandProductEntityMapper.deleteProdSeries(id);
		
		return true;
	}

	@Override
	public List<Map<String, Object>> selectAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return brandProductEntityMapper.selectAllBrandProduct(map);
	}

	@Override
	public List<Map<String, Object>> selectEntAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return brandProductEntityMapper.selectAllEntProduct(map);
	}

	@Override
	public Boolean deleteEntProduct(Long id) {
		// TODO Auto-generated method stub
		
		brandProductEntityMapper.deleteEntProduct(id);
		
		brandProductEntityMapper.deleteEntProdAttrvalue(id);
		
		brandProductEntityMapper.deleteEntProdSeries(id);
		
		brandProductEntityMapper.deleteEntProdsalesattr(id);
		
		
		brandProductEntityMapper.deleteEntProdImage(id);
		
		return true;
	}

	@Override
	public Boolean selectPromotionProductByProductId(List<Long> product_id) {
		// TODO Auto-generated method stub
		
		List<Long> ls =brandProductEntityMapper.selectPromotionProductByProductId(product_id);
		
		if(ls!=null&&ls.size()>0){
			
			return false;
		}
		return true;
	}

	@Override
	public Page<Map<String,Object>> selectProductForBanner(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		UserEntity userEntity=UserContext.getCurrentUser();
		
		if(userEntity.getUser_type().intValue()==UserType.PARTNER.getIndex().intValue()){
			
			Map<String,Object> map=(Map<String, Object>) page.getParam();
			
			PartnerEntity  partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();
			
			map.put("partner_id", partnerEntity.getId());
		}
if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			Map<String,Object> map=(Map<String, Object>) page.getParam();
			
			BrandEntity  partnerEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			
			map.put("brandId", partnerEntity.getId());
		}
		
		page.setData(brandProductEntityMapper.selectProductForBannerPage(page));
		return page;
	}

	@Override
	public BrandProductEntity getBrandProductBannerById(Long id) {
		// TODO Auto-generated method stub
		
          BrandProductEntity  brandProductEntity =brandProductEntityMapper.selectAllByPrimaryKey(id);
		
		brandProductEntity.setProductViewAttrAndVaule(brandProductEntityMapper.selectViewbyproduct(id));
		
		
		return brandProductEntity;
	}

	@Override
	public Boolean addBrandProductForCopy(BrandProductEntity brandProductEntity) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		UserEntity u = UserContext.getCurrentUser();
		BrandEntity brandEntity =null;
		BuildEnterpriseEntity enterpriseEntity =null;
		BuildClassEntity buildClassEntity =null;
		PartnerEntity partnerEntity =null;
		 if (u.getUser_type() == UserType.BRAND.getIndex()) {
			 brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			 enterpriseEntity = brandEntity.getBuildEnterpriseEntity();
			 buildClassEntity = enterpriseEntity.getBuildClassEntity();
			 partnerEntity = brandEntity.getPartnerEntity();
			if(brandEntity != null && brandEntity.getBrand_idintify_type() != null && brandEntity.getBrand_idintify_type() == 1){
				brandProductEntity.setSell_type(SysConstant.seller_type_online);
			}
			
		}
		
		  //this.popuBrandProductEntity(brandProductEntity);
		   brandProductEntity.setCreate_time(new Date());
		   
		   
		   brandProductEntityMapper.insertSelective(brandProductEntity);
		   Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		   SolrInputDocument document = new SolrInputDocument();
		   document.addField("product_id", brandProductEntity.getId()+"");//id
		   //System.out.println(brandProductEntity.getId());
		   document.addField("product_name", brandProductEntity.getProduct_title());//name
		   //System.out.println(brandProductEntity.getProduct_title());
		   document.addField("description", brandProductEntity.getDescription());//详情
		   //System.out.println(brandProductEntity.getDescription());
		   document.addField("brand_id", brandProductEntity.getBrand_id());//品牌id
		   document.addField("brand_state", 1);//品牌状态
		   document.addField("partner_state", 2);//合伙人状态
		   //System.out.println(brandProductEntity.getBrand_id());
		   document.addField("hot_line", brandEntity.getHot_line());//品牌热线
		   //System.out.println(brandEntity.getHot_line());
			document.addField("enterprise_id", brandProductEntity.getEnterprise_id());
			//System.out.println(brandProductEntity.getEnterprise_id());
			document.addField("brand_name", enterpriseEntity.getName());//品牌名称
			//System.out.println(enterpriseEntity.getName());
			document.addField("brand_imgurl", enterpriseEntity.getImgurl());//品牌图片
			//System.out.println(enterpriseEntity.getImgurl());
			document.addField("class_id", brandProductEntity.getClass_id());//品类
			//System.out.println(brandProductEntity.getClass_id());
			document.addField("class_name", buildClassEntity.getName());//品类名
			//System.out.println(buildClassEntity.getName());
			document.addField("citypartner_id", brandProductEntity.getPartner_id());//城市合伙人
			//System.out.println(brandProductEntity.getPartner_id());
			document.addField("region_code", partnerEntity.getRegion_id());//区域
			//System.out.println(partnerEntity.getRegion_id());
			document.addField("state", brandProductEntity.getState());//状态
			//System.out.println(brandProductEntity.getState());
		   document.addField("create_time", DateUtils.formatDate_(brandProductEntity.getCreate_time()));//创建时间
		   //System.out.println(brandProductEntity.getCreate_time());
			if(brandProductEntity.getNew_time()!=null){
				document.addField("new_time", DateUtils.formatDateYmd(brandProductEntity.getNew_time()));//新品时间
			}
		   //System.out.println(brandProductEntity.getNew_time());
		   document.addField("isnew", brandProductEntity.getIsnew());//是否新品
		   //System.out.println(brandProductEntity.getIsnew());
		   document.addField("sort", brandProductEntity.getProduct_sort());//产品排序
		   //System.out.println(brandProductEntity.getProduct_sort());
		   document.addField("unit", brandProductEntity.getProduct_unit());//计价单位
		   //System.out.println( brandProductEntity.getProduct_unit());
		   document.addField("sales_model", brandProductEntity.getSales_model());//销售方式
		   //System.out.println( brandProductEntity.getSales_model());
			document.addField("percent", brandProductEntity.getPercent());//百分比
			//System.out.println( brandProductEntity.getPercent());
			
			
			   List<Long> series_ids = new ArrayList<>();
		  List<BaseBrandProdSeriesEntity>	baseBrandProdSeriesEntities =brandProductEntity.getBaseBrandProdSeriesEntities();
		  
		  for (BaseBrandProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
			  
			  
			  
			  
			  baseBrandProdSeriesEntity.setProduct_id(brandProductEntity.getId());
			  
			  baseBrandProdSeriesEntityMapper.insertSelective(baseBrandProdSeriesEntity);
			  series_ids.add(baseBrandProdSeriesEntity.getSeries_id());
		  }
		  document.addField("series_ids", series_ids);//系列id
		  List<String> images = new ArrayList<>();
		  List<String> case_images = new ArrayList<>();
		  
		  List<BaseBrandProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
		  String img_url = null;
		  for(int i=0;i<baseBrandProdImgEntities.size();i++){
			  BaseBrandProdImgEntity baseBrandProdImgEntity =baseBrandProdImgEntities.get(i);
			  
			  baseBrandProdImgEntity.setProduct_id(brandProductEntity.getId());
			  baseBrandProdImgEntity.setImg_sort(i);
			  baseBrandProdImgEntityMapper.insertSelective(baseBrandProdImgEntity);
			  
			  if(baseBrandProdImgEntities.get(i).getType()==1){
				  images.add(baseBrandProdImgEntities.get(i).getImgurl());
				  if(img_url==null){
					  img_url = baseBrandProdImgEntities.get(i).getImgurl();
				  }
			  }else{
				  case_images.add(baseBrandProdImgEntities.get(i).getImgurl());
			  }
		  }
		  document.addField("images", images);//轮播图
		  //System.out.println( images);
		  document.addField("case_images", case_images);//案例图
		  //System.out.println( case_images);
		  document.addField("image_url", img_url);//案例图
		  //System.out.println( img_url);
		  List<BaseBrandProdSalesAttrEntity> prodSalesAttrEntities=brandProductEntity.getServerprodSalesAttrEntity();
		  List<Double> price=new ArrayList<>();
		  List<String> color = new ArrayList<>();
		  List<String> color_model = new ArrayList<>();
		  List<String> model = new ArrayList<>();
		  
		  for (BaseBrandProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
			  
			  
			   
			  brandProdSalesAttrEntity.setData_flag("ins");
			   
			  brandProdSalesAttrEntity.setProduct_id(brandProductEntity.getId());
			  prodSalesAttrEntityMapper.insertSelective(brandProdSalesAttrEntity);
			  
			  price.add(brandProdSalesAttrEntity.getPrice());
			  color.add(brandProdSalesAttrEntity.getColor());
			  model.add(brandProdSalesAttrEntity.getModel());
			  color_model.add(brandProdSalesAttrEntity.getColor()+" "+brandProdSalesAttrEntity.getModel());
			  
		  }
		  document.addField("min_price", Collections.min(price));
		  //System.out.println( Collections.min(price));
		  document.addField("min_prom_price", Collections.min(price));
		  document.addField("max_price", Collections.max(price));
		  //System.out.println( Collections.max(price));
		  document.addField("color", color);
		  //System.out.println( color);
		  document.addField("color_model", color_model);
		  //System.out.println( color_model);
			document.addField("model", model);
			//System.out.println( model);
		  
		  
		 List<BrandProdAttrValueEntity> brandProdAttrValueEntities= brandProductEntity.getBrandProdAttrValueEntities();
		 
		 for (BrandProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
			 
			 brandProdAttrValueEntity.setClass_id(brandProductEntity.getClass_id());
			 brandProdAttrValueEntity.setProduct_id(brandProductEntity.getId());
			 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
		}
		 Map<String, String> arrtMap = brandProdAttrValueEntityMapper.selectValuesByProductIdForProductCore(brandProductEntity.getId());
		 document.addField("attr_value", arrtMap.get("attr_value").split(","));
			document.addField("attr_ids", arrtMap.get("attr_ids").split(","));
			document.addField("attr_names", arrtMap.get("attr_names").split(","));
			document.addField("value_ids", arrtMap.get("value_ids").split(","));
		 //BrandProdAttrValueName attrValueName=brandProductEntityMapper.selectproductAttrValueNameByProductId(brandProductEntity.getId());
		// RedisUtils.getRedisTemplate().opsForHash().put("brand_product_attr_value_name", brandProductEntity.getId()+"", attrValueName);
			 SalesNumEntity salesNumEntity = new SalesNumEntity();
			 
			 salesNumEntity.setProduct_id(brandProductEntity.getId());
			 
			 
			 Random r= new Random();
			 
			 int  l=r.nextInt(100);
			 
			 salesNumEntity.setSales_num(new Long(l));
			 
			 salesNumEntityMapper.insertSelective(salesNumEntity);
			document.addField("sale_num", salesNumEntity.getSales_num().intValue());
			 //System.out.println( salesNumEntity.getSales_num().intValue());
			 docs.add(document);
			 super.initProduct();
				try {
					UpdateResponse add = solr.add(docs);
					solr.commit();
					if(add.getStatus()!=0){
						System.out.println("添加solr错误信息："+add.getStatus());
					}
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
					return false;
				}
		return true;
	}
	
	@Override
	public Page<Map<String, Object>> getBrandPaoductBrandMain(Page<Map<String, Object>> page) {
		
		
		
		if(page.getParam()==null){
			Map<String,Object> param = new HashMap<>();
			
			param.put("brand_id", ((BrandEntity)UserContext.getCurrentUserInfo()).getId());
			page.setParam(param);
		}else{
			
			Map<String,Object> param =(Map<String, Object>) page.getParam();
			param.put("brand_id", ((BrandEntity)UserContext.getCurrentUserInfo()).getId());
			
		}
		
		// TODO Auto-generated method stub
		
		//Map<String,Object> param=(Map<String, Object>) page.getParam();
		//param.put("brand_id",1);
		page.setData(brandProductEntityMapper.getBrandPaoductBrandMainPage(page));
		return page;
	}
	@Override
	public Page<Map<String, Object>> getBrandPaoductBrandMainList(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		page.setData(brandProductEntityMapper.getBrandPaoductBrandMainListPage(page));
		return page;
	}

/*	@Override
	public Boolean modfiyBrandProductForCopy(BrandProductEntity brandProductEntity) {
		// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				UserEntity u = UserContext.getCurrentUser();
				BrandEntity brandEntity =null;
				BuildEnterpriseEntity enterpriseEntity =null;
				BuildClassEntity buildClassEntity =null;
				PartnerEntity partnerEntity =null;
				 if (u.getUser_type() == UserType.BRAND.getIndex()) {
					 brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
					 enterpriseEntity = brandEntity.getBuildEnterpriseEntity();
					 buildClassEntity = enterpriseEntity.getBuildClassEntity();
					 partnerEntity = brandEntity.getPartnerEntity();
					if(brandEntity != null && brandEntity.getBrand_idintify_type() != null && brandEntity.getBrand_idintify_type() == 1){
						brandProductEntity.setSell_type(SysConstant.seller_type_online);
					}
					
				}
				
				  // this.popuBrandProductEntity(brandProductEntity);
				   Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
				   SolrInputDocument document = new SolrInputDocument();
				   document.addField("product_id", brandProductEntity.getId()+"");//id
				   //System.out.println(brandProductEntity.getId());
				   document.addField("product_name", brandProductEntity.getProduct_title());//name
				   //System.out.println(brandProductEntity.getProduct_title());
				   document.addField("description", brandProductEntity.getDescription());//详情
				   //System.out.println(brandProductEntity.getDescription());
				   document.addField("brand_id", brandProductEntity.getBrand_id());//品牌id
				   //System.out.println(brandProductEntity.getBrand_id());
				   document.addField("hot_line", brandEntity.getHot_line());//品牌id
				   //System.out.println(brandEntity.getHot_line());
					document.addField("enterprise_id", brandProductEntity.getEnterprise_id());
					//System.out.println(brandProductEntity.getEnterprise_id());
					document.addField("brand_name", enterpriseEntity.getName());//品牌名称
					//System.out.println(enterpriseEntity.getName());
					document.addField("brand_imgurl", enterpriseEntity.getImgurl());//品牌图片
					//System.out.println(enterpriseEntity.getImgurl());
					document.addField("class_id", brandProductEntity.getClass_id());//品类
					//System.out.println(brandProductEntity.getClass_id());
					document.addField("class_name", buildClassEntity.getName());//品类名
					//System.out.println(buildClassEntity.getName());
					document.addField("citypartner_id", brandProductEntity.getPartner_id());//城市合伙人
					//System.out.println(brandProductEntity.getPartner_id());
					document.addField("region_code", partnerEntity.getRegion_id());//区域
					//System.out.println(partnerEntity.getRegion_id());
					if(brandProductEntity.getNew_time()!=null){
						document.addField("new_time", DateUtils.formatDateYmd(brandProductEntity.getNew_time()));//新品时间
					}
				   //System.out.println(brandProductEntity.getNew_time());
				   document.addField("isnew", brandProductEntity.getIsnew());//是否新品
				   //System.out.println(brandProductEntity.getIsnew());
				   document.addField("sort", brandProductEntity.getProduct_sort());//产品排序
				   //System.out.println(brandProductEntity.getProduct_sort());
				   document.addField("unit", brandProductEntity.getProduct_unit());//计价单位
				   //System.out.println( brandProductEntity.getProduct_unit());
				   document.addField("sales_model", brandProductEntity.getSales_model());//销售方式
				   //System.out.println( brandProductEntity.getSales_model());
					document.addField("percent", brandProductEntity.getPercent());//百分比
					//System.out.println( brandProductEntity.getPercent());
					
				   List<Long> series_ids = new ArrayList<>();
				   BaseBrandProductEntity  productEntity =brandProductEntityMapper.selectByPrimaryKey(brandProductEntity.getId());
				   
				   brandProductEntity.setState(productEntity.getState());
				   
				   brandProductEntity.setCreate_time(productEntity.getCreate_time());
				   document.addField("state", productEntity.getState());//状态
				   //System.out.println(brandProductEntity.getState());	   
				   if(productEntity.getCreate_time()!=null){
				   document.addField("create_time", DateUtils.formatDate_(productEntity.getCreate_time()));//创建时间
			   }
				   //System.out.println(brandProductEntity.getCreate_time());
				   brandProductEntityMapper.updateByPrimaryKeyWithBLOBs(brandProductEntity);
					
				  List<BaseBrandProdSeriesEntity>	baseBrandProdSeriesEntities =brandProductEntity.getBaseBrandProdSeriesEntities();
				  baseBrandProdSeriesEntityMapper.deleteProdSeriesByProductId(brandProductEntity.getId());
				  for (BaseBrandProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
					  
					  
						  baseBrandProdSeriesEntity.setProduct_id(brandProductEntity.getId());
						  baseBrandProdSeriesEntityMapper.insertSelective(baseBrandProdSeriesEntity);
						  series_ids.add(baseBrandProdSeriesEntity.getSeries_id());
				  }
				  document.addField("series_ids", series_ids);//系列id
				  List<String> images = new ArrayList<>();
				  List<String> case_images = new ArrayList<>();
				  List<BaseBrandProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
				  String img_url = null;
				  brandProdImgEntityMapper.deleteByProductId(brandProductEntity.getId());
				  for(int i=0;i<baseBrandProdImgEntities.size();i++){
					  BaseBrandProdImgEntity baseBrandProdImgEntity =baseBrandProdImgEntities.get(i);
					  baseBrandProdImgEntity.setImg_sort(i);
						  baseBrandProdImgEntity.setProduct_id(brandProductEntity.getId());
						  baseBrandProdImgEntityMapper.insertSelective(baseBrandProdImgEntity);
					  
					  if(baseBrandProdImgEntities.get(i).getType()==1){
						  images.add(baseBrandProdImgEntities.get(i).getImgurl());
						  if(img_url==null){
							  img_url = baseBrandProdImgEntities.get(i).getImgurl();
						  }
					  }else{
						  case_images.add(baseBrandProdImgEntities.get(i).getImgurl());
					  }
				  }
				  document.addField("images", images);//轮播图
				  //System.out.println( images);
				  document.addField("case_images", case_images);//案例图
				  //System.out.println( case_images);
				  document.addField("image_url", img_url);//案例图
				  //System.out.println( img_url);
				  
				  List<Double> price=new ArrayList<>();
				  List<String> color = new ArrayList<>();
				  List<String> color_model = new ArrayList<>();
				  List<String> model = new ArrayList<>();
				  
				
				  
				  List<BaseBrandProdSalesAttrEntity> prodSalesAttrEntities=brandProductEntity.getServerprodSalesAttrEntity();
				  
				  for (BaseBrandProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
					  
					  if(brandProdSalesAttrEntity.getId()==null){
					 
					brandProdSalesAttrEntity.setData_flag("ins");
					  brandProdSalesAttrEntity.setProduct_id(brandProductEntity.getId());
					  prodSalesAttrEntityMapper.insertSelective(brandProdSalesAttrEntity);
					  }else{
						  brandProdSalesAttrEntity.setData_flag("upd");
						  prodSalesAttrEntityMapper.updateByPrimaryKeySelective(brandProdSalesAttrEntity);
					  }
					  price.add(brandProdSalesAttrEntity.getPrice());
					  color.add(brandProdSalesAttrEntity.getColor());
					  model.add(brandProdSalesAttrEntity.getModel());
					  color_model.add(brandProdSalesAttrEntity.getColor()+" "+brandProdSalesAttrEntity.getModel());
				  }
				  document.addField("min_price", Collections.min(price));
				  //System.out.println( Collections.min(price));
				  document.addField("min_prom_price", Collections.min(price));
				  document.addField("max_price", Collections.max(price));
				  //System.out.println( Collections.max(price));
				  document.addField("color", color);
				  //System.out.println( color);
				  document.addField("color_model", color_model);
				  //System.out.println( color_model);
					document.addField("model", model);
					//System.out.println( model);
				  prodAttrValueEntityMapper.deleteProdattValueByProductId(brandProductEntity.getId());
				 List<BrandProdAttrValueEntity> brandProdAttrValueEntities= brandProductEntity.getBrandProdAttrValueEntities();
				 
				 for (BrandProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
					 
					 brandProdAttrValueEntity.setProduct_id(brandProductEntity.getId());
					 
					 if(brandProdAttrValueEntity.getAttr_id().longValue()==1L){
						 brandProdAttrValueEntity.setId(null);
						 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
						
					 }else{
						 
						 BrandProdAttrValueEntity attrValueEntity =prodAttrValueEntityMapper.selectProdattrValueByProductIdAndAttrId(brandProdAttrValueEntity);
						 
						 if(attrValueEntity==null){
							 brandProdAttrValueEntity.setId(null);
							 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
						 }else{
							 
							 brandProdAttrValueEntity.setId(attrValueEntity.getId());
							 prodAttrValueEntityMapper.updateByPrimaryKeySelective(brandProdAttrValueEntity);
						 }
						 
						
					 }
					
					 
				}
				 Map<String, String> arrtMap = brandProdAttrValueEntityMapper.selectValuesByProductIdForProductCore(brandProductEntity.getId());
				 document.addField("attr_value", arrtMap.get("attr_value").split(","));
					document.addField("attr_ids", arrtMap.get("attr_ids").split(","));
					document.addField("attr_names", arrtMap.get("attr_names").split(","));
					document.addField("value_ids", arrtMap.get("value_ids").split(","));
				 
				 BrandProdAttrValueName attrValueName=brandProductEntityMapper.selectproductAttrValueNameByProductId(brandProductEntity.getId());
				 
				 //RedisUtils.getRedisTemplate().opsForHash().delete("brand_product_attr_value_name", brandProductEntity.getId()+"");
				 //RedisUtils.getRedisTemplate().opsForHash().put("brand_product_attr_value_name", brandProductEntity.getId()+"", attrValueName);
				 SalesNumEntity salesNumEntity = salesNumEntityMapper.selectByProductId(brandProductEntity.getId());
		
				if(salesNumEntity!=null){
		 document.addField("sale_num", salesNumEntity.getSales_num().intValue());
		 }
				 //System.out.println( salesNumEntity.getSales_num().intValue());
				 docs.add(document);
				 super.initProduct();
					try {
						UpdateResponse delete = solr.deleteById(brandProductEntity.getId()+"");
						solr.commit();
						if(delete.getStatus()!=0){
							System.out.println("添加solr错误信息："+delete.getStatus());
						}
						UpdateResponse add = solr.add(docs);
						solr.commit();
						if(add.getStatus()!=0){
							System.out.println("添加solr错误信息："+add.getStatus());
						}
					} catch (SolrServerException | IOException e) {
						e.printStackTrace();
						return false;
					}
				
				return true;
	}*/

}
