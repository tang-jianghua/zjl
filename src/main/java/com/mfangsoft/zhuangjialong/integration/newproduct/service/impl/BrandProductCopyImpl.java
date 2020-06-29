package com.mfangsoft.zhuangjialong.integration.newproduct.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.BeanUtilsExtends;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.integration.brandclassify.mapper.BrandClassifyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.entclassify.mapper.EntClassifyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entclassify.model.EntClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper.EntNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdImgEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProductEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.service.EntProductService;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdAttrValueEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdImgEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductCopy;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductService;
import com.mfangsoft.zhuangjialong.integration.series.mapper.BrandSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.series.mapper.EntSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.model.EntSeriesEntity;
@Service
public class BrandProductCopyImpl implements BrandProductCopy {

	
	@Autowired
	private  BrandProductService productService;
	@Autowired
	private  BrandNewProductEntityMapper brandNewProductEntityMapper;
	
	@Autowired
	private  BrandClassifyEntityMapper brandClassifyEntityMapper;
	
	@Autowired
	private BrandSeriesEntityMapper brandSeriesEntityMapper;
	
	
	@Autowired 
	private EntClassifyEntityMapper entClassifyEntityMapper;
	
	
	@Autowired
	private EntSeriesEntityMapper entSeriesEntityMapper;
	
	
	@Autowired
	private EntNewProductEntityMapper entProductEntityMapper;
	
	@Autowired
	private EntProductService entProductService;
	
	@Autowired
	private BrandService brandService;
	@Autowired
	private BrandProductService brandProductServiceImpl;
	@Autowired
	private ProductCoreService productCoreServiceImpl;
	
	
	@Autowired 
	private  BrandProdAttrValueEntityMapper prodAttrValueEntityMapper;
	
	
	
	
	@Override
	public Boolean addCopyBrandProduct(Long frombrandId,Long tobrandId,Long class_id) {
		// TODO Auto-generated method stub
		
	//	List<Long> list=brandNewProductEntityMapper.selectBrandProductByBrandId(frombrandId,class_id);
		Map<String,Object> tempMap = new HashMap<>();
		Map<String,Object> map  = new HashMap<>();
		map.put("brand_id", frombrandId);
		List<BrandClassifyEntity> brandClassifyEntities=brandClassifyEntityMapper.queryBrandClassifies(map);
		for (BrandClassifyEntity brandClassifyEntity : brandClassifyEntities) {
			
			BrandClassifyEntity classifyEntity = new BrandClassifyEntity();
			
			classifyEntity.setBrand_id(tobrandId);
			
			classifyEntity.setName(brandClassifyEntity.getName());
			
			brandClassifyEntityMapper.insert(classifyEntity);
			
			Map<String,Object> maps = new HashMap<>();
			
			maps.put("classify_id", brandClassifyEntity.getId());
			
			List<BrandSeriesEntity> brandSeriesEntities=brandSeriesEntityMapper.getBrandSeriesListByClassify(maps);
			
			
			for (BrandSeriesEntity brandSeriesEntity : brandSeriesEntities) {
				
				BrandSeriesEntity seriesEntity = new BrandSeriesEntity();
				
				seriesEntity.setClassify_id(classifyEntity.getId());
				
				seriesEntity.setName(brandSeriesEntity.getName());
				brandSeriesEntityMapper.insert(seriesEntity);
				tempMap.put(brandSeriesEntity.getId().toString(), seriesEntity.getId());
			}
		}
		List<Long> list=brandNewProductEntityMapper.selectBrandProductByBrandId(frombrandId,class_id);
		
		
		
		List<BrandProductEntity> brandProductEntities = new ArrayList<>();
		
		for (Long productId : list) {
			
			
			BrandProductEntity brandProductEntity=productService.getBrandProductById(productId);
			
			brandProductEntity.setId(null);
			
			BrandEntity brandEntity=brandService.getBrandById(tobrandId);
			brandProductEntity.setBrand_id(tobrandId);
			
			brandProductEntity.setPartner_id(brandEntity.getCitypartner_id());
			
			brandProductEntity.setEnterprise_id(brandEntity.getPartnerEntity().getEnterprise_id());
			
			//brandProductEntity.set
			List<BaseBrandProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
			
			for (BaseBrandProdImgEntity baseBrandProdImgEntity : baseBrandProdImgEntities) {
				
				baseBrandProdImgEntity.setId(null);
			}
			List<BaseBrandProdSeriesEntity>  baseBrandProdSeriesEntities=brandProductEntity.getBaseBrandProdSeriesEntities();
			
			
			for (BaseBrandProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
				
				baseBrandProdSeriesEntity.setSeries_id(new Long(tempMap.get(baseBrandProdSeriesEntity.getSeries_id().toString()).toString()));
				baseBrandProdSeriesEntity.setId(null);
			}
			
			List<BrandProdAttrValueEntity> attrValueEntities=brandProductEntity.getBrandProdAttrValueEntities();
			
			for (BrandProdAttrValueEntity brandProdAttrValueEntity : attrValueEntities) {
				
				brandProdAttrValueEntity.setId(null);
			}
			
			List<BrandProdSalesAttrEntity> brandProdSalesAttrEntities =brandProductEntity.getProdSalesAttrEntity();
			
			for (BrandProdSalesAttrEntity brandProdSalesAttrEntity : brandProdSalesAttrEntities) {
				
				
				List<BaseBrandProdSalesAttrEntity>  baseBrandProdSalesAttrEntities=brandProdSalesAttrEntity.getStandardArray();
				
				for (BaseBrandProdSalesAttrEntity baseBrandProdSalesAttrEntity : baseBrandProdSalesAttrEntities) {
					
					baseBrandProdSalesAttrEntity.setId(null);
				}
				brandProdSalesAttrEntity.setId(null);
			}
			brandProductEntities.add(brandProductEntity);
			
		}
		
		for (BrandProductEntity brandProductEntity2 : brandProductEntities) {
			productService.addBrandProductForCopy(brandProductEntity2);
		}
		
		return true;
	}

