package com.mfangsoft.zhuangjialong.integration.series.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.department.model.DepartmentEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper.BaseEntProdSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BaseBrandProdSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.mapper.BrandSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.series.mapper.EntSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.model.EntSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.service.BrandSeriesService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class BrandSeriesServiceImpl  implements  BrandSeriesService {

	
	@Autowired
	private BrandSeriesEntityMapper brandSeriesEntityMapper;
	
	@Autowired
	private BaseBrandProdSeriesEntityMapper baseBrandProdSeriesEntityMapper;
	
	
	@Autowired
	private BaseEntProdSeriesEntityMapper baseEntProdSeriesEntityMapper;
	
	
	@Autowired
	private EntSeriesEntityMapper entSeriesEntityMapper;
	
	@Override
	public List<BrandSeriesEntity> getBrandSeriesEntityList(Long brand_id) {
		// TODO Auto-generated method stub
		return brandSeriesEntityMapper.getBrandSeriesEntityList(brand_id);
	}
	@Override
	public Boolean addBrandSeries(BrandSeriesEntity brandSeriesEntity) {
		// TODO Auto-generated method stub
		
		if(brandSeriesEntityMapper.insert(brandSeriesEntity)>0){
			
			return true;
		}
		return false;
	}
	@Override
	public BrandSeriesEntity getBrandSeriesById(Long id) {
		// TODO Auto-generated method stub
		return brandSeriesEntityMapper.selectByPrimaryKey(id);
	}
	@Override
	public Boolean modifyBrandSeries(BrandSeriesEntity brandSeriesEntity) {
		// TODO Auto-generated method stub
		
		if(brandSeriesEntityMapper.updateByPrimaryKeySelective(brandSeriesEntity)>0){
			return true;
		}
		return false;
	}
	@Override
	public Page<BrandSeriesEntity> queryBrandSeriesForPage(Page<BrandSeriesEntity> page) {
		// TODO Auto-generated method stub
		
		 page.setData(brandSeriesEntityMapper.queryBrandSeriesForPage(page));
		return page;
	}
	@Override
	public List<Map<String, Object>> getClassifySeries() {
		// TODO Auto-generated method stub
		
		
		 BrandEntity brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
		 Map<String,Object> map = new HashMap<String,Object>();
		 
		 map.put("brand_id", brandEntity.getId());
		 
		 
		return brandSeriesEntityMapper.getClassifySeries(map);
	}
	
	
	
	
	@Override
	public Boolean addEntSeries(EntSeriesEntity entSeriesEntity) {
		// TODO Auto-generated method stub
		
		if(entSeriesEntityMapper.insert(entSeriesEntity)>0){
			
			return true;
		}
		return false;
	}
	@Override
	public Boolean modifyEntSeries(EntSeriesEntity entSeriesEntity) {
		// TODO Auto-generated method stub
		
		if(entSeriesEntityMapper.updateByPrimaryKeySelective(entSeriesEntity)>0){
			
			return true;
		}
		return false;
	}
	@Override
	public List<Tree> getClassifySeriesForTree() {
		// TODO Auto-generated method stub
		List<Tree> list = new ArrayList<>();
		
		
		
		 BrandEntity brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
		 
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("brand_id", brandEntity.getId());
		
		return this.getParent(list, brandSeriesEntityMapper.getClassifySeries(map));
	}
	
	private  List<Tree> getParent(List<Tree> list,List<Map<String,Object>> maps)
	{
		for (Map map : maps) {
			if(map.get("parent_id")==null){
				Tree<Tree> tree = new Tree<>();
				tree.setText(map.get("name").toString());
				tree.setId(map.get("id").toString());
				tree.setSprite("folder");
				tree.setItems(this.getChildren(map.get("id"), maps));
				list.add(tree);
			}
			
		}
		
		return list;
		
	}
	
	private List<Tree> getChildren(Object parentId,List<Map<String,Object>> maps){
		
		List<Tree>  trees  = new ArrayList<>();
		
		for (Map map : maps) {
			
			if(map.get("parent_id")==null){
				
				continue;
			}
		
			if(new Double(map.get("parent_id").toString()).intValue()==new Double(parentId.toString()).intValue()){
				Tree<Tree> tree = new Tree<>();
				tree.setId(map.get("id")+"");
				tree.setText(map.get("name").toString());
				tree.setSprite("folder");
				tree.setItems(this.getChildren(map.get("id").toString(), maps));
				trees.add(tree);
			}
			
		}
		
		return trees;
	}
	@Override
	public List<BrandSeriesEntity> getBrandSeriesListByClassify(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return brandSeriesEntityMapper.getBrandSeriesListByClassify(map);
	}
	@Override
	public List<Tree> getEntClassifySeriesForTree() {
		
		UserEntity  user = UserContext.getCurrentUser();
		if(user.getUser_type()==UserType.ENTERPRISE.getIndex()){
			
			EnterpriseEntity enterpriseEntity=(EnterpriseEntity) UserContext.getCurrentUserInfo();
			 Map<String,Object> map = new HashMap<String,Object>();
			 map.put("ent_id", enterpriseEntity.getId());
			
			List<Tree> list = new ArrayList<>();
		return this.getParent(list, entSeriesEntityMapper.getClassifySeries(map));
			
		}else{
			
			List<Tree> list = new ArrayList<>();
			
			
			return list;
			
		}
		
		
	}
	@Override
	public List<Map<String, Object>> getEntClassifySeries() {
		// TODO Auto-generated method stub
		

		EnterpriseEntity enterpriseEntity=(EnterpriseEntity) UserContext.getCurrentUserInfo();
  
		 
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("ent_id", enterpriseEntity.getId());
		return entSeriesEntityMapper.getClassifySeries(map);
	}
	@Override
	public List<EntSeriesEntity> getEntSeriesListByClassify(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return entSeriesEntityMapper.getEntSeriesListByClassify(map);
	}
	@Override
	public Boolean removeBrandSeries(Long id) {
		// TODO Auto-generated method stub
		
		List<BaseBrandProdSeriesEntity> baseBrandProdSeriesEntities=baseBrandProdSeriesEntityMapper.selectBySeriesId(id);
		
		if(baseBrandProdSeriesEntities!=null&&baseBrandProdSeriesEntities.size()==0){
			brandSeriesEntityMapper.deleteByPrimaryKey(id);
			return true;
		}
		
		return false;
	}
	@Override
	public Boolean removeEntSeries(Long id) {
		// TODO Auto-generated method stub
		
		
		List<BaseEntProdSeriesEntity> baseEntProdSeriesEntities=baseEntProdSeriesEntityMapper.selectBySeriesId(id);
		
		if(baseEntProdSeriesEntities!=null&&baseEntProdSeriesEntities.size()==0){
			
			entSeriesEntityMapper.deleteByPrimaryKey(id);
			return true;
		}
		
		return false;
	}

}
