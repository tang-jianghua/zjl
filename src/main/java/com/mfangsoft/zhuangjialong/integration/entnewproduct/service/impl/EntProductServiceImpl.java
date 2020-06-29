package com.mfangsoft.zhuangjialong.integration.entnewproduct.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper.BaseEntProdImgEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper.BaseEntProdSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper.EntNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper.EntProdAttrValueEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper.EntProdSalesAttrEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdImgEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProductEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.service.EntProductService;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdImgEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service
public class EntProductServiceImpl implements EntProductService {

	@Autowired
	private EntProdSalesAttrEntityMapper prodSalesAttrEntityMapper;
	@Autowired
	private BaseEntProdSeriesEntityMapper baseBrandProdSeriesEntityMapper;
	@Autowired
	private   EntNewProductEntityMapper brandProductEntityMapper;
	@Autowired
	private  BaseEntProdImgEntityMapper baseBrandProdImgEntityMapper;
	@Autowired 
	private  EntProdAttrValueEntityMapper prodAttrValueEntityMapper;
	
	@Override
	public Boolean addEntProduct(EntProductEntity brandProductEntity) {
		// TODO Auto-generated method stub
		
		   this.popuEntProductEntity(brandProductEntity);
		   brandProductEntityMapper.insertSelective(brandProductEntity);
			
		  List<BaseEntProdSeriesEntity>	baseBrandProdSeriesEntities =brandProductEntity.getBaseBrandProdSeriesEntities();
		  
		  for (BaseEntProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
			  
			  baseBrandProdSeriesEntity.setProduct_id(brandProductEntity.getId());
			  
			  baseBrandProdSeriesEntityMapper.insertSelective(baseBrandProdSeriesEntity);
			
		  }
		  
		  List<BaseEntProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
		  
		  for(int i=0;i<baseBrandProdImgEntities.size();i++){
			  BaseEntProdImgEntity baseBrandProdImgEntity =baseBrandProdImgEntities.get(i);
			  
			  baseBrandProdImgEntity.setProduct_id(brandProductEntity.getId());
			  baseBrandProdImgEntity.setImg_sort(i);
			  baseBrandProdImgEntityMapper.insertSelective(baseBrandProdImgEntity);
		  }
		  List<BaseEntProdSalesAttrEntity> prodSalesAttrEntities=brandProductEntity.getServerprodSalesAttrEntity();
		  
		  for (BaseEntProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
			  
			  brandProdSalesAttrEntity.setData_flag("ins");
			  brandProdSalesAttrEntity.setProduct_id(brandProductEntity.getId());
			 
			  prodSalesAttrEntityMapper.insertSelective(brandProdSalesAttrEntity);
		  }
		  
		 List<EntProdAttrValueEntity> brandProdAttrValueEntities= brandProductEntity.getBrandProdAttrValueEntities();
		 
		 for (EntProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
			 
			 brandProdAttrValueEntity.setClass_id(brandProductEntity.getClass_id());
			 brandProdAttrValueEntity.setProduct_id(brandProductEntity.getId());
			 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
		}
		 EntProdAttrValueName attrValueName=brandProductEntityMapper.selectproductAttrValueNameByProductId(brandProductEntity.getId());
		 RedisUtils.getRedisTemplate().opsForHash().put("ent_product_attr_value_name", brandProductEntity.getId()+"", attrValueName);

		return true;
	}
	
	
	private void popuEntProductEntity(EntProductEntity brandProductEntity){
		
		UserEntity userEntity = UserContext.getCurrentUser();
		
		if(userEntity.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
			
			EnterpriseEntity  enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
			
			brandProductEntity.setEnterprise_id(enterpriseEntity.getId());
			
			brandProductEntity.setBuild_brand_id(enterpriseEntity.getBuildEnterpriseEntity().getId());
			brandProductEntity.setClass_id(enterpriseEntity.getBuildEnterpriseEntity().getClass_id());
			
			
		}
		
	}

