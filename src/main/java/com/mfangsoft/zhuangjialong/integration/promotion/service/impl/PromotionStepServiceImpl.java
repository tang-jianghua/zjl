package com.mfangsoft.zhuangjialong.integration.promotion.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.ArrayRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionStepConditionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionStepProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillTimeEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepConditionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;
import com.mfangsoft.zhuangjialong.integration.promotion.service.PromotionStepService;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service
public class PromotionStepServiceImpl implements PromotionStepService {

	@Autowired
	PromotionEntityMapper promotionEntityMapper;
	@Autowired
	PromotionStepConditionEntityMapper promotionStepConditionEntityMapper;
	@Autowired
	PromotionStepProductEntityMapper promotionStepProductEntityMapper;
	@Autowired
	UnionProductMapper unionProductMapper;
	@Autowired
	PromotionSeckillProductEntityMapper promotionSeckillProductEntityMapper;
	@Autowired
	PromotionZhekouProductEntityMapper promotionZhekouProductEntityMapper;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	@Autowired
	PromotionZhekouServiceImpl promotionZhekouServiceImpl;
	
	// private void permit(Page<Map<String, Object>> page){
	//
	// }

	@Override
	public ResponseMessage<String> addPlatformStepPromotion(PromotionEntityStepParam record) {

		ResponseMessage<String> responseMessage = new ResponseMessage<String>();
		record.setCreate_time(new Date());
		record.setCreater_type(0);
		promotionEntityMapper.insertSelective(record);
		long id = record.getId();
		if (id > 0) {

			List<PromotionStepConditionEntity> list = record.getStepConditionEntityList();
			for (PromotionStepConditionEntity entity : list) {
				entity.setNote_time(new Date());
				entity.setPromotion_id(record.getId());
				promotionStepConditionEntityMapper.insert(entity);
			}

			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			throw new RuntimeException();
		}
		return responseMessage;
	}