	@Override
	public Boolean addFromEntToBrandProduct(Page<Map<String,Object>> page) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		
		Page<Map<String,Object>> pages=entProductService.getEntProductForPage(page);
		
		
		List<Long> ids = new ArrayList<>();
		
		List<Map<String,Object>> list=pages.getData();
		
		for (Map<String, Object> map : list) {
			
			ids.add(new Long(map.get("id").toString()));
		}
		
		Long brand_id =((BrandEntity)UserContext.getCurrentUserInfo()).getId();
        Map<String,Object> map  = new HashMap<>();
		
		map.put("brand_id", ((BrandEntity)UserContext.getCurrentUserInfo()).getId());
		
		List<BrandClassifyEntity> brandClassifyEntities=brandClassifyEntityMapper.queryBrandClassifies(map);
		if(brandClassifyEntities.isEmpty()){
			
			copyProductAndseries(ids,brand_id);
			
		}else{
			//copyProduct(ids);
		}
		
		return true;
		
		
	}

	@Override
	public Boolean addFromEntToBrandProduct(List<Long> ids) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		
		Map<String,Object> map  = new HashMap<>();
		
		Long brand_id =((BrandEntity)UserContext.getCurrentUserInfo()).getId();
		map.put("brand_id", brand_id);
		
		List<BrandClassifyEntity> brandClassifyEntities=brandClassifyEntityMapper.queryBrandClassifies(map);
		if(brandClassifyEntities.isEmpty()){
			
			copyProductAndseries(ids,brand_id);
			
		}else{
			copyProduct(ids,brand_id);
		}
		return true;
	}

	private void copyProduct(List<Long> ids,Long brand_id) throws IllegalAccessException, InvocationTargetException {
		for (Long id : ids) {
			EntProductEntity entProductEntity=entProductService.getEntProductById(id);
			Map<String,Object> p= new HashMap<>();
			p.put("tmpflag", entProductEntity.getId()+"");
			p.put("brand_id", brand_id);
			Long entProductId=brandNewProductEntityMapper.selectBrandProductByTmpflag(p);
			
			if(entProductId==null){
				
				
				BrandProductEntity  brandProductEntity = new BrandProductEntity();
				
				
				
				BeanUtilsExtends.copyProperties(brandProductEntity,entProductEntity);
				
				brandProductEntity.setBaseBrandProdImgEntities(setProductImage(entProductEntity.getBaseBrandProdImgEntities()));
				
				
				List<BaseBrandProdSeriesEntity>  baseBrandProdSeriesEntities = new ArrayList<>();
				brandProductEntity.setBaseBrandProdSeriesEntities(baseBrandProdSeriesEntities);
				
				brandProductEntity.setBrandProdAttrValueEntities(setProdAttrValue(entProductEntity.getBrandProdAttrValueEntities()));
				
				brandProductEntity.setProdSalesAttrEntity(setProdSalesAttr(entProductEntity.getProdSalesAttrEntity()));
				
				brandProductEntity.setTmpflag(entProductEntity.getId().toString());
				
				BrandEntity  brandEntity = brandService.getBrandById(brand_id);
				brandProductEntity.setBrand_id(brandEntity.getId());
				
				brandProductEntity.setEnterprise_id(brandEntity.getPartnerEntity().getEnterpriseEntity().getId());
				brandProductEntity.setBuild_brand_id(brandEntity.getBuildEnterpriseEntity().getId());
				brandProductEntity.setPartner_id(brandEntity.getCitypartner_id());
				brandProductEntity.setClass_id(brandEntity.getBuildEnterpriseEntity().getClass_id());
				brandProductEntity.setState(2);
				productService.addBrandProductForCopy(brandProductEntity);
			}/*else{
				
				//productService.deleteProduct(entProductId);
				
				BrandProductEntity  brandProductEntity = new BrandProductEntity();
				
				BeanUtilsExtends.copyProperties(brandProductEntity,entProductEntity);
				brandProductEntity.setId(entProductId);
				
				
				brandProductEntity.setBaseBrandProdImgEntities(setProductImage(entProductEntity.getBaseBrandProdImgEntities()));
				
				
				List<BaseBrandProdSeriesEntity>  baseBrandProdSeriesEntities = new ArrayList<>();
				brandProductEntity.setBaseBrandProdSeriesEntities(baseBrandProdSeriesEntities);
				
				brandProductEntity.setBrandProdAttrValueEntities(setProdAttrValue(entProductEntity.getBrandProdAttrValueEntities()));
				
				brandProductEntity.setProdSalesAttrEntity(setProdSalesAttr(entProductEntity.getProdSalesAttrEntity()));
				
				brandProductEntity.setTmpflag(entProductEntity.getId().toString());
				
				BrandEntity  brandEntity = brandService.getBrandById(brand_id);
				brandProductEntity.setBrand_id(brandEntity.getId());
				
				brandProductEntity.setEnterprise_id(brandEntity.getPartnerEntity().getEnterpriseEntity().getId());
				brandProductEntity.setBuild_brand_id(brandEntity.getBuildEnterpriseEntity().getId());
				brandProductEntity.setPartner_id(brandEntity.getCitypartner_id());
				brandProductEntity.setClass_id(brandEntity.getBuildEnterpriseEntity().getClass_id());
				brandProductEntity.setState(2);
				productService.modfiyBrandProductForCopy(brandProductEntity);
			}*/
			
			
			
		}
	}
	
	
	private void copyProductAndseries(List<Long> ids,Long brand_id) throws IllegalAccessException, InvocationTargetException {
		
		   Set<Object> map = new HashSet<>();
		   Set<Object> smap = new HashSet<>();
	       Map<String,Object> classifyMap = new HashMap<>();
	       Map<String,Object> sMap = new HashMap<>();
	       Map<String,Object> tempMap = new HashMap<>();

	   
	    BrandClassifyEntity brandClassifyEntity=null;
	    
		
		for (Long id : ids) {
			
			
			
			EntProductEntity entProductEntity=entProductService.getEntProductById(id);
			
			Map<String,Object> p= new HashMap<>();
			p.put("tmpflag", entProductEntity.getId()+"");
			p.put("brand_id", brand_id);
			Long entProductId=brandNewProductEntityMapper.selectBrandProductByTmpflag(p);
			
			if(entProductId==null){
			List<BaseEntProdSeriesEntity> baseEntProdSeriesEntities=entProductEntity.getBaseBrandProdSeriesEntities();
			
			for (BaseEntProdSeriesEntity baseEntProdSeriesEntity : baseEntProdSeriesEntities) {
				
				EntSeriesEntity entSeriesEntity =entSeriesEntityMapper.selectByPrimaryKey(baseEntProdSeriesEntity.getSeries_id());
				
				BrandSeriesEntity brandSeriesEntity = new BrandSeriesEntity();
				
				BeanUtilsExtends.copyProperties(brandSeriesEntity, entSeriesEntity);
				if(map.add(entSeriesEntity.getClassify_id())){
					 brandClassifyEntity = new BrandClassifyEntity();
					 
					 EntClassifyEntity entClassifyEntity =entClassifyEntityMapper.selectByPrimaryKey(entSeriesEntity.getClassify_id());
						
					 BeanUtilsExtends.copyProperties(brandClassifyEntity, entClassifyEntity);
					 
					 brandClassifyEntity.setBrand_id(brand_id);
					 
					 brandClassifyEntityMapper.insert(brandClassifyEntity);
					 
					 classifyMap.put(entSeriesEntity.getClassify_id()+"", brandClassifyEntity);
					 
					 
					 if(smap.add(entSeriesEntity.getId())){
						 
						 brandSeriesEntity.setClassify_id(brandClassifyEntity.getId());
						 
						 brandSeriesEntity.setId(null);
						 
						 brandSeriesEntityMapper.insert(brandSeriesEntity);
						 
						 sMap.put(entSeriesEntity.getId()+"", brandSeriesEntity);
						 tempMap.put(entSeriesEntity.getId()+"", brandSeriesEntity.getId());
					 }else{
						 
						 
						 BrandSeriesEntity  seriesEntity =(BrandSeriesEntity) sMap.get(entSeriesEntity.getId()+"");
						 
						 
						 tempMap.put(entSeriesEntity.getId()+"", seriesEntity.getId());
					 }
					 
				}else{
					
					brandClassifyEntity =(BrandClassifyEntity) classifyMap.get(entSeriesEntity.getClassify_id()+"");
					
					if(smap.add(entSeriesEntity.getId())){
						 
						brandSeriesEntity.setClassify_id(brandClassifyEntity.getId());
						
						brandSeriesEntity.setId(null);
						
						brandSeriesEntityMapper.insert(brandSeriesEntity);
						
						tempMap.put(entSeriesEntity.getId()+"", brandSeriesEntity.getId());
						
						 sMap.put(entSeriesEntity.getId()+"", brandSeriesEntity);
					 }else{
						 
						 BrandSeriesEntity  seriesEntity =(BrandSeriesEntity) sMap.get(entSeriesEntity.getId()+"");
						 
						 
						 tempMap.put(entSeriesEntity.getId()+"", seriesEntity.getId());
					 }
					
					
					
					
				}
				
			}
			}
		}
		
		List<BrandProductEntity> brandProductEntities = new ArrayList<>();
		for (Long id : ids) {
			
			EntProductEntity entProductEntity=entProductService.getEntProductById(id);
			
			Map<String,Object> p= new HashMap<>();
			p.put("tmpflag", entProductEntity.getId()+"");
			p.put("brand_id", ((BrandEntity)UserContext.getCurrentUserInfo()).getId());
			Long entProductId=brandNewProductEntityMapper.selectBrandProductByTmpflag(p);
			
			if(entProductId==null){
			BrandProductEntity  brandProductEntity = new BrandProductEntity();
			
			BeanUtilsExtends.copyProperties(brandProductEntity,entProductEntity);
			
			brandProductEntity.setBaseBrandProdImgEntities(setProductImage(entProductEntity.getBaseBrandProdImgEntities()));
			
			brandProductEntity.setBaseBrandProdSeriesEntities(setProdSeries(entProductEntity.getBaseBrandProdSeriesEntities(), tempMap));
			
			brandProductEntity.setBrandProdAttrValueEntities(setProdAttrValue(entProductEntity.getBrandProdAttrValueEntities()));
			
			brandProductEntity.setProdSalesAttrEntity(setProdSalesAttr(entProductEntity.getProdSalesAttrEntity()));
			
			brandProductEntity.setTmpflag(entProductEntity.getId().toString());
			
			brandProductEntities.add(brandProductEntity);
			}
		}
		
		for (BrandProductEntity brandProductEntity : brandProductEntities) {
			
			brandProductEntity.setState(2);
			productService.addBrandProduct(brandProductEntity);
		}
		
	}

	private List<BaseBrandProdSeriesEntity> setProdSeries(List<BaseEntProdSeriesEntity> baseEntProdSeriesEntities,Map<String,Object> map) throws IllegalAccessException, InvocationTargetException{
		
       List<BaseBrandProdSeriesEntity> baseBrandProdSeriesEntities = new ArrayList<>();
		
		for (BaseEntProdSeriesEntity baseEntProdSeriesEntity : baseEntProdSeriesEntities) {
			
			BaseBrandProdSeriesEntity baseBrandProdSeriesEntity = new BaseBrandProdSeriesEntity();
			
			BeanUtilsExtends.copyProperties(baseBrandProdSeriesEntity, baseEntProdSeriesEntity);
			
			baseBrandProdSeriesEntity.setId(null);
			
			baseBrandProdSeriesEntity.setSeries_id(new Long(map.get(baseEntProdSeriesEntity.getSeries_id()+"").toString()));
			
			baseBrandProdSeriesEntities.add(baseBrandProdSeriesEntity);
			
		}
		
		return baseBrandProdSeriesEntities;
		
	}
	
	
	
	private List<BrandProdSalesAttrEntity>  setProdSalesAttr(List<EntProdSalesAttrEntity> entProdSalesAttrEntities)
			throws IllegalAccessException, InvocationTargetException {
		
		
		List<BrandProdSalesAttrEntity>  brandProdSalesAttrEntities = new ArrayList<>();
		for (EntProdSalesAttrEntity entProdSalesAttrEntity : entProdSalesAttrEntities) {
			
			BrandProdSalesAttrEntity brandProdSalesAttrEntity  = new BrandProdSalesAttrEntity();
			
			
			BeanUtilsExtends.copyProperties(brandProdSalesAttrEntity, entProdSalesAttrEntity);
			
			List<BaseEntProdSalesAttrEntity>  baseEntProdSalesAttrEntities=entProdSalesAttrEntity.getStandardArray();
			
			List<BaseBrandProdSalesAttrEntity> baseBrandProdSalesAttrEntities = new ArrayList<>();
			
			  for (BaseEntProdSalesAttrEntity baseEntProdSalesAttrEntity : baseEntProdSalesAttrEntities) {
				  
				  BaseBrandProdSalesAttrEntity baseBrandProdSalesAttrEntity = new BaseBrandProdSalesAttrEntity();
				  
				  BeanUtilsExtends.copyProperties(baseBrandProdSalesAttrEntity, baseEntProdSalesAttrEntity);
				  
				  baseBrandProdSalesAttrEntity.setId(null);
				  
				  baseBrandProdSalesAttrEntities.add(baseBrandProdSalesAttrEntity);
				
				  
			}
			
			brandProdSalesAttrEntity.setStandardArray(baseBrandProdSalesAttrEntities);
			
			
			brandProdSalesAttrEntities.add(brandProdSalesAttrEntity);
			
			
		}
		return brandProdSalesAttrEntities;
	}

	private List<BrandProdAttrValueEntity> setProdAttrValue(List<EntProdAttrValueEntity> entProdAttrValueEntities)
			throws IllegalAccessException, InvocationTargetException {
		List<BrandProdAttrValueEntity> brandProdAttrValueEntities = new ArrayList<>();
		
		for (EntProdAttrValueEntity entProdAttrValueEntity : entProdAttrValueEntities) {
			
			 if(entProdAttrValueEntity.getAttr_value().indexOf(",")!=-1){
				 
				 String[] str = entProdAttrValueEntity.getAttr_value().split(",");
				 
				 for (String string : str) {
					
					 
					   BrandProdAttrValueEntity brandProdAttrValueEntity = new BrandProdAttrValueEntity();
						
						BeanUtilsExtends.copyProperties(brandProdAttrValueEntity, entProdAttrValueEntity);
						
						brandProdAttrValueEntity.setId(null);
						
						brandProdAttrValueEntity.setAttr_value(string);
						
						brandProdAttrValueEntities.add(brandProdAttrValueEntity);
				 }
				 
				   
			 }else{
				 
				 
				    BrandProdAttrValueEntity brandProdAttrValueEntity = new BrandProdAttrValueEntity();
					
					BeanUtilsExtends.copyProperties(brandProdAttrValueEntity, entProdAttrValueEntity);
					
					brandProdAttrValueEntity.setId(null);
					
					brandProdAttrValueEntities.add(brandProdAttrValueEntity);
			 }
			
			
			
			
			
		}
		return brandProdAttrValueEntities;
	}

	private List<BaseBrandProdImgEntity> setProductImage(List<BaseEntProdImgEntity> baseEntProdImgEntities)
			throws IllegalAccessException, InvocationTargetException {
		List<BaseBrandProdImgEntity> baseBrandProdImgEntities = new ArrayList<>();
		
		for (BaseEntProdImgEntity baseEntProdImgEntity : baseEntProdImgEntities) {
			
			BaseBrandProdImgEntity baseBrandProdImgEntity = new BaseBrandProdImgEntity();
			
			BeanUtilsExtends.copyProperties(baseBrandProdImgEntity, baseEntProdImgEntity);
			
			baseBrandProdImgEntity.setId(null);
			
			baseBrandProdImgEntities.add(baseBrandProdImgEntity);
			
		}
		return baseBrandProdImgEntities;
	}

	@Override
	public Boolean addCopyEntToBrandProduct(Long brandId, Long entId, Long class_id) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		
		Map<String,Object> tempMap = new HashMap<>();
		Map<String,Object> map  = new HashMap<>();
		map.put("brand_id", brandId);
		List<BrandClassifyEntity> brandClassifyEntities=brandClassifyEntityMapper.queryBrandClassifies(map);
		for (BrandClassifyEntity brandClassifyEntity : brandClassifyEntities) {
			
			EntClassifyEntity  entClassifyEntity  = new EntClassifyEntity();
			
			entClassifyEntity.setEnt_id(entId);
			
			entClassifyEntity.setName(brandClassifyEntity.getName());
			
			entClassifyEntity.setId(null);
			
			
			entClassifyEntityMapper.insert(entClassifyEntity);
			
			Map<String,Object> maps = new HashMap<>();
			
			maps.put("classify_id", brandClassifyEntity.getId());
			
			List<BrandSeriesEntity> brandSeriesEntities=brandSeriesEntityMapper.getBrandSeriesListByClassify(maps);
			
			
			for (BrandSeriesEntity brandSeriesEntity : brandSeriesEntities) {
				
				EntSeriesEntity  entSeriesEntity = new EntSeriesEntity();
				
				//BrandSeriesEntity seriesEntity = new BrandSeriesEntity();
				
				entSeriesEntity.setClassify_id(entClassifyEntity.getId());
				
				entSeriesEntity.setName(brandSeriesEntity.getName());
				
				//seriesEntity.setClassify_id(classifyEntity.getId());
				
				//entSeriesEntity.setId(brandSeriesEntity.getId());
				//seriesEntity.setName(brandSeriesEntity.getName());
				entSeriesEntityMapper.insert(entSeriesEntity);
				tempMap.put(brandSeriesEntity.getId().toString(), entSeriesEntity.getId());
			}
		}
		List<Long> list=brandNewProductEntityMapper.selectBrandProductByBrandId(brandId,class_id);
		
		
		
		List<EntProductEntity> entProductEntities = new ArrayList<>();
		
		for (Long productId : list) {
			
			BrandProductEntity brandProductEntity=productService.getBrandProductById(productId);
			
			EntProductEntity  entProductEntity  =  new EntProductEntity();
			
			
			
			BeanUtilsExtends.copyProperties(entProductEntity, brandProductEntity);
			
			entProductEntity.setEnterprise_id(entId);
			
			entProductEntity.setClass_id(3L);
			
			entProductEntity.setBuild_brand_id(144L);
			
			//brandProductEntity.set
			List<BaseBrandProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
			
			List<BaseEntProdImgEntity> baseEntProdImgEntities = new ArrayList<>();
			
			for (BaseBrandProdImgEntity baseBrandProdImgEntity : baseBrandProdImgEntities) {
				
				BaseEntProdImgEntity   baseEntProdImgEntity = new BaseEntProdImgEntity();
				
				BeanUtilsExtends.copyProperties(baseEntProdImgEntity, baseBrandProdImgEntity);
				
				baseEntProdImgEntity.setId(null);
				baseEntProdImgEntities.add(baseEntProdImgEntity);
				//baseBrandProdImgEntity.setId(null);
			}
			entProductEntity.setBaseBrandProdImgEntities(baseEntProdImgEntities);
			
			List<BaseBrandProdSeriesEntity>  baseBrandProdSeriesEntities=brandProductEntity.getBaseBrandProdSeriesEntities();
			
			List<BaseEntProdSeriesEntity> baseEntProdSeriesEntities = new ArrayList<>();
			
			for (BaseBrandProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
				
				
				BaseEntProdSeriesEntity  baseEntProdSeriesEntity = new BaseEntProdSeriesEntity();
				
				BeanUtilsExtends.copyProperties(baseEntProdSeriesEntity, baseBrandProdSeriesEntity);
				
				baseEntProdSeriesEntity.setId(null);
				baseEntProdSeriesEntity.setSeries_id(new Long(tempMap.get(baseBrandProdSeriesEntity.getSeries_id()+"").toString()));
				baseEntProdSeriesEntities.add(baseEntProdSeriesEntity);
			}
			
			entProductEntity.setBaseBrandProdSeriesEntities(baseEntProdSeriesEntities);
			
			
			List<BrandProdAttrValueEntity> attrValueEntities=brandProductEntity.getBrandProdAttrValueEntities();
			
			List<EntProdAttrValueEntity> entProdAttrValueEntities = new ArrayList<>();
			
			for (BrandProdAttrValueEntity brandProdAttrValueEntity : attrValueEntities) {
				
				
				EntProdAttrValueEntity attrValueEntity = new EntProdAttrValueEntity();
				
				
				BeanUtilsExtends.copyProperties(attrValueEntity, brandProdAttrValueEntity);
				
				attrValueEntity.setId(null);
				
				if(brandProdAttrValueEntity.getAttr_value().indexOf(",")!=-1){
					
					
					String s[]=brandProdAttrValueEntity.getAttr_value().split(",");
					
					for (String string : s) {
						
						
						EntProdAttrValueEntity entProdAttrValueEntity = new EntProdAttrValueEntity();
						
						BeanUtilsExtends.copyProperties(entProdAttrValueEntity, brandProdAttrValueEntity);
						
						entProdAttrValueEntity.setAttr_value(string);
						
						entProdAttrValueEntity.setId(null);
						entProdAttrValueEntities.add(entProdAttrValueEntity);
						
					}
					
					
				}else{
					
					entProdAttrValueEntities.add(attrValueEntity);
				}
				
				
				
				
			
				
			}
			
			entProductEntity.setBrandProdAttrValueEntities(entProdAttrValueEntities);
			
			List<BrandProdSalesAttrEntity> brandProdSalesAttrEntities =brandProductEntity.getProdSalesAttrEntity();
			
			
			List<EntProdSalesAttrEntity> entProdSalesAttrEntities  =new ArrayList<>();
			
			for (BrandProdSalesAttrEntity brandProdSalesAttrEntity : brandProdSalesAttrEntities) {
				
				
				
				EntProdSalesAttrEntity entProdSalesAttrEntity = new EntProdSalesAttrEntity();
				
				
				BeanUtilsExtends.copyProperties(entProdSalesAttrEntity, brandProdSalesAttrEntity);
				
				List<BaseEntProdSalesAttrEntity> attrEntities  =new ArrayList<>();
				
				List<BaseBrandProdSalesAttrEntity>  baseBrandProdSalesAttrEntities=brandProdSalesAttrEntity.getStandardArray();
				
				for (BaseBrandProdSalesAttrEntity baseBrandProdSalesAttrEntity : baseBrandProdSalesAttrEntities) {
					
					BaseEntProdSalesAttrEntity attrEntity = new BaseEntProdSalesAttrEntity();
					
					
					BeanUtilsExtends.copyProperties(attrEntity, baseBrandProdSalesAttrEntity);
					
					attrEntity.setId(null);
					attrEntities.add(attrEntity);
					//baseBrandProdSalesAttrEntity.setId(null);
				}
				entProdSalesAttrEntity.setStandardArray(attrEntities);
				//brandProdSalesAttrEntity.setId(null);
				
				entProdSalesAttrEntities.add(entProdSalesAttrEntity);
			}
			
			entProductEntity.setProdSalesAttrEntity(entProdSalesAttrEntities);
			entProductEntities.add(entProductEntity);
			
		}
		
		
		for (EntProductEntity entProductEntity2 : entProductEntities) {
			
			entProductEntity2.setId(null);
			entProductService.addEntProduct(entProductEntity2);
		}
		
		return true;
		
		
		//return null;
	}

