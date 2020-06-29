package com.mfangsoft.zhuangjialong.integration.promotion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Case;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.StringUtils;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
import com.mfangsoft.zhuangjialong.integration.promotion.service.UnionPromotionService;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
@Service
public class UnionPromotionServiceImpl implements UnionPromotionService {

	@Autowired
	private UnionPromotionMapper unionPromotionMapper;
	
	@Autowired
	private UnionProductMapper unionProductMapper;
	@Autowired
	private BrandNewProductEntityMapper  brandProductEntityMapper;
	
	@Autowired
	private BrandEntityMapper brandEntityMapper;
	
	
	
	@Override
	public Boolean addUnionPromotion(UnionPromotion unionPromotion) {
		// TODO Auto-generated method stub
		PartnerEntity partnerEntity=(PartnerEntity) UserContext.getCurrentUserInfo();
		if(partnerEntity!=null){
			unionPromotion.setPartner_id(partnerEntity.getId());
		}else{
			return false;
		}

		unionPromotionMapper.insertSelective(unionPromotion);
		
		Integer upID = unionPromotion.getId();
		if (unionPromotion.getTotal_flag() == 1){//全部产品
			//通过合伙人id找到所有品牌
			//List<Map<String, Object>> bml = brandEntityMapper.selectBrandNameByPartner(partnerEntity.getId());
			//StringBuilder sb = new StringBuilder();
			String[] bIDs = unionPromotion.getBrand_ids().split(",");
			//for (int i=0;i<bml.size();i++){
			List<UnionProduct> upList = new ArrayList<UnionProduct>();
			for (String bID : bIDs){
//				if (i != bml.size()-1)
//					sb.append(bml.get(i).get("id") + ",");
				if (StringUtil.isBlank(bID))
					continue;
				//通过品牌id找到所有品牌产品
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("start_time", unionPromotion.getStart_time());
				param.put("end_time", unionPromotion.getEnd_time());
				param.put("brand_id", bID);
				List<Long> bpIDList = unionProductMapper.findNotSelectedBrandProductIDList(param);
				//List<Long> bpIDList = brandProductEntityMapper.selectUnionByBrands(bID);
//				List<UnionProduct> upList = new ArrayList<UnionProduct>();
				for (Long bpID : bpIDList){
					UnionProduct up = new UnionProduct();
					up.setBrand_id(Long.valueOf(bID));
					up.setProduct_id(bpID);
					up.setPromotion_id(upID);
					//unionProductMapper.insertSelective(up);//插入改品牌下所有产品
					upList.add(up);
				}
			}
			try {
				unionProductMapper.batchInsert(upList);
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
		return true;
	}
	@Override
	public Boolean modifyUnionPromotion(UnionPromotion unionPromotion) {
		// TODO Auto-generated method stub
		PartnerEntity partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();
		//添加合伙人id
		if (partnerEntity != null) {
			unionPromotion.setPartner_id(partnerEntity.getId());
		} else {
			return false;
		}
		Integer unionPromotionID = unionPromotion.getId();
		//原数据
		Map<String, Object> unionPromotionOriginal = unionPromotionMapper.selectByPrimaryKey(unionPromotionID);
		//数据库中原品牌id数组
		String[] originalBrandIDs = String.valueOf(unionPromotionOriginal.get("brand_ids")).split(",");
		//前端新修改传入的品牌id数组
		String[] newBrandIDs = unionPromotion.getBrand_ids().split(",");
		//交集
		String[] intersectBrandIDs = StringUtils.intersect(originalBrandIDs, newBrandIDs);
		
		//差集 删除掉的品牌id数组
		String[] deleteBrandIDs = StringUtils.minus(intersectBrandIDs, originalBrandIDs);
		if(deleteBrandIDs.length>0){
			unionProductMapper.deleteByUnionPromotionIDANDBrandIDs(unionPromotionID, deleteBrandIDs);
		}

		//差集 新增的品牌id数组
		String[] addBrandIDs = StringUtils.minus(intersectBrandIDs, newBrandIDs);
		if (addBrandIDs.length > 0 && unionPromotion.getTotal_flag() == 1) {// 全部产品
			List<UnionProduct> upList = new ArrayList<UnionProduct>();
			for (String bID : addBrandIDs) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("start_time", unionPromotion.getStart_time());
				param.put("end_time", unionPromotion.getEnd_time());
				param.put("brand_id", bID);
				List<Long> bpIDList = unionProductMapper.findNotSelectedBrandProductIDList(param);
				//List<Long> bpIDList = brandProductEntityMapper.selectUnionByBrands(bID);
				for (Long bpID : bpIDList) {
					UnionProduct up = new UnionProduct();
					up.setBrand_id(Long.valueOf(bID));
					up.setProduct_id(bpID);
					up.setPromotion_id(unionPromotion.getId());
					upList.add(up);
					//unionProductMapper.insertSelective(up);// 插入改品牌下所有产品
				}
			}
			unionProductMapper.batchInsert(upList);
		}

		unionPromotionMapper.updateByPrimaryKeySelective(unionPromotion);

		return true;
	}
	
	
	

	@Override
	public Page<Map<String, Object>> queryUnionPromotionForPage(Page<Map<String, Object>> map) {
		// TODO Auto-generated method stub
		Map<String, Object> param = (Map<String, Object>) map.getParam();
		//不同权限只能看到自己创建的活动
		switch (UserContext.getCurrentUser().getUser_type()){
		case 2://企业
			param.put("enterprise_id",((EnterpriseEntity) UserContext.getCurrentUserInfo()).getId());
			break;
		case 3://城市合伙人
			param.put("partner_id", ((PartnerEntity) UserContext.getCurrentUserInfo()).getId());
			break;
		case 4://品牌管理员
			param.put("brand_ids", ((BrandEntity) UserContext.getCurrentUserInfo()).getId());
			break;
		case 5://店铺管理员
			param.put("shop_id", ((ShopEntity) UserContext.getCurrentUserInfo()).getId());
			break;
		}	
		map.setData(unionPromotionMapper.queryUnionPromotionForPage(map));
		return map;
	}

	@Override
	public Page<Map<String, Object>> queryProductForPromotionForPage(Page<Map<String, Object>> map) {
		// TODO Auto-generated method stub
		
		map.setData( brandProductEntityMapper.queryProductForPromotionForPage(map));
		return map;
	}

	@Override
	public List<Map<String, Object>> queryBrandName() {
		// TODO Auto-generated method stub
		
		PartnerEntity partnerEntity=(PartnerEntity) UserContext.getCurrentUserInfo();
		
		
		return brandEntityMapper.selectBrandNameByPartner(partnerEntity.getId());
	}

	@Override
	public Boolean addUnionPromotionProduct(UnionPromotion unionPromotion) {
		// TODO Auto-generated method stub
		
		if(unionPromotion.getTotalflag().equals("1")){
			
			for (UnionProduct unionProduct : unionPromotion.getUnionProducts()) {
				unionProduct.setPromotion_id(unionPromotion.getId());
				
				unionProductMapper.insertSelective(unionProduct);
				
				
			}
			
		}else
		{
			List<Long> l=brandProductEntityMapper.selectUnionByBrands(unionPromotion.getBrand_ids());
			
			for (Long long1 : l) {
				
				UnionProduct product = new UnionProduct();
				
				product.setProduct_id(long1);
				product.setPromotion_id(unionPromotion.getId());
				unionProductMapper.insertSelective(product);
			}
			
		}
		return null;
	}

	@Override
	public Boolean modifyUnionPromotionProduct(UnionPromotion unionPromotion) {
		// TODO Auto-generated method stub
		unionProductMapper.deleteByUnionPromotionID(unionPromotion.getId());
		if(unionPromotion.getTotalflag().equals("1")){
			
			for (UnionProduct unionProduct : unionPromotion.getUnionProducts()) {
				unionProduct.setPromotion_id(unionPromotion.getId());
				
				unionProductMapper.insertSelective(unionProduct);
				
				
			}
			
		}else
		{
			List<Long> l=brandProductEntityMapper.selectUnionByBrands(unionPromotion.getBrand_ids());
			
			for (Long long1 : l) {
				
				UnionProduct product = new UnionProduct();
				
				product.setProduct_id(long1);
				product.setPromotion_id(unionPromotion.getId());
				unionProductMapper.insertSelective(product);
			}
			
		}
		return true;
	}

	@Override
	public Boolean deleteUnionPromotion(Integer id) {
		try{
			unionPromotionMapper.deleteByPrimaryKey(id);
			unionProductMapper.deleteByUnionPromotionID(id);
		}
		catch (Exception e){
			return false;
		}
		return true;
	}
	
	@Override
	public Boolean modifyOnOffFlag(Integer unionPromotionID, Integer onOffFlag) {
		int effectNum = 0; 
		effectNum = unionPromotionMapper.updateOnOffFlag(unionPromotionID, onOffFlag);
		if (effectNum > 0)
			return true;
		return false;
	}

}