	@Override
	public Boolean modfiyEntProduct(EntProductEntity brandProductEntity) {
		   this.popuEntProductEntity(brandProductEntity);
		   brandProductEntityMapper.updateByPrimaryKeyWithBLOBs(brandProductEntity);
			
		  List<BaseEntProdSeriesEntity>	baseBrandProdSeriesEntities =brandProductEntity.getBaseBrandProdSeriesEntities();
		  baseBrandProdSeriesEntityMapper.deleteProdSeriesByProductId(brandProductEntity.getId());
		  for (BaseEntProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
			  
			  
				  baseBrandProdSeriesEntity.setProduct_id(brandProductEntity.getId());
				  baseBrandProdSeriesEntityMapper.insertSelective(baseBrandProdSeriesEntity);
			
		  }
		  
		  List<BaseEntProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
		  
		  for(int i=0;i<baseBrandProdImgEntities.size();i++){
			  BaseEntProdImgEntity baseBrandProdImgEntity =baseBrandProdImgEntities.get(i);
			  
			  if(baseBrandProdImgEntity.getId()==null){
				  
				  baseBrandProdImgEntity.setProduct_id(brandProductEntity.getId());
				  baseBrandProdImgEntity.setImg_sort(i);
				  baseBrandProdImgEntityMapper.insertSelective(baseBrandProdImgEntity);
			  }else{
				  
				  baseBrandProdImgEntityMapper.updateByPrimaryKeySelective(baseBrandProdImgEntity);
			  }
			  
			 
		  }
		  List<BaseEntProdSalesAttrEntity> prodSalesAttrEntities=brandProductEntity.getServerprodSalesAttrEntity();
		  
		  for (BaseEntProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
			  
			  if(brandProdSalesAttrEntity.getId()==null){
				  brandProdSalesAttrEntity.setData_flag("ins");
			  brandProdSalesAttrEntity.setProduct_id(brandProductEntity.getId());
			  prodSalesAttrEntityMapper.insertSelective(brandProdSalesAttrEntity);
			  }else{
				  brandProdSalesAttrEntity.setData_flag("upd");
				  prodSalesAttrEntityMapper.updateByPrimaryKeySelective(brandProdSalesAttrEntity);
			  }
		  }
		  
		  
		 List<EntProdAttrValueEntity> brandProdAttrValueEntities= brandProductEntity.getBrandProdAttrValueEntities();
		 prodAttrValueEntityMapper.deleteProdattValueByProductId(brandProductEntity.getId());
		 for (EntProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
			 
			 brandProdAttrValueEntity.setProduct_id(brandProductEntity.getId());
			 
			 if(brandProdAttrValueEntity.getAttr_id().longValue()==1L){
				 brandProdAttrValueEntity.setId(null);
				 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
				
			 }else{
				 
				 EntProdAttrValueEntity attrValueEntity =prodAttrValueEntityMapper.selectProdattrValueByProductIdAndAttrId(brandProdAttrValueEntity);
				 brandProdAttrValueEntity.setId(attrValueEntity.getId());
				 prodAttrValueEntityMapper.updateByPrimaryKeySelective(brandProdAttrValueEntity);
			 }
		}
		 
		 
		 
		 EntProdAttrValueName attrValueName=brandProductEntityMapper.selectproductAttrValueNameByProductId(brandProductEntity.getId());
		 
		  RedisUtils.getRedisTemplate().opsForHash().delete("ent_product_attr_value_name", brandProductEntity.getId()+"");
		  RedisUtils.getRedisTemplate().opsForHash().put("ent_product_attr_value_name", brandProductEntity.getId()+"", attrValueName);
		return true;
	}

	@Override
	public EntProductEntity getEntProductById(Long id) {
		// TODO Auto-generated method stub
		
		EntProductEntity entProductEntity=	brandProductEntityMapper.selectAllByPrimaryKey(id);
		
		List<EntProdAttrValueEntity> attrValueEntities=prodAttrValueEntityMapper.selectProdattrValueByProductId(id);
		
		List<ClassAttrEntity> attrEntities=(List<ClassAttrEntity>) RedisUtils.getRedisTemplate().opsForHash().get("class_attr", entProductEntity.getClass_id()+"");
		
		for (EntProdAttrValueEntity prodAttrValueEntity : attrValueEntities) {
			
			for (ClassAttrEntity classAttrEntity : attrEntities) {
				
				if(prodAttrValueEntity.getAttr_id().longValue()==classAttrEntity.getId().longValue()){
					
					
					prodAttrValueEntity.setAttrEntity(classAttrEntity);
					
				}
				
				
			}
			
		}
		entProductEntity.setBrandProdAttrValueEntities(attrValueEntities);
		entProductEntity.setProductViewAttrAndVaule(brandProductEntityMapper.selectViewbyproduct(id));
		entProductEntity.setProdSalesAttrEntity(prodSalesAttrEntityMapper.getBrandProdSalesAttrByProductId(entProductEntity.getId()));
		
		return entProductEntity;
	}

	@Override
	public Page<Map<String, Object>> getEntProductForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		popuPage(page);
		Long t=System.currentTimeMillis();
		