//	@Override
//	public Boolean addFromEntToBrandProduct(List<Long> list, List<Long> brand_id)
//			throws IllegalAccessException, InvocationTargetException {
//		// TODO Auto-generated method stub
//		
//		
//		for (Long brand : brand_id) {
//			
//			List<Long> l=brandNewProductEntityMapper.selectBrandProductByBrandId(brand, 1L);
//			
//			for (Long id : l) {
//				
//				
//				BrandProductEntity  brandProductEntity=productService.getBrandProductById(id);
//				
//				List<BrandProdAttrValueEntity>  attrValueEntities=	brandProductEntity.getBrandProdAttrValueEntities();
//				
//				
//				List<BrandProdAttrValueEntity> a = new ArrayList<>();
//				
//				for (BrandProdAttrValueEntity brandProdAttrValueEntity : attrValueEntities) {
//					
//					
//					if(brandProdAttrValueEntity.getAttrEntity().getIsmultiple().intValue()==1)
//					{
//						
//						prodAttrValueEntityMapper.deleteByPrimaryKey(brandProdAttrValueEntity.getId());
//						String[] str = brandProdAttrValueEntity.getAttr_value().split(",");
//						
//						for (String string : str) {
//							
//							BrandProdAttrValueEntity attrValueEntity = new BrandProdAttrValueEntity();
//							
//							BeanUtilsExtends.copyProperties(attrValueEntity, brandProdAttrValueEntity);
//							attrValueEntity.setAttr_value(string);
//							
//							a.add(attrValueEntity);
//						}
//						
//					}
//					
//				}
//				
//				
//				for (BrandProdAttrValueEntity brandProdAttrValueEntity : a) {
//					
//					brandProdAttrValueEntity.setId(null);
//					prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
//					
//					
//				}
//				
//				
//			}
//       
//		}
//		return true;
//		
//		
//	}

}
