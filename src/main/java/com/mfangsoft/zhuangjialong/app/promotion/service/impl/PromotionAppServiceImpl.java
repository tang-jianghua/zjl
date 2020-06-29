package com.mfangsoft.zhuangjialong.app.promotion.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.mapper.PromotionNoteEntityMapper;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionDetailParam;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionNoteEntity;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.app.promotion.service.PromotionAppService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionStepProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.service.impl.PromotionZhekouServiceImpl;
import com.mfangsoft.zhuangjialong.system.mapper.sysConstantEntityMapper;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;

@Service
public class PromotionAppServiceImpl implements PromotionAppService {

	@Autowired
	PromotionEntityMapper promotionEntityMapper;
	@Autowired
	PromotionNoteEntityMapper promotionNoteEntityMapper;
	@Autowired
	PromotionSeckillProductEntityMapper promotionSeckillProductEntityMapper;
	@Autowired
	sysConstantEntityMapper sysConstantEntityMapper;
	@Autowired
	PromotionStepProductEntityMapper promotionStepProductEntityMapper;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	@Autowired
	PromotionZhekouServiceImpl promotionZhekouServiceImpl;
	@Autowired
	private BrandNewProductEntityMapper brandProductEntityMapper;

	public PromotionTimer queryMainBrandPromotion(PromotionTimer param) {

		promotionSeckillProductEntityMapper.updateSellNum_1();// 更新虚拟销量

		List<Product> list_result = new ArrayList<Product>();

		PromotionTimer pt = getMainPromotionAndProducts(param);

		if (pt != null && pt.getProduct() != null) {
			List<Product> pList = pt.getProduct();
			List<Product> list_temp = new ArrayList<Product>();

			List<sysConstantEntity> list_sys = sysConstantEntityMapper
					.getSysConstantByType("skil_promotion_product_sort");
			Map<String, String> map = new HashMap<String, String>();

			if (list_sys != null) {
				for (sysConstantEntity s : list_sys) {
					map.put(s.getKey(), "v");
				}
			}

			Iterator<Product> iter = pList.iterator();
			for (; iter.hasNext();) {
				Product p = iter.next();

				if (list_sys != null) {
					if (map.containsKey(p.getProduct_id().toString())) {
						list_temp.add(p);
						iter.remove();
					}
				}

				// Map<String,Object> set_product_id_map =
				// RedisUtils.getMap("skil_promotion_product_sort");
				// if(set_product_id_map != null &&
				// set_product_id_map.containsKey(p.getProduct_id().toString())){
				// list_temp.add(p);
				// iter.remove();
				// }
			}
			list_result.addAll(list_temp);
			list_result.addAll(pList);
			pt.setProduct(list_result);
		}

		return pt;

	}

	public PromotionTimer getMainPromotionAndProducts(PromotionTimer param) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");

		String region_code = param.getRegion_code();
		if (region_code != null) {
			if ("110100".equals(region_code)) {
				region_code = "110000";
			}
			if ("120100".equals(region_code)) {
				region_code = "120000";
			}
			if ("310100".equals(region_code)) {
				region_code = "310000";
			}
			if ("500100".equals(region_code)) {
				region_code = "500000";
			}
		}
		
		List<PromotionTimer> p = promotionEntityMapper.selectpromotionTimeForAppByRegionCode(region_code);

		// if (p != null && p.size() > 0 && p.get(0) != null) {
		//
		// if (p.get(0).getStart_time().getTime() > System.currentTimeMillis())
		// {// 开始时间大于现在
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(p.get(0).getStart_time());
		//
		// long time = caculate(p.get(0).getStart_time(),
		// p.get(0).getPstart_time());
		//
		// result = p.get(0);
		// result.setLongtime(time);
		// return result;
		// }
		// }

		// if (p != null && p.size() > 0 && p.get(0) != null) {
		// if (p.get(0).getAbc() != null && p.get(0).getAbc() > 0) {
		// result = p.get(0);
		// for (PromotionTimer t : p) {// 取最小整数
		// if (t.getAbc() > 0) {
		// result = t;
		// } else {
		// break;
		// }
		// }
		// } else {
		// result = p.get(0);
		// }
		//
		// }