		page.setData(brandProductEntityMapper.selectBrandProductForPage(page));
		
		
		List<Map<String, Object>> lss= page.getData();
		
		
		for (Map<String, Object> map : lss) {
			
			EntProdAttrValueName attrValueName=getAttrValueName(map.get("id").toString());
			
			if(attrValueName!=null){
				map.put("space", attrValueName.getSpace());
				
				map.put("style", attrValueName.getStyle());
				map.put("madin", attrValueName.getMadin());
			}else{
				
				EntProdAttrValueName as =brandProductEntityMapper.selectproductAttrValueNameByProductId(new Long(map.get("id").toString()));
				
				if(as!=null){
				map.put("space", as.getSpace());
				
				map.put("style", as.getStyle());
				map.put("madin", as.getMadin());
				}
			}
			
			
			
		}
		
		
		return page;
	}
	
	
	private EntProdAttrValueName  getAttrValueName (String id){
		
		
		try {
			return (EntProdAttrValueName) RedisUtils.getRedisTemplate().opsForHash().get("ent_product_attr_value_name", id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	


	@Override
	public Page<Map<String, Object>> getBrandDevProductListForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		page.setData(brandProductEntityMapper.getBrandDevProductListForPage(page));
		return page;
	}
	
	private void popuPage(Page<Map<String, Object>> page){
		
		
		
		Map<String,Object>  map = (Map<String, Object>) page.getParam();
		
		UserEntity userEntity = UserContext.getCurrentUser();
		
		if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			BrandEntity  brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			
			map.put("build_brand_id", brandEntity.getEnterprise_id());
			
		}
		if(userEntity.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
			
			EnterpriseEntity  enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
			
			map.put("build_brand_id", enterpriseEntity.getBuildEnterpriseEntity().getId());
			
			
		}
	}


	@Override
	public Boolean removeSalesAttr(Long id) {
		// TODO Auto-generated method stub
		
		prodSalesAttrEntityMapper.updateProdSalesAttr(id);
		
		return true;
	}


	@Override
	public List<EntProdAttrValueName> selectproductAttrValueName() {
		// TODO Auto-generated method stub
		return brandProductEntityMapper.selectproductAttrValueName();
	}


	@Override
	public int updateProdBold(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return brandProductEntityMapper.updateProdBold(map);
	}


	@Override
	public EntProdAttrValueName selectproductAttrValueNameByProductId(Long id) {
		// TODO Auto-generated method stub
		return brandProductEntityMapper.selectproductAttrValueNameByProductId(id);
	}


	@Override
	public Boolean addEntProductForCopy(EntProductEntity brandProductEntity) {
		// TODO Auto-generated method stub
		  this.popuEntProductEntity(brandProductEntity);
		   brandProductEntityMapper.insertSelective(brandProductEntity);
			
		  List<BaseEntProdSeriesEntity>	baseBrandProdSeriesEntities =brandProductEntity.getBaseBrandProdSeriesEntities();
		  
		  for (BaseEntProdSeriesEntity baseBrandProdSeriesEntity : baseBrandProdSeriesEntities) {
			  
			  baseBrandProdSeriesEntity.setProduct_id(brandProductEntity.getId());
			  
			  baseBrandProdSeriesEntityMapper.insertSelective(baseBrandProdSeriesEntity);
			
		  }
		  
		  List<BaseEntProdImgEntity> baseBrandProdImgEntities=brandProductEntity.getBaseBrandProdImgEntities();
		  
		  for(int i=0;i<baseBrandProdImgEntities.size();i++){
			  BaseEntProdImgEntity baseBrandProdImgEntity =baseBrandProdImgEntities.get(i);
			  
			  baseBrandProdImgEntity.setProduct_id(brandProductEntity.getId());
			  baseBrandProdImgEntity.setImg_sort(i);
			  baseBrandProdImgEntityMapper.insertSelective(baseBrandProdImgEntity);
		  }
		  List<BaseEntProdSalesAttrEntity> prodSalesAttrEntities=brandProductEntity.getServerprodSalesAttrEntity();
		  
		  for (BaseEntProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntities) {
			  
			  brandProdSalesAttrEntity.setData_flag("ins");
			  brandProdSalesAttrEntity.setProduct_id(brandProductEntity.getId());
			 
			  prodSalesAttrEntityMapper.insertSelective(brandProdSalesAttrEntity);
		  }
		  
		 List<EntProdAttrValueEntity> brandProdAttrValueEntities= brandProductEntity.getBrandProdAttrValueEntities();
		 
		 for (EntProdAttrValueEntity brandProdAttrValueEntity : brandProdAttrValueEntities) {
			 
			 brandProdAttrValueEntity.setClass_id(brandProductEntity.getClass_id());
			 brandProdAttrValueEntity.setProduct_id(brandProductEntity.getId());
			 prodAttrValueEntityMapper.insertSelective(brandProdAttrValueEntity);
		}
		
		 return true;
	}
	
	

	
}
