package com.mfangsoft.zhuangjialong.app.personalCenter.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerCollectionEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.HistoryEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CollectBrandModel;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.HistoryEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.PartnerSendMessageModel;
import com.mfangsoft.zhuangjialong.app.personalCenter.service.PersonalCenterService;
import com.mfangsoft.zhuangjialong.app.prepay.mapper.ShopPrepayEntityMapper;
import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
import com.mfangsoft.zhuangjialong.app.product.model.ArrModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.promotion.model.Promotion_type;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.Mark2D;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.MySolrDoc;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.ClassAttrValueEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
import com.mfangsoft.zhuangjialong.system.mapper.sysConstantEntityMapper;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年6月12日
 * 
 */
@Service
public class PersonalCenterServiceImpl extends SuperCore implements PersonalCenterService {

	@Autowired
	private HistoryEntityMapper historyEntityMapper;
	@Autowired
	private CustomerCollectionEntityMapper customerCollectionEntityMapper;
	@Autowired
	private BrandNewProductEntityMapper brandProductEntityMapper;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	@Autowired
	CustomerEntityMapper customerEntityMapper;
	@Autowired
	UnionPromotionMapper unionPromotionMapper;
	@Autowired
	BuildClassEntityMapper buildClassEntityMapper;
	@Autowired
	BrandEntityMapper brandEntityMapper;
	@Autowired
	BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	ShopPrepayEntityMapper shopPrepayEntityMapper;
	@Autowired
	ClassAttrValueEntityMapper classAttrValueEntityMapper;
	@Autowired
	UserEquipmentEntityMapper userEquipmentEntityMapper;
	@Autowired
	sysConstantEntityMapper sysConstantEntityMapper;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	@Override
	public boolean addHistory(HistoryEntity param) {
		HistoryEntity historyEntity = historyEntityMapper.selectByHistoryEntity(param);
		if (historyEntity == null) {
			param.setCreate_time(new Date());
			param.setState(1);
			int i = historyEntityMapper.insertSelective(param);
			if (i == 0) {
				return false;
			}
		} else {
			historyEntity.setCreate_time(new Date());
			historyEntity.setState(1);
			int i = historyEntityMapper.updateByPrimaryKeySelective(historyEntity);
			if (i == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<CollectBrandModel> selectBrandByCustomerId(Map<String, Object> param) {
		return customerCollectionEntityMapper.selectBrandByCustomerId(param);
	}

	@Override
	public List<Map<String, Object>> getHistoryByCustomerId(HistoryEntity param) {
		
		 List<Map<String, String>> list = historyEntityMapper.selectProductIdByCustomerId(param);
		super.initProduct();
		query.setFields("product_id,image_url,product_name,min_prom_price,sale_num");
		query.setQuery("*:*");
		query.setRows(list.size());
		List<Map<String, Object>> products = new ArrayList<>();
		StringBuffer pds = new StringBuffer();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					pds.append("product_id:" + list.get(i).get("product_id") + " OR ");
				} else {
					pds.append("product_id:" + list.get(i).get("product_id"));
				}
			}

			query.setFilterQueries(pds.toString());
			// 搜索
			try {
				response = solr.query(query);
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 获取结果
			products = MySolrDoc.toJson(response.getResults());
			for (int i = 0; i < products.size(); i++) {
				String product_id = products.get(i).get("product_id").toString();
			    List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(Long.valueOf(product_id));
			    for (int j = 0; j < list.size(); j++) {
					if(product_id .equals( list.get(j).get("product_id"))){
						products.get(i).put("history_time", list.get(j).get("create_time"));
					}
				}
			    products.get(i).put("promotion_types", promotionTypes);
	            
	            if(promotionTypes == null || !promotionTypes.contains(4)){
	    			// 折扣工具
	            	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(Long.valueOf(product_id));
	            	
	    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), Long.valueOf(product_id));
	    			if(zhekou != null){
	    				String price = products.get(i).get("min_prom_price").toString();
	    				products.get(i).put("min_prom_price", NumberUtil.round2(zhekou.getDiscount() * Double.parseDouble(price)));
	    			}
	    		}
				}
			for(int i=0;i<products.size()-1;i++){//外层循环控制排序趟数
				for(int j=0;j<products.size()-1-i;j++){//内层循环控制每一趟排序多少次
					if(Long.valueOf(products.get(j).get("history_time").toString())<Long.valueOf(products.get(j+1).get("history_time").toString())){
						Map temp = new HashMap<>();
						temp.putAll(products.get(j));
						products.get(j).clear();
						products.get(j).putAll(products.get(j+1));
						products.get(j+1).clear();
						products.get(j+1).putAll(temp);
					}
				}
			}
		}
		return products;
	}

	@Override
	public void updateAllHistory(HistoryEntity param) {
		historyEntityMapper.updateAllHistory(param);
	}

	@Override
	public List<MessageEntity> selectByCustomer_Id(Long customer_id) {
		return messageEntityMapper.selectByCustomer_Id(customer_id);
	}

	@Override
	public int updateByCustomerIdSelective(MessageEntity record) {
		return messageEntityMapper.updateByCustomerIdSelective(record);
	}

	@Override
	public int deleteMessageByCustomerId(Long customer_id) {
		return messageEntityMapper.deleteByCustomerId(customer_id);
	}

	@Override
	public int selectUnReadByCustomerId(Long customer_id) {
		return messageEntityMapper.selectUnReadByCustomerId(customer_id);
	}

	@Override
	public boolean updateCollections(Map<String, Object> param) {

		int i = customerCollectionEntityMapper.updateByIds(param);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ResponseMessage<List<CustomerCouponsModel>> queryCoupons(CustomerCouponsModel param) {

		ResponseMessage<List<CustomerCouponsModel>> responseMessage = new ResponseMessage<>();

		List<CustomerCouponsModel> list = brandCouponsEntityMapper.selectCouponsByCustomerId(param);
		sysConstantEntity sysEntity = sysConstantEntityMapper.getSysConstantByKey("shuang11couponsid");

		if (list != null) {
			for (CustomerCouponsModel c : list) {
				if (sysEntity != null && sysEntity.getValue().equals(c.getCoupons_id().toString())) {
					c.setName(sysEntity.getDescription());
				} else {
					if(c.getBrand_name() != null){
						c.setName(c.getBrand_name() + c.getName());
					}else{
						c.setName(c.getName());
					}
				}
			}
		}
		responseMessage.setCode("0");
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(list);
		return responseMessage;
	}

	public List<CustomerCouponsModel> selectRedBagByCustomerId(CustomerCouponsModel param) {

		List<CustomerCouponsModel> brandCouponsEntity_r = new ArrayList<>();

		List<CustomerCouponsModel> brandCouponsEntity = brandCouponsEntityMapper.selectRedBagByCustomerId(param);
		if (brandCouponsEntity != null) {

			sysConstantEntity sysEntity = sysConstantEntityMapper.getSysConstantByKey("shuang11redbagid");
			for (CustomerCouponsModel c : brandCouponsEntity) {
				if (sysEntity != null && sysEntity.getValue().equals(c.getCoupons_id().toString())) {
					c.setName(sysEntity.getDescription());
				} else {
					if(c.getBrand_name() != null){
						c.setName(c.getBrand_name() + c.getName());
					}else{
						c.setName(c.getName());
					}
				}
			}

			for (CustomerCouponsModel e : brandCouponsEntity) {
				e.setRegbagType(0);
			}
			brandCouponsEntity_r.addAll(brandCouponsEntity);
		}

		List<ShopPrepayEntity> list = shopPrepayEntityMapper.selectByCustomerIdForPCenter(param.getCustomer_id());
		if (list != null) {
			for (ShopPrepayEntity s : list) {
				CustomerCouponsModel prepayRedbags = new CustomerCouponsModel();
				prepayRedbags.setId((long) s.getId());
				prepayRedbags.setName(s.getName());
				prepayRedbags.setValue(s.getValue().doubleValue());
				if(s.getState().intValue() == 0){
					prepayRedbags.setIsused(false);
				}else{
					prepayRedbags.setIsused(true);
				}
				
				prepayRedbags.setCustomer_id(s.getCustomer_id());
				prepayRedbags.setStart_time(s.getCreate_time());
				prepayRedbags.setRegbagType(1);

				if (s.getState().intValue() == 0) {
					prepayRedbags.setNote(2); // 2有效 3已使用
				} else {
					prepayRedbags.setNote(3);
				}
				brandCouponsEntity_r.add(prepayRedbags);
			}
		}
		return brandCouponsEntity_r;

	}
	
	@Override
	public Page<ProductListModel> getSqlCollectionProduct(Page<ProductListModel> param) {
		Map<String, String> map = (Map<String, String>) param.getParam();
		ArrModel arrModel = new ArrModel();
		String string = map.get("search_text");
		List<String> list = new ArrayList<>();
		if (string != "") {
			String[] split = string.split(" ");
			for (int i = 0; i < split.length; i++) {
				if (!"".equals(split[i])) {
					list.add(split[i]);
				}
			}
			arrModel.setProduct_name(list);
			param.setArrModel(arrModel);
		}

		List<ProductListModel> products = customerCollectionEntityMapper
				.selectCollectionProductsByCustomerIdForPage(param);
		for (int i = 0; i < products.size(); i++) {
			products.get(i).setPromotion_types(
					brandProductEntityMapper.selectPromotionTypesByProductId(products.get(i).getProduct_id()));
		}
		param.setArrModel(null);
		param.setParam(null);
		param.setData(products);
		return param;
	}
	

	@Override
	public Page<Map<String, Object>> getCollectionProduct(Page<Map<String, Object>> page) {
		Map<String, Object> map = (Map<String, Object>) page.getParam();
		if (map.get("customer_id") == null) {
			return null;
		}
		if (!"".equals(map.get("search_text"))) {
			String search_text = map.get("search_text").toString();
			StringBuffer sb = new StringBuffer();
			sb.append("AND (");
			String[] split = search_text.split(" ");
			for (int i = 0; i < split.length; i++) {
				if (!"".equals(split[i])) {
					if (i < split.length - 1) {
						sb.append("p.`product_title` LIKE '%").append(split[i] ) .append("%' OR ");
					} else {
						sb.append("p.`product_title` LIKE '%").append(split[i] ) .append("%' )");
					}
				}
			}
			map.put("sql", sb.toString());
		}
		List<Map<String,Object>> list = customerCollectionEntityMapper.selectCollectionProductByCustomerIdForPage(page);
		if(list.size()>0){
		super.initProduct();
		query.setFields("product_id,image_url,product_name,min_prom_price,sale_num");
		query.setQuery("*:*");
		List<Map<String, Object>> products = new ArrayList<>();
			StringBuffer ids = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					ids.append("product_id:" + list.get(i).get("product_id") + " OR ");
				} else {
					ids.append("product_id:" + list.get(i).get("product_id"));
				}
			}
			query.addFilterQuery(ids.toString());

		// 搜索
		try {
			response = solr.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获取结果
		products = MySolrDoc.toJson(response.getResults());
		// 添加收藏id
		for (int i = 0; i < products.size(); i++) {
			String product_id = products.get(i).get("product_id")+"";
			for (int j = 0; j < list.size(); j++) {
				String product_id2 = list.get(j).get("product_id") + "";
				if (product_id.equals(product_id2)){
					products.get(i).put("collect_id", list.get(j).get("id"));
					products.get(i).put("collect_time", list.get(j).get("create_time"));
				}
			}
		    List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(Long.valueOf(product_id));
		    products.get(i).put("promotion_types", promotionTypes);
            
            if(promotionTypes == null || !promotionTypes.contains(4)){
    			// 折扣工具
            	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(Long.valueOf(product_id));
            	
    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), Long.valueOf(product_id));
    			if(zhekou != null){
    				String price = products.get(i).get("min_prom_price").toString();
    				products.get(i).put("min_prom_price", NumberUtil.round2(zhekou.getDiscount() * Double.parseDouble(price)));
    			}
			}
		}
		page.setParam(null);
		for(int i=0;i<products.size()-1;i++){//外层循环控制排序趟数
			for(int j=0;j<products.size()-1-i;j++){//内层循环控制每一趟排序多少次
				if(Long.valueOf(products.get(j).get("collect_time").toString())<Long.valueOf(products.get(j+1).get("collect_time").toString())){
					Map temp = new HashMap<>();
					temp.putAll(products.get(j));
					products.get(j).clear();
					products.get(j).putAll(products.get(j+1));
					products.get(j+1).clear();
					products.get(j+1).putAll(temp);
				}
			}
		}
		page.setData(products);
		} else{
			page.setParam(null);
			page.setData(list);
		}
		return page;
	}


	public void updatemessagestate(Long customer_id, Long[] id_array) {
		messageEntityMapper.updatemessagestate(customer_id, id_array);
	}

	public void updatereceivemessage(CustomerEntity record) {
		customerEntityMapper.updatereceivemessage(record);
	}

	public CustomerEntity queryreceivemessage(Long customer_id) {
		return customerEntityMapper.queryreceivemessage(customer_id);
	}

	@Override
	public List<UnionPromotion> selectCashByCustomerId(Long customer_id) {
		List<UnionPromotion> cashes = unionPromotionMapper.selectCashByCustomerId(customer_id);
		for (int i = 0; i < cashes.size(); i++) {
			long s = System.currentTimeMillis();// 得到当前的毫秒
			long time = cashes.get(i).getEnd_time().getTime();
			if (cashes.get(i).getCa_state() == 1) {
				cashes.get(i).setCstate(2);// 已使用
			} else {
				if (s > time) {
					cashes.get(i).setCstate(1);// 已过期
				} else {
					long day = (time - s) / 1000 / 60 / 60 / 24;
					if (day <= 10) {
						cashes.get(i).setCstate(4);// 即将到期
					} else {
						cashes.get(i).setCstate(3);// 未使用
					}
				}
			}
			cashes.get(i).setCa_state(null);
			cashes.get(i).setState(null);
			cashes.get(i).setCash_state(null);
			cashes.get(i).setBrands(null);
			String[] brand_ids = cashes.get(i).getBrand_ids().split(",");
			StringBuffer info = new StringBuffer();
			info.append("仅限");
			for (int j = 0; j < brand_ids.length; j++) {
				info.append(brandEntityMapper.selectBrandNameById(Long.parseLong(brand_ids[j])));
				if (j != brand_ids.length - 1) {
					info.append(",");
				}
			}
			info.append("联合促销活动产品中使用");
			cashes.get(i).setInfo(info.toString());
			cashes.get(i).setBrand_ids(null);
		}
		Collections.sort(cashes, new Comparator<UnionPromotion>() {
			@Override
			public int compare(UnionPromotion o1, UnionPromotion o2) {
				if (o1.getCstate() - o2.getCstate() > 0) {
					return 1;
				} else {
					return 0;
				}
			}
		});

		return cashes;
	}

	@Override
	public Map<String, Object> selectAllSpaces() {
		// List<WallpaperAttrEntity> list =
		// wallpaperAttrEntityMapper.selectAttresByType("space");
		List<ClassAttrValueEntity> list = classAttrValueEntityMapper.getAttrValueEntityByAttrId(1L);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setImage(SysConstant.image_bath_path + "upload/image/space_" + list.get(i).getId() + ".jpg");
		}

		Map<String, Object> result = new HashMap<>();
		result.put("spaces", list);
		result.put("top_image", SysConstant.image_bath_path + "upload/image/space_top.png");
		return result;
	}

	@Override
	public List<BuildClassEntity> selectAllClasses() {
		return buildClassEntityMapper.selectAllClasses();
	}

	@Override
	public List<ClassAttrValueEntity> selectStyles() {
		return classAttrValueEntityMapper.getAttrValueEntityByAttrId(2L);
	}

	@Override
	public List<ProductListModel> getSqlHistoryByCustomerId(HistoryEntity param) {
		List<ProductListModel> list = historyEntityMapper.selectProductsByCustomerId(param);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPromotion_types(
					brandProductEntityMapper.selectPromotionTypesByProductId(list.get(i).getProduct_id()));
		}
		return list;
	}

	

	@Override
	public boolean sendpartnermessage(PartnerSendMessageModel pModel) {
		if (pModel.getAccount_array() != null) {
			List<UserEquipmentEntity> list = userEquipmentEntityMapper
					.selectAllByCustomerAccount(pModel.getAccount_array());
			for (UserEquipmentEntity e : list) {
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setCustomer_id(e.getCustomer_id());
				messageEntity.setType_id(MessageConstant.SmartEquipment);
				messageEntity.setParams(pModel.getTitle() + "," + pModel.getContent());
				messageEntity.setTime(new Date());
				messageEntityMapper.insertSelective(messageEntity);

				QuestsManagerBean.addQuest(new Quest() {

					@Override
					public boolean execute() {
						JPushUtil.sendMessage(e.getPlatform(), e.getPushstr(),
								MessageFormat.format(MessageConstant.SmartEquipmentTitle, pModel.getTitle()),
								MessageFormat.format(MessageConstant.SmartEquipmentContent, pModel.getContent()));
						return true;
					}

					@Override
					public boolean condition() {
						return true;
					}

					@Override
					public boolean delete() {
						return true;
					}
				});
			}

		}
		return true;
	}
	
	public void get2DMarkToStream(String url, HttpServletResponse response){
		
		try {
			Mark2D.get2DMarkToStream(url, response, 200, 200, "png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