		// if (result != null) {
		// result.setStrtime(format.format(result.getPstart_time()));
		//
		// long end_time = caculate(result.getPend_time());
		// long start_time = caculate(result.getPstart_time());
		//
		// if (result.getIscurrent()) {
		// result.setLongtime(end_time);
		// } else {
		// result.setLongtime(start_time);
		// }
		// }
		PromotionTimer result = null;

		if (p != null && p.size() > 0) {

			for (PromotionTimer t : p) {
				t.setStrtime(format.format(t.getPstart_time()));

				// 距离活动开始
				if (t.getStart_time().getTime() > System.currentTimeMillis()) {
					long time = caculate(t.getStart_time().getTime(), t.getPstart_time());
					t.setLongtime(time);
					return t;
				} else {
					// 活动中
					if (t.getIscurrent()) {
						long end_time = caculate(t.getPend_time());
						t.setLongtime(end_time);
						return t;
					} else {
						// 距离时间段开始
						long time = caculate(t.getPstart_time());

						if (time > 0) {// 下一个时间段
							t.setLongtime(time);
							result = t;
							break;
						}
					}
				}
			}
			if (result == null && p.get(0) != null) {
				if (p.get(0).getEnd_time().getTime() > (System.currentTimeMillis() + SysConstant.MILLIS_PER_DAY)) {
					result = p.get(0);
					long time = caculate((System.currentTimeMillis() + SysConstant.MILLIS_PER_DAY),
							result.getPstart_time());
					result.setLongtime(time);
				}
			}
		}
		return result;

	}

	public static void main(String[] args) {
		String timeFormatter = "HH:mm";
		SimpleDateFormat simple0 = new SimpleDateFormat(timeFormatter);

		try {
			System.out.println(simple0.parse("07:20").getTime());
			// caculate(simple0.parse("20:20"));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private Long caculate(Date date) {
		Calendar cal = Calendar.getInstance();// 使用日历类
		cal.setTime(date);
		int day = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		Calendar cal2 = Calendar.getInstance();// 使用日历类
		cal2.set(Calendar.HOUR_OF_DAY, day);
		cal2.set(Calendar.MINUTE, min);
		cal2.set(Calendar.SECOND, sec);
		return cal2.getTimeInMillis() - System.currentTimeMillis();
	}

	private Long caculate(Long promotion_date, Date date) {
		Calendar cal = Calendar.getInstance();// 使用日历类
		cal.setTime(date);
		int day = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		Calendar cal2 = Calendar.getInstance();// 使用日历类
		cal2.setTimeInMillis(promotion_date);
		cal2.set(Calendar.HOUR_OF_DAY, day);
		cal2.set(Calendar.MINUTE, min);
		cal2.set(Calendar.SECOND, sec);
		return cal2.getTimeInMillis() - System.currentTimeMillis();
	}

	public List<PromotionTimer> getpromotiontime(PromotionTimer param) {

		return getpromotiontimeFormDB(param.getPid());

	}

	public PromotionTimer getpromotiontime2(PromotionTimer param) {
		List<PromotionTimer> p = getpromotiontimeFormDB(param.getPid());
		if (p != null) {
			for (PromotionTimer result : p) {
				if (result.getTime_id().equals(param.getTime_id())) {
					return result;
				}
			}
		}
		return null;
	}

	public List<PromotionTimer> getpromotiontimeFormDB(Long pid) {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		List<PromotionTimer> p = promotionEntityMapper.selectpromotionTime(pid);
		if (p != null) {
			for (PromotionTimer result : p) {

				if (result.getStart_time().getTime() > System.currentTimeMillis()) {
					result.setStrtime(format.format(result.getPstart_time()));
					long time = caculate(result.getStart_time().getTime(), result.getPstart_time());
					result.setState(1);// 未开始
					result.setLongtime(time);

				} else {

					result.setStrtime(format.format(result.getPstart_time()));
					long end_time = caculate(result.getPend_time());
					long start_time = caculate(result.getPstart_time());

					if (end_time < 0) {
						result.setState(-1);// 已结束
						result.setLongtime(start_time);
					} else if (start_time > 0) {
						result.setState(1);// 未开始
						result.setLongtime(start_time);
					} else {
						result.setState(0);// 进行中
						result.setLongtime(end_time);
					}
				}
			}
		}
		return p;
	}

	public List<Product> queryPlatformPromotionProduct(Page<Product> page) {

		// Map<String,Object> map = (Map<String,Object>)page.getParam();
		// PromotionTimer r = null;
		//
		// if(map.get("pid") != null && map.get("time_id") != null){
		// List<PromotionTimer> p =
		// getpromotiontimeFormDB((Long.parseLong((int)(map.get("pid"))+"")));
		// if (p != null) {
		// for (PromotionTimer result : p) {
		// if(result.getTime_id().equals(map.get("time_id"))){
		// r = result;
		// }
		// }
		// }
		// }
		// List<Product> product =
		// promotionEntityMapper.selectpromotionProductForPage(page);
		// if(product != null){
		// for(Product p : product){
		// if(r != null){
		// p.setNote_state(r.getState());
		// }else{
		// p.setNote_state(1);
		// }
		// }
		// }
		// return product;
		Map<String, Object> map = (Map<String, Object>) page.getParam();
		List<Product> list = null;
		if (map.get("customer_id") != null) {
			list = promotionEntityMapper.selectpromotionProductForPage(page);
		} else {
			list = promotionEntityMapper.selectpromotionProductFor2Page(page);
		}

		List<Product> list_result = new ArrayList<Product>();
		if (map.get("product_id") != null) {
			String product_id = (String) (map.get("product_id").toString());
			Product p = null;
			for (Iterator<Product> ite = list.iterator(); ite.hasNext();) {
				Product iteNext = ite.next();
				if (iteNext != null && iteNext.getProduct_id().longValue() == Long.parseLong(product_id)) {
					p = iteNext;
					ite.remove();
				}
			}
			if (p != null) {
				list_result.add(p);
			}
			list_result.addAll(list);
		} else {
			list_result.addAll(list);
		}

		return list_result;

	}

	public Long addPromotionCustomerNote(PromotionNoteEntity param) {

		PromotionNoteEntity result = promotionNoteEntityMapper.selectByCustomerId(param.getPid(), param.getTime_id(),
				param.getCustomer_id(), param.getProduct_id());
		Long note_id = null;
		if (result == null) {
			param.setUpdate_time(new Date());
			promotionNoteEntityMapper.insertSelective(param);

			promotionSeckillProductEntityMapper.updateNoteCount(param.getPid(), param.getTime_id(),
					param.getProduct_id());

			note_id = param.getId();
		} else {
			note_id = result.getId();
		}
		return note_id;
	}

	public List<PromotionTimer> getpromotioncustomernoteproducts(Long customer_id) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		List<PromotionTimer> p = promotionEntityMapper.selectNoteProductByCustomerId(customer_id);
		if (p != null) {
			for (PromotionTimer result : p) {

				result.setStrtime(format.format(result.getPstart_time()));

			}

		}
		return p;

	}

	public Integer removepromotioncustomernote(PromotionNoteEntity param) {

		promotionNoteEntityMapper.deleteByPrimaryKey(param.getId());

		promotionSeckillProductEntityMapper.updateDecresrNoteCount(param.getPid(), param.getTime_id(),
				param.getProduct_id());

		return 1;
	}

	public ResponseMessage<String> checkpromotionconditionforcustomer(PromotionSeckillProductEntity param) {
		ResponseMessage<String> responseMessage = new ResponseMessage<String>();

		Boolean total_conditon = promotionEntityMapper.checkTotalProductSumconditionForCustomer(param.getPromotion_id(),
				param.getTime_id(), param.getProduct_id());

		Boolean person_conditon = promotionEntityMapper.checkPersonProductSumconditionForCustomer(
				param.getPromotion_id(), param.getTime_id(), param.getProduct_id());

		if (total_conditon && person_conditon) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else if (!total_conditon) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage("产品总数超过限制");
		} else if (!person_conditon) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage("个人购买总数超过限制");
		}
		return responseMessage;
	}

	public List<PromotionEntityStepParam> querypromotionforfirstpage(String region_code) {

		long time = System.currentTimeMillis();
		long t_old = time;
		
		if (region_code != null) {
			if ("110100".equals(region_code)) {
				region_code = "110000";
			}
			if ("120100".equals(region_code)) {
				region_code = "120000";
			}
			if ("310100".equals(region_code)) {
				region_code = "310000";
			}
			if ("500100".equals(region_code)) {
				region_code = "500000";
			}
		}
		
		List<PromotionEntityStepParam> list = promotionEntityMapper.querypromotionforfirstpage(region_code);

		if (list != null) {
			for (PromotionEntityStepParam entity : list) {

				List<Product> list_p = promotionStepProductEntityMapper.selectProductOfOnePromotion(entity.getId(),
						region_code);

				entity.setProductList(new ArrayList<Product>());
				int i = 0;
				Map<Integer, Integer> map = new HashMap<>();
				for (Product p : list_p) {
					if (map.get(p.getBrand_id()) == null) {

						List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(p.getProduct_id());
						p.setPromotion_types(promotionTypes);

						// 折扣工具
						PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct((long) p.getBrand_id(), p.getProduct_id());
						if (zhekou != null) {
							p.setNew_price(NumberUtil.round(zhekou.getDiscount() * p.getOld_price(), 2, BigDecimal.ROUND_HALF_DOWN));
						}

						entity.getProductList().add(p);
						map.put(p.getBrand_id(), new Integer(1));
						++i;
					} else if (map.get(p.getBrand_id()) < 2) {

						List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(p.getProduct_id());
						p.setPromotion_types(promotionTypes);

						// 折扣工具
						PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct((long) p.getBrand_id(), p.getProduct_id());
						if (zhekou != null) {
							p.setNew_price(NumberUtil.round(zhekou.getDiscount() * p.getOld_price(), 2, BigDecimal.ROUND_HALF_DOWN));
						}

						entity.getProductList().add(p);
						map.put(p.getBrand_id(), map.get(p.getBrand_id()) + 1);
						++i;
					} else {
						continue;
					}
					if (i > 10) {
						break;
					}
				}
			}
		}
		return list;
	}

	public PromotionEntityStepParam getpromotionfordetail(PromotionEntityStepParam param) {

		PromotionEntityStepParam entity = promotionEntityMapper.selectPromotionByPrimaryKey(param.getId());
		if (entity != null) {
			List<String> list_img = new ArrayList<>();
			entity.setImgs_list(list_img);

			if (StringUtils.isNotEmpty(entity.getImgurl1())) {
				list_img.add(entity.getImgurl1());
				entity.setImgurl1(null);
			}
			if (StringUtils.isNotEmpty(entity.getImgurl2())) {
				list_img.add(entity.getImgurl2());
				entity.setImgurl2(null);
			}
			if (StringUtils.isNotEmpty(entity.getImgurl3())) {
				list_img.add(entity.getImgurl3());
				entity.setImgurl3(null);
			}

			String region_code = param.getRegion_code();
			if (region_code != null) {
				if ("110100".equals(region_code)) {
					region_code = "110000";
				}
				if ("120100".equals(region_code)) {
					region_code = "120000";
				}
				if ("310100".equals(region_code)) {
					region_code = "310000";
				}
				if ("500100".equals(region_code)) {
					region_code = "500000";
				}
			}
			List<BrandProduct> list = promotionEntityMapper.selectPromotionBrandInfoById(param.getId(), region_code);
			if (list != null) {
				Map<String, List<BrandProduct>> c_b = new HashMap<String, List<BrandProduct>>();

				for (BrandProduct bp : list) {
					if (c_b.get(bp.getClass_name()) == null) {
						List<BrandProduct> t = new ArrayList<>();
						t.add(bp);
						c_b.put(bp.getClass_name(), t);
					} else {
						c_b.get(bp.getClass_name()).add(bp);
					}
				}

				List<PromotionDetailParam> p_list = new ArrayList<PromotionDetailParam>();

				for (Map.Entry<String, List<BrandProduct>> entry : c_b.entrySet()) {
					PromotionDetailParam class_brand = new PromotionDetailParam();
					class_brand.setClass_name(entry.getKey());
					class_brand.setList(entry.getValue());
					p_list.add(class_brand);

				}

				entity.setClass_brand(p_list);

			}

		}
		return entity;
	}

	public List<Product> getapppromotionproductforpage(Page<Product> page) {

		List<Product> list = promotionStepProductEntityMapper.selectAppPromotionProductforPage(page);

		if (list != null) {
			for (Product p : list) {
//				Double price = promotionZhekouServiceImpl.getZhekouPrice(p.getBrand_id().longValue(), p.getProduct_id(), p.getOld_price());
				
				PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(p.getBrand_id().longValue(), p.getProduct_id());
    			if(zhekou != null){
    				p.setNew_price(NumberUtil.round(zhekou.getDiscount() * p.getOld_price(), 2, BigDecimal.ROUND_HALF_DOWN));
    			}
			}
		}
		return list;
	}
}
