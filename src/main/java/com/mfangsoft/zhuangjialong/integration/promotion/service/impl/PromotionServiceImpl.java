package com.mfangsoft.zhuangjialong.integration.promotion.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.PartnerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillTimeEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.ProductState;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillTimeEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.service.PromotionService;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

import cn.jpush.api.report.UsersResult.User;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	PromotionEntityMapper promotionEntityMapper;
	@Autowired
	PromotionSeckillTimeEntityMapper promotionSeckillTimeEntityMapper;
	@Autowired
	PartnerEntityMapper partnerEntityMapper;
	@Autowired
	PromotionSeckillProductEntityMapper promotionSeckillProductEntityMapper;
	@Autowired
	UnionProductMapper unionProductMapper;
	
	@Override
	public ResponseMessage<String> addPromotion(PromotionEntity record) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		record.setCreate_time(new Date());
		record.setCreater_type(0);// 系统开发者

//		if (record.getEnd_time() != null) {
//			String date = DateUtils.formatDate_(record.getEnd_time());
//			date = date.split(" ")[0] + " 23:59:59";
//			try {
//				record.setEnd_time(DateUtils.parseDate_(date));
//			} catch (ParseException e) {
//				message.setCode(SysConstant.FAILURE_CODE);
//				message.setMessage("时间格式不正确");
//				return message;
//			}
//		}
		
		if (record.getLimit_time() != null) {
			String date = DateUtils.formatDate_(record.getLimit_time());
			date = date.split(" ")[0] + " 23:59:59";
			try {
				record.setLimit_time(DateUtils.parseDate_(date));
			} catch (ParseException e) {
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage("时间格式不正确");
				return message;
			}
		}
		
		if(record.getLimit_time().after(record.getStart_time())){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("报名截止时间不能晚于开始时间");
			return message;
		}
		
		if (record.getPartner_id() != null) {
			List<PromotionEntity> list = promotionEntityMapper.selectAll();
			if (list != null) {

				String[] str = record.getPartner_id().split(",");
				for (String s : str) {

					for (PromotionEntity p : list) {
						boolean flag = false;
						if (p.getPartner_id() != null) {
							String[] str_db = p.getPartner_id().split(",");
							for (String s_db : str_db) {
								if (s.equals(s_db)) {
									flag = true;// 包含
									break;
								}
							}
						}

						if (flag) {
							long start_time = record.getStart_time().getTime();
							long end_time = record.getEnd_time().getTime();
							long st = p.getStart_time().getTime();
							long et = p.getEnd_time().getTime();

							if ((start_time <= st && end_time >= et) || (start_time >= st && start_time < et)
									|| (end_time > st && end_time <= et)) {
								message.setCode(SysConstant.FAILURE_CODE);
								message.setMessage("设置的时间不能和现有秒杀活动时间重叠");
								return message;
							}
						}
					}
				}
			}
		}
		promotionEntityMapper.insertSelective(record);
		long id = record.getId();
		if (id > 0) {
			List<PromotionSeckillTimeEntity> timeEntity = record.getTimeEntity();
			if (timeEntity != null) {
				int i = 0;
				for (PromotionSeckillTimeEntity t : timeEntity) {
					t.setPid(id);
					promotionSeckillTimeEntityMapper.insertSelective(t);
					i++;
				}
				for (; i < 5; i++) {
					PromotionSeckillTimeEntity t = new PromotionSeckillTimeEntity();
					t.setPid(id);
					promotionSeckillTimeEntityMapper.insertSelective(t);
				}
			}
		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}

	public static void main(String[] args) {

		System.out.println("吗".charAt(0) >= 'A' && "吗".charAt(0) <= 'Z');

	}

	@Override
	public List<Map<String,String>> queryplatformpromotionListByPage(Page<Map<String,String>> page) {

		Map<String, Object> map;

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();
		} else {
			map = new HashMap<>();
			page.setParam(map);
		}

		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex() || u.getUser_type() == UserType.SHOP.getIndex()) {

			return null;
		}
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", p.getId() + "");

		} else if (u.getUser_type() == UserType.ENTERPRISE.getIndex()) {
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", enterpriseEntity.getId());

		}

		List<Map<String,String>> list = promotionEntityMapper.getSencondKillPromotionForPage(page);
		for(Map<String,String> p : list){
			Map<String,String> map_temp = new HashMap<String,String>();
			String brand_id_group = p.get("brand_id_group");
			if(brand_id_group != null){
				List<String> list1 = Arrays.asList(brand_id_group.split(","));
				for(String s : list1){
					map_temp.put(s, "value");
				}
				p.put("brand_id_count", map_temp.size() + "");
			}
			map_temp = null;
		}
		
		
		return list;

	}

	@Override
	public PromotionEntity getPlatformPromotionById(Long id) {

		Long brand_id = 0L;
		UserEntity userEntity = UserContext.getCurrentUser();
		if (userEntity.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = r.getId();
		}

		PromotionEntity entity = promotionEntityMapper.selectSencondKillPromotionByPrimaryKey(id, brand_id);

		if (entity != null && entity.getTimeEntity() != null) {
			List<PromotionSeckillTimeEntity> listT = entity.getTimeEntity();
			for (PromotionSeckillTimeEntity t : listT) {
				if (t.getPstart_time() != null) {
					t.setPstart_time_str(DateUtils.formatTime(t.getPstart_time().getTime()));
					t.setPstart_time(null);
				}
				if (t.getPend_time() != null) {
					t.setPend_time_str(DateUtils.formatTime(t.getPend_time().getTime()));
					t.setPend_time(null);
				}
			}
		}

		return entity;
	}

	@Override
	public boolean modifyPromotion(PromotionEntity record) throws ParseException {

		record.setUpdate_time(new Date());

		if (record.getEnd_time() != null) {
			String date = DateUtils.formatDate_(record.getEnd_time());
			date = date.split(" ")[0] + " 23:59:59";
			record.setEnd_time(DateUtils.parseDate_(date));

		}

		promotionEntityMapper.updateByPrimaryKeySelective(record);
		long id = record.getId();
		if (id > 0) {
			List<PromotionSeckillTimeEntity> timeEntity = record.getTimeEntity();
			if (timeEntity != null) {
				for (PromotionSeckillTimeEntity t : timeEntity) {
					t.setPid(id);
					promotionSeckillTimeEntityMapper.updateByPrimaryKeySelective(t);
				}
			}
		}
		return true;
	}

	public List<PromotionTimer> selectBrandListOfOnepromotionForPlatform(PromotionTimer promotion) {

		return promotionEntityMapper.selectBrandListOfOnepromotionForPlatform(promotion.getPid(),
				promotion.getTime_id());
	}

	public PromotionTimer getProductOfOnepromotionForPlatform(PromotionTimer promotion) {

		 UserEntity u = UserContext.getCurrentUser();
		
		 if (u.getUser_type() == UserType.BRAND.getIndex()) {
		 BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
		 promotion.setBrand_id(r.getId());
		 }

		PromotionTimer list = promotionEntityMapper.selectProductOfOnepromotionForPlatform(promotion.getPid(),
				promotion.getTime_id(), promotion.getBrand_id(), promotion.getStateList());
		if(list == null) return new PromotionTimer();
			List<BrandProduct> brandProduct = list.getBrandProduct();
			for (BrandProduct pt : brandProduct) {
				
				pt.setProduct_state0(new ArrayList<Product>());
				pt.setProduct_state1(new ArrayList<Product>());
				pt.setProduct_state2(new ArrayList<Product>());
				pt.setProduct_state3(new ArrayList<Product>());
				pt.setProduct_state4(new ArrayList<Product>());
				pt.setProduct_state5(new ArrayList<Product>());
				
				List<Product> product_list = pt.getProduct();
				for (Product p : product_list) {
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

	public List<Map<String, String>> getbrandpromotionListByPage1(Page<Map<String, String>> page) {

		Map<String, String> map = (HashMap<String, String>) page.getParam();

		Long brand_id = null;

		UserEntity u = UserContext.getCurrentUser();

		if (u.getUser_type() == UserType.PARTNER.getIndex() || u.getUser_type() == UserType.ENTERPRISE.getIndex()
				|| u.getUser_type() == UserType.PLATFORM.getIndex()) {

			return null;
		}
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = r.getId();
		} else if (u.getUser_type() == UserType.SHOP.getIndex()) {
			ShopEntity s = (ShopEntity) UserContext.getCurrentUserInfo();
			brand_id = s.getBrand_id();
		}
		
		if (brand_id != null) {
			map.put("brand_id", brand_id.toString());
		}
		
		
		List<Map<String, String>> list_p = promotionEntityMapper.getbrandpromotionList1ByPage(page);
		
		for (int i = 0; i < list_p.size(); i++) {
			Map<String, String> map1 = new HashMap<>();
			if (list_p.get(i) == null)
				continue;
			// Map map2 = (HashMap)list_p.get(i);
			if (list_p.get(i).get("brand_ids") != null) {
				List<String> list = Arrays.asList(((String) list_p.get(i).get("brand_ids")).split(","));
				for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
					String s = iter.next();
					if (StringUtils.isNotEmpty(s)) {
						map1.put(s, "no_use");
					}
				}
				list_p.get(i).put("brand_id_count", map1.size() + "");
			}
		}
		return list_p;
	}

	public List<PromotionEntity> getbrandpromotionListByPage2(Page<PromotionEntity> page) {

		Map<String, String> map = (HashMap<String, String>) page.getParam();

		UserEntity u = UserContext.getCurrentUser();

		if (u.getUser_type() == UserType.PARTNER.getIndex() || u.getUser_type() == UserType.ENTERPRISE.getIndex()
				|| u.getUser_type() == UserType.PLATFORM.getIndex() || u.getUser_type() == UserType.SHOP.getIndex()) {

			return null;
		}

		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand_id", r.getId() + "");
			// r.getPartnerEntity().getId();
			//
			Long class_id = r.getBuildEnterpriseEntity().getClass_id();// classID
			map.put("class_id", class_id + "");

		}
		return promotionEntityMapper.getbrandpromotionList2ByPage(page);
	}

	public List<Map<String, Object>> getbrandpromotionproductListByPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = (HashMap<String, Object>) page.getParam();

		UserEntity u = UserContext.getCurrentUser();

		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand_id", r.getId() + "");

			// Long class_id =
			// r.getEnterpriseEntity().getBuildClassEntity().getId();// classID
			// map.put("class_id", class_id+"");

		}
		if (map.get("class_name") != null && map.get("class_name").toString().length() == 1) {
			String class_name = map.get("class_name").toString().toUpperCase();
			if (class_name.charAt(0) >= 'A' && class_name.charAt(0) <= 'Z') {
				map.put("class_name", class_name);
			}
		}
		if (map.get("series_name") != null && map.get("series_name").toString().length() == 1) {
			String class_name = map.get("series_name").toString().toUpperCase();
			if (class_name.charAt(0) >= 'A' && class_name.charAt(0) <= 'Z') {
				map.put("series_name", class_name);
			}
		}

		List<Map<String, Object>> result_list = new ArrayList<Map<String, Object>>();

