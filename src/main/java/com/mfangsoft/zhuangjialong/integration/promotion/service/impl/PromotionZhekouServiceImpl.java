package com.mfangsoft.zhuangjialong.integration.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.service.PromotionZhekouService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service
public class PromotionZhekouServiceImpl implements PromotionZhekouService {

	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	@Autowired
	PromotionZhekouProductEntityMapper promotionZhekouProductEntityMapper;
	@Autowired
	BrandNewProductEntityMapper brandNewProductEntityMapper;
	
	@Autowired
	PromotionEntityMapper promotionEntityMapper;
	
	public static void main(String[] args) {
		PromotionZhekouEntity r = new PromotionZhekouEntity();
		r.setDiscount(0.7);
		r.setEnd_time(new Date());
		r.setStart_time(new Date());
		r.setType(Integer.parseInt("1"));
		r.setUse_coupons_flag(true);
		r.setUse_redbag_flag(true);
		r.setUse_zhekou_flag(true);

		System.out.println(new Gson().toJson(r));

	}

	@Override
	public ResponseMessage addZhekouPromotion(PromotionZhekouEntity record, ResponseMessage responseMessage) {

		Long brand_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity p = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = p.getId();
		}
		
		record.setBrand_id(brand_id);

		if (record.getBrand_id() == null || record.getDiscount() == null || record.getType() == null
				|| record.getStart_time() == null || record.getEnd_time() == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

		// 需要discount brand_id start_time end_time
		List<PromotionZhekouEntity> list = promotionZhekouEntityMapper.selectBetweenList(record);

		if (list != null && list.size() > 0) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage("和现有工具的时间重复");
			return responseMessage;
		}

		record.setCreate_time(new Date());

		if (promotionZhekouEntityMapper.insertSelective(record) > 0) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}

	@Override
	public List<Map<String, String>> queryZhekouPromotionForPage(Page<Map<String, String>> param) {

		Long brand_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity p = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = p.getId();
		} else {
			return null;
		}

		Map map = (HashMap<String, Object>) param.getParam();
		map.put("brand_id", brand_id);

		return promotionZhekouEntityMapper.queryZhekouPromotionForPage(param);

	}

	@Override
	public PromotionZhekouEntity queryOneZhekouPromotion(Long id){
		
		return promotionZhekouEntityMapper.selectByPrimaryKey(id);
		
	}
	
	@Override
	public ResponseMessage modifyZhekouPromotion(PromotionZhekouEntity record, ResponseMessage responseMessage){
		
		promotionZhekouEntityMapper.updateByPrimaryKeySelective(record);
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		
		return responseMessage;
	}
	
	@Override
	public ResponseMessage addZhekouPromotionPorduct(List<PromotionZhekouProductEntity> param,
			ResponseMessage responseMessage) {

		if (param != null && param.size() > 0) {

//			PromotionZhekouEntity record = promotionZhekouEntityMapper.selectByPrimaryKey(param.get(0).getZhekou_id());
			// 需要brand_id start_time end_time ,查询其他折扣活动
//			String product_id_str = promotionZhekouEntityMapper.selectProductBetweenList(record);
			
			String product_id_str = promotionZhekouEntityMapper.selectProductOfOneTool(param.get(0).getZhekou_id());
			
			for (PromotionZhekouProductEntity entity : param) {
				if (StringUtils.isNotEmpty(product_id_str) && product_id_str.contains(entity.getProduct_id().toString())) {
					continue;
				}
				
				promotionZhekouProductEntityMapper.insert(entity);
			}
		}

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
	}

	public Page<Map<String,Object>> queryZhekouPromotionPorduct(Page<Map<String,Object>> page){
		
		Map<String,Object> map;
		
		if(page.getParam()==null){
			map= new HashMap<>();
		}else{
			map=(Map<String, Object>) page.getParam();
		}
		
		page.setData(brandNewProductEntityMapper.selectZhekouProductForPage(page));
		
		return page;
	}
	
	public void deleteZhekouPromotionPorduct(List<Long> id_list){
		
		for(Long id : id_list){
			promotionZhekouProductEntityMapper.deleteByPrimaryKey(id);
		}
		
	}

	@Override
	public List<Map<String, Object>> queryAllZhekouPorductBrandToChooseByPage(Page<Map<String, Object>> page) {

		Map<String,Object> map = (HashMap)page.getParam();
		if(map == null){
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
		
		PromotionZhekouEntity entity = promotionZhekouEntityMapper.selectByPrimaryKey(Long.valueOf(map.get("id").toString()));
		map.put("start_time", DateUtils.formatDate_(entity.getStart_time()));
		map.put("end_time", DateUtils.formatDate_(entity.getEnd_time()));
		
		
		List<String> existProduct = promotionZhekouProductEntityMapper.selectedExistZhekouPromotionBrandProduct(map);
		
		existProduct.add("0");
		
		map.put("existProductList", existProduct);
		
		List<Map<String, Object>> list = promotionZhekouProductEntityMapper.selectAllProductForPromotionForBrandByPage(page);
		
		return list;
	}
	
	/**
	 * 查询当前时间折扣工具价格
	 * @param brand_id
	 * @param product_id
	 * @param price
	 * @return
	 */
	public Double getZhekouPrice(Long brand_id, Long product_id, Double price){
		
		List<PromotionZhekouParam> list_zhekou_p = promotionZhekouEntityMapper.selectZheKouPromotionProduct();
		
		return getZhekouPriceTool(brand_id, list_zhekou_p, product_id, price);
	}
	
	/**
	 * 查询设定时间折扣工具价格
	 * @param brand_id
	 * @param product_id
	 * @param price
	 * @return
	 */
	public Double getZhekouPriceWithTime(Long brand_id, Long product_id, Double price, Date start_time, Date end_time){
		
		List<PromotionZhekouParam> list_zhekou_p = promotionZhekouEntityMapper.selectZheKouPromotionProductWithTime(start_time, end_time);
		
		return getZhekouPriceTool(brand_id, list_zhekou_p, product_id, price);
	}
	
	/**
	 * 工具
	 * @param brand_id
	 * @param product_id
	 * @param price
	 * @return
	 */
	public Double getZhekouPriceTool(Long brand_id, List<PromotionZhekouParam> list_zhekou_p, Long product_id, Double price){
		
		if(list_zhekou_p != null && list_zhekou_p.size() > 0){
			for(PromotionZhekouParam pp : list_zhekou_p){
				if(pp == null) continue;
				if(pp.getIsAllProduct().equals(1) && pp.getBrand_id().equals(brand_id)){//选中全部产品
					return pp.getDiscount() * price;
				}else{
					if(pp.getZhekouProductEntity() != null){
						for(PromotionZhekouProductEntity p : pp.getZhekouProductEntity()){
							if(product_id.equals(p.getProduct_id())){
								return pp.getDiscount() * price;
							}
						}
					}
				}
			}
		}
		
		return price;
	}
}