	@Override
	public List<Map<String, Object>> queryPlatformStepPromotionListByPage(Page<Map<String, Object>> page) {

		Map<String, Object> map;

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();
		} else {
			map = new HashMap<>();
			page.setParam(map);
		}

		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.SHOP.getIndex()) {

			return null;
		}
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", p.getId() + "");

		} else if (u.getUser_type() == UserType.ENTERPRISE.getIndex()) {
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", enterpriseEntity.getId());
		}

		return promotionEntityMapper.getStepPromotionForPage(page);
	}

	@Override
	public Map<String, Object> getPlatformStepPromotionByid(Long id) {

		Long brand_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity b = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = b.getId();
		}

		HashMap<String, Object> param = promotionEntityMapper.selectByPrimaryKeyForStep(id, brand_id);
		param.put("stepConditionEntityList", promotionStepConditionEntityMapper.selectListByPromotionId(id));

		return param;

	}

	@Override
	public void modifyPlatformPromotion(PromotionEntityStepParam param) {

		promotionEntityMapper.updateByPrimaryKeySelective(param);

		List<PromotionStepConditionEntity> list = param.getStepConditionEntityList();

		if (list != null) {
			for (PromotionStepConditionEntity entity : list) {
				if (entity.getId() == null) {
					entity.setPromotion_id(param.getId());
					promotionStepConditionEntityMapper.insertSelective(entity);
				}
				// promotionStepConditionEntityMapper.updateByPrimaryKeySelective(entity);
			}
		}
	}

	public boolean deletePlatformPromotion(PromotionStepConditionEntity param) {

		return promotionStepConditionEntityMapper.deleteByPrimaryKey(param.getId()) > 0;

	}

	public boolean registerPlatformPromotion(PromotionEntity param) {

		Long brand_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity b = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = b.getId();
		}

		if (brand_id == null) {
			return false;
		}

		PromotionEntity record = promotionEntityMapper.selectByPrimaryKey(param.getId());
		String brand_id_p = record.getBrand_id();

		if (brand_id_p == null) {
			record.setBrand_id(brand_id.toString());
		} else {
			record.setBrand_id(brand_id_p + "," + brand_id);
		}

		if (promotionEntityMapper.updateByPrimaryKeySelective(record) > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean addStepPromotionProducts(PromotionEntityStepParam param) {

		Long brand_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity b = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = b.getId();
		}

		if (brand_id == null) {
			return false;
		}

		List<Long> list = param.getProduct_id_list();
		for (Long product_id : list) {
			PromotionStepProductEntity record = new PromotionStepProductEntity();
			record.setPromotion_id(param.getId());
			record.setBrand_id(brand_id);
			record.setNote_time(new Date());
			record.setProduct_id(product_id);
			record.setState(0);

			promotionStepProductEntityMapper.insertSelective(record);
		}

		return true;
	}

	@Override
	public List<Product> getStepPromotionProductList(PromotionStepProductEntity param) {

		Long brand_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity b = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = b.getId();
		}

		if (brand_id == null) {
			return null;
		}

		param.setBrand_id(brand_id);

		return promotionStepProductEntityMapper.selectProductOfOneStepPromotionForBrand(param);

	}

	public PromotionEntityStepParam getStepPromotionProductForPlatform(PromotionEntityStepParam param) {

		
		UserEntity u = UserContext.getCurrentUser();

		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			param.setBrand_id_long(r.getId());
		} else if (u.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			param.setPartner_id_long(p.getId());

		} else if (u.getUser_type() == UserType.ENTERPRISE.getIndex()) {
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			param.setEnterprise_id(enterpriseEntity.getId());

		}

		// 参数promotion_id
		PromotionEntityStepParam list = promotionStepProductEntityMapper.selectProductOfOneStepPromotionForPlatform(param);
		
		if(list == null) return new PromotionEntityStepParam();
		
		List<BrandProduct> brandProduct = list.getBrandProduct();
		
		if(brandProduct == null) return list;
		
		PromotionEntity entity = promotionEntityMapper.selectByPrimaryKey(param.getId());
		List<PromotionZhekouParam> list_zhekou_p = promotionZhekouEntityMapper.selectZheKouPromotionProductWithTime(entity.getStart_time(), entity.getEnd_time());
		
		for (BrandProduct pt : brandProduct) {
			
			pt.setProduct_state0(new ArrayList<Product>());
			pt.setProduct_state1(new ArrayList<Product>());
			pt.setProduct_state2(new ArrayList<Product>());
			pt.setProduct_state3(new ArrayList<Product>());
			pt.setProduct_state4(new ArrayList<Product>());
			pt.setProduct_state5(new ArrayList<Product>());
			
			List<Product> product_list = pt.getProduct();
			if(product_list == null) continue;
			
			for (Product p : product_list) {
				if(p == null) continue;
				Double promotion_price = promotionZhekouServiceImpl.getZhekouPriceTool(pt.getBrand_id(), list_zhekou_p, p.getProduct_id(), p.getMin_price());
				p.setNew_price(NumberUtil.round(promotion_price, 2, BigDecimal.ROUND_HALF_DOWN));
				
				switch (p.getState()) {
				case 0:
					pt.getProduct_state0().add(p);
					break;
				case 1:
					pt.getProduct_state1().add(p);
					break;
				case 2:
					pt.getProduct_state2().add(p);
					break;
				case 3:
					pt.getProduct_state3().add(p);
					break;
				case 4:
					pt.getProduct_state4().add(p);
					break;
				case 5:
					pt.getProduct_state5().add(p);
					break;
				}
			}
			pt.setProduct(null);

		}
		return list;
	}

	public Page<Map<String, Object>> selectAllProductForBrandToAddByPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = (HashMap) page.getParam();
		if (map == null) {
			return null;
		}

		Long brand_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity p = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = p.getId();
		} else {
			return null;
		}

		map.put("brand_id", brand_id);

		PromotionEntity entity = promotionEntityMapper.selectByPrimaryKey(Long.valueOf(map.get("id").toString()));
		map.put("start_time", DateUtils.formatDate_(entity.getStart_time()));
		map.put("end_time", DateUtils.formatDate_(entity.getEnd_time()));

		List<String> unionProduct = unionProductMapper.selectedExistUnionPromotionBrandProduct(map);
		List<String> existScendSkillProduct = promotionSeckillProductEntityMapper
				.selectedExistScendSkillPromotionBrandProduct(map);
		List<String> existManProduct = promotionSeckillProductEntityMapper.selectedExistManPromotionBrandProduct(map);

		List<String> existProduct = new ArrayList<String>();
		existProduct.add("0");
		
		if (unionProduct != null && unionProduct.size() > 0) {
			existProduct.addAll(unionProduct);
		}
		if (existScendSkillProduct != null && existScendSkillProduct.size() > 0) {
			existProduct.addAll(existScendSkillProduct);
		}
		if (existManProduct != null && existManProduct.size() > 0) {
			existProduct.addAll(existManProduct);
		}

		map.put("existProductList", existProduct);

		page.setData(promotionZhekouProductEntityMapper.selectAllProductForPromotionForBrandByPage(page));

		return page;
	}

	@Override
	public boolean removeStepPromotionProductForBrand(List<Long> param) {

		for (Long id : param) {
			promotionStepProductEntityMapper.deleteByPrimaryKey(id);
		}

		return true;
	}

	@Override
	public ResponseMessage<String> cancleRegisterPlatformPromotion(Long id) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		PromotionEntity entity = promotionEntityMapper.selectByPrimaryKey(id);

		Long brand_id = null;
		UserEntity userEntity = UserContext.getCurrentUser();
		if (userEntity.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = r.getId();
		}

		if (brand_id == null) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("品牌ID未找到");
			return message;
		}

		if (promotionStepProductEntityMapper.selectProductStateForBrandId(id, brand_id) > 0) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("请先下架已存在的产品");
			return message;
		}

		if (entity.getBrand_id() == null) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("还没有报名");
			return message;
		} else {

			List<String> list = Arrays.asList(entity.getBrand_id().split(","));
			list = new ArrayList<>(list);
			for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
				String s = iter.next();
				if (s.equals(brand_id.toString())) {
					iter.remove();
				}
			}
			if (list.size() <= 0) {
				entity.setBrand_id(null);
			} else {
				StringBuilder sb = new StringBuilder();
				for (String s : list) {
					sb.append(s);
				}
				entity.setBrand_id(sb.toString());
			}
			int result = promotionEntityMapper.updateBrandIdByPrimaryKey(id, entity.getBrand_id());
			if (result > 0) {
				promotionStepProductEntityMapper.deleteByBrandIdForTimePart(id, brand_id);// 删除刚刚提交的产品
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			} else {
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage("修改失败");
			}
		}
		return message;

	}

	// public List<Map<String,Object>>
	// getbrandManpromotionListByPage1(Page<Map<String,Object>> page){
	//
	// Map<String, String> map = (HashMap<String, String>) page.getParam();
	//
	// Long brand_id = null;
	//
	// UserEntity u = UserContext.getCurrentUser();
	//
	// if (u.getUser_type() == UserType.PARTNER.getIndex() || u.getUser_type()
	// == UserType.ENTERPRISE.getIndex()
	// || u.getUser_type() == UserType.PLATFORM.getIndex()) {
	//
	// return null;
	// }
	// if (u.getUser_type() == UserType.BRAND.getIndex()) {
	// BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
	// brand_id = r.getId();
	// } else if (u.getUser_type() == UserType.SHOP.getIndex()) {
	// ShopEntity s = (ShopEntity) UserContext.getCurrentUserInfo();
	// brand_id = s.getBrand_id();
	// }
	// if (brand_id != null) {
	// map.put("brand_id", brand_id.toString());
	// }else{
	// return null;
	// }
	//
	// List<Map<String, String>> list_p =
	// promotionEntityMapper.getbrandpromotionList1ByPage(page);
	//
	// for (int i = 0; i < list_p.size(); i++) {
	// Map<String, String> map1 = new HashMap<>();
	// if (list_p.get(i) == null)
	// continue;
	// // Map map2 = (HashMap)list_p.get(i);
	// if (list_p.get(i).get("brand_ids") != null) {
	// List<String> list = Arrays.asList(((String)
	// list_p.get(i).get("brand_ids")).split(","));
	// for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
	// String s = iter.next();
	// if (StringUtils.isNotEmpty(s)) {
	// map1.put(s, "no_use");
	// }
	// }
	// list_p.get(i).put("brand_id_count", map1.size() + "");
	// }
	// }
	// return list_p;
	//
	// }
	//
	//
	// public List<Map<String,Object>>
	// getbrandManpromotionListByPage2(Page<Map<String,Object>> page){
	//
	//
	// return null;
	// }

	public boolean modifyStepPromotionProductForBrand(List<PromotionStepProductEntity> param){
		boolean flag = true;
		if(param != null){
			for(PromotionStepProductEntity record : param){
				record.setNote_time(new Date());
				flag = flag && promotionStepProductEntityMapper.updateByPrimaryKeySelective(record) > 0;
			}
		}
		
		
		return flag;
		
		
	}
	
	
}