//		List<Long> listUnioProduct = promotionEntityMapper
//				.selectUnioPromotionProductList(Long.parseLong(map.get("pid")));

		PromotionEntity entity = promotionEntityMapper.selectByPrimaryKey(Long.valueOf(map.get("pid").toString()));
		map.put("start_time", DateUtils.formatDate_(entity.getStart_time()));
		map.put("end_time", DateUtils.formatDate_(entity.getEnd_time()));

		List<String> unionProduct = unionProductMapper.selectedExistUnionPromotionBrandProduct(map);
		List<String> existManProduct = promotionSeckillProductEntityMapper.selectedExistManPromotionBrandProduct(map);

		List<String> existProduct = new ArrayList<String>();
		existProduct.add("0");
		
		if (unionProduct != null && unionProduct.size() > 0) {
			existProduct.addAll(unionProduct);
		}
		if (existManProduct != null && existManProduct.size() > 0) {
			existProduct.addAll(existManProduct);
		}

		map.put("existProductList", existProduct);

		List<Map<String, Object>> list = promotionEntityMapper.selectpromotionProductForBrandByPage(page);
		// int f = 0;
		// if (listUnioProduct != null && listUnioProduct.size() > 0) {
		// Iterator<Map<String, Object>> iter = list.iterator();
		// for (; iter.hasNext();) {
		// Map<String, Object> tempMap = iter.next();
		// if (listUnioProduct.contains((long) tempMap.get("product_id"))) {
		// System.out.println(tempMap.get("product_id"));
		// f++;
		// iter.remove();
		// }else{
		// result_list.add(tempMap);
		// }
		// }
		// }else{
		// result_list.addAll(list);
		// }
		// page.setTotal(page.getTotal() - f);

		return list;
	}

	public List<Map<String, String>> selectProductOfOnepromotionForBrandByPage(Page<Map<String, String>> page) {
		Map<String, String> map = (HashMap<String, String>) page.getParam();
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand_id", r.getId() + "");
		}
		return promotionEntityMapper.selectProductOfOnepromotionForBrandByPage(page);

	}

	public Map<String, Integer> getProductInfoOfOnepromotionForBrand(Long pid) {
		Map<String, Integer> map = new HashMap<>();
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();

			List<ProductState> list = promotionSeckillProductEntityMapper.selectStateCountByPIdBrandIdForBrand(pid,
					r.getId());
			map.put("uncheck", 0);
			map.put("pass", 0);
			map.put("unpass", 0);

			if (list != null) {
				for (ProductState p : list) {
					switch (p.getState()) {
					case 0:
						map.put("uncheck", p.getCount());
						break;
					case 1:
						map.put("pass", p.getCount());
						break;
					case 2:
						map.put("unpass", p.getCount());
						break;
					}
				}
			}
		}
		return map;
	}

	public ResponseMessage<String> addmodifybrandpromotionproductList(PromotionSeckillProductEntity param) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		PromotionEntity p = promotionEntityMapper.selectByPrimaryKey(param.getPromotion_id());

		PromotionSeckillProductEntity db_p = promotionSeckillProductEntityMapper
				.selectByPidTimeIdProductId(param.getPromotion_id(), param.getTime_id(), param.getProduct_id());
		if (db_p != null) {
			db_p.setTitle(param.getTitle());
			db_p.setPrice(param.getPrice());
			db_p.setPerson_product_num(param.getPerson_product_num());
			db_p.setLimit_num(param.getLimit_num());
			db_p.setState(0);// 修改后状态改成未审核
			db_p.setUpdate_time(new Date());
			promotionSeckillProductEntityMapper.updateByPidTimeidProductIdSelective(db_p);
		} else {
			UserEntity u = UserContext.getCurrentUser();
			if (u.getUser_type() == UserType.BRAND.getIndex()) {
				BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();

				int count = promotionSeckillProductEntityMapper.selectSumproductByPidTimeId(param.getPromotion_id(),
						param.getTime_id(), r.getId());

				if ((count + 1) > p.getPromotion_product_num().intValue()) {
					message.setCode(SysConstant.FAILURE_CODE);
					message.setMessage("添加的产品超过限制数");
					return message;
				}

				param.setBrand_id(r.getId());
				param.setUpdate_time(new Date());
				promotionSeckillProductEntityMapper.insertSelective(param);

				// String brand_id = p.getBrand_id();
				// if (brand_id != null) {
				// if (!Arrays.asList(brand_id.split(",")).contains(r.getId() +
				// "")) {
				// brand_id = brand_id + "," + r.getId();
				// }
				// } else {
				// brand_id = r.getId() + "";
				// }
				// p.setBrand_id(brand_id);
				// promotionEntityMapper.updateByPrimaryKeySelective(p);
			}

		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}

	public ResponseMessage<String> removebrandpromotionproduct(List<PromotionSeckillProductEntity> param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		for(PromotionSeckillProductEntity p : param){
			promotionSeckillProductEntityMapper.deleteByPidTimeidProductId(p);// 直接删掉，防产品重复
		}

		// UserEntity u = UserContext.getCurrentUser();
		// if (u.getUser_type() == UserType.BRAND.getIndex()) {
		// BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
		//
		// int count =
		// promotionSeckillProductEntityMapper.selectbrandCountByPIdBrandIdForBrand(param.getPromotion_id(),
		// r.getId());
		//
		// if(count == 0){
		// PromotionEntity p =
		// promotionEntityMapper.selectByPrimaryKey(param.getPromotion_id());
		// String brand_id = p.getBrand_id();
		// if(brand_id != null){
		// String[] str = brand_id.split(",");
		// List<String> list = Arrays.asList(str);
		// list = new ArrayList<>(list);
		// for(Iterator<String> iter = list.iterator();iter.hasNext();){
		// String s = iter.next();
		// if(s.equals(r.getId().toString())){
		// iter.remove();
		// }
		// }
		// StringBuilder result = new StringBuilder();
		// for(String s : list){
		// result.append(s);
		// }
		// p.setBrand_id(result.toString());
		// promotionEntityMapper.updateByPrimaryKeySelective(p);
		// }
		// }
		// }
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}

	public boolean modifyonepromotionproduct(List<PromotionSeckillProductEntity> p) {
		if (p != null) {
			for (PromotionSeckillProductEntity product : p) {
//				if (product.getLimit_num() == null || product.getLimit_num() < 0 || product.getPrice() == null
//						|| product.getPrice() < 0 || product.getPerson_product_num() == null
//						|| product.getPerson_product_num() < 0) {
//					continue;
//				}
				product.setUpdate_time(new Date());
				promotionSeckillProductEntityMapper.updateByPidTimeidProductIdSelective(product);
			}
		}
		return true;

	}

	@Override
	public Page<Map<String, Object>> queryPromotionLink(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		UserEntity userEntity = UserContext.getCurrentUser();

		if (userEntity.getUser_type().intValue() == UserType.PARTNER.getIndex().intValue()) {

			PartnerEntity partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			map.put("partner_id", partnerEntity.getId());

		}
		
		if (userEntity.getUser_type().intValue() == UserType.BRAND.getIndex().intValue()) {

			BrandEntity partnerEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			map.put("brand_id", partnerEntity.getId());

		}

		page.setData(promotionEntityMapper.queryPromotionLinkPage(page));
		return page;
	}

	public boolean registerPromotionforbrand(PromotionSeckillTimeEntity param) {

		PromotionSeckillTimeEntity entity = promotionSeckillTimeEntityMapper.selectByPrimaryKey(param.getId());

		Long brand_id = null;
		UserEntity userEntity = UserContext.getCurrentUser();
		if (userEntity.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity r = (BrandEntity) UserContext.getCurrentUserInfo();
			brand_id = r.getId();
		}

		if (brand_id == null) {
			return false;
		}

		if (entity != null && entity.getBrand_id() != null) {
			String[] array = entity.getBrand_id().split(",");
			// 检测已报名过
			for (int i = 0; i < array.length; i++) {
				if (StringUtils.isNotEmpty(array[i]) && array[i].equals(brand_id.toString())) {
					return false;
				}
			}
			entity.setBrand_id(entity.getBrand_id() + "," + brand_id);
		} else {
			entity.setBrand_id(brand_id.toString());
		}

		int result = promotionSeckillTimeEntityMapper.updateByPrimaryKeySelective(entity);

		return result > 0;
	}

	public ResponseMessage<String> cancleRegisterPromotionforbrand(PromotionSeckillTimeEntity param) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		PromotionSeckillTimeEntity entity = promotionSeckillTimeEntityMapper.selectByPrimaryKey(param.getId());

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

		if (promotionSeckillProductEntityMapper.selectProductStateForBrandId(param.getId(), brand_id) > 0) {
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
			int result = promotionSeckillTimeEntityMapper.updateBrandIdByPrimaryKey(param.getId(),
					entity.getBrand_id());
			if (result > 0) {
				promotionSeckillProductEntityMapper.deleteByBrandIdForTimePart(param.getId(), brand_id);// 删除刚刚提交的产品
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			} else {
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage("修改失败");
			}
			return message;
		}

	}

}
