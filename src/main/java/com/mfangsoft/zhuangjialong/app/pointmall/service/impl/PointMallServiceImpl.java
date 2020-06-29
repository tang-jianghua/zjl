package com.mfangsoft.zhuangjialong.app.pointmall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.pointmall.model.ConvertProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.FlowPackageModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.PointMallModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.PointProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.service.PointMallService;
import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowModel;
import com.mfangsoft.zhuangjialong.app.sendflow.service.SendFlowService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.DistanceUtil;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.PointConvertEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.PointMallEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointMallEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月22日
* 
*/
@Service(value="appPointMall")
public class PointMallServiceImpl implements PointMallService{

	 @Autowired
	 PointMallEntityMapper pointMallEntityMapper;
	 @Autowired
	 CustomerPointEntityMapper customerPointEntityMapper;
	 @Autowired
	 CustomerEntityMapper customerEntityMapper;
	 @Autowired
	 PointConvertEntityMapper pointConvertEntityMapper;
	 @Autowired
	 SendFlowService sendFlowServiceImpl;
	 
	 public final static Integer FLOW_TYPE=1;//流量
	 public final static Integer SHOP_TYPE=2;//购物卡
	 public final static Integer COMMODITY_TYPE=3;//实物商品
	 public final static Integer GIFT_TYPE=4;//实物礼品
	 public final static Integer CONVERT_UNUSED=1;//兑换未使用
	 public final static Integer CONVERT_USED=2;//兑换已使用
	 public final static Integer CONVERT_USING=3;//充值中
	 public final static Integer CONVERT_POINT_TYPE = 8;//兑换积分类型
	@Override
	public PointMallModel selectPointMall(Page<PointProductModel> pages) {
		PointMallModel pointMallModel = new PointMallModel();
		Map<String, Integer> map = (Map<String, Integer>)pages.getParam();
		pointMallModel.setPoint(customerPointEntityMapper.selectTotalPointByCustomerId(Long.valueOf(map.get("customer_id"))));
		pointMallModel.setBg_img(SysConstant.image_bath_path+RedisUtils.getValue("pointmallimg"));//后台存到redis中的地址
		//	pointMallModel.setBg_img("http://139.196.154.83:8081/file/"+RedisUtils.getValue("pointmallimg"));//后台存到redis中的地址
		List<PointProductModel> products = pointMallEntityMapper.queryPointProductsForPage(pages);
		pages.setData(products);
		pages.setParam(null);
		pointMallModel.setProducts(pages);
		return pointMallModel;
	}

	
	@Override
	public PointMallModel selectPoint(Page<CustomerPointEntity> pages) {
		PointMallModel pointMallModel = new PointMallModel();
		Map<String, Integer> map = (Map<String, Integer>)pages.getParam();
		pointMallModel.setPoint(customerPointEntityMapper.selectTotalPointByCustomerId(Long.valueOf(map.get("customer_id"))));
		List<CustomerPointEntity> points = customerPointEntityMapper.selectPointsByCustomerIdForPage(pages);
		pages.setData(points);
		pages.setParam(null);
		pointMallModel.setPoints(pages);
		return pointMallModel;
	}


	@Override
	public synchronized String addConvertProduct(ConvertProductModel convertProductModel) {
		BasePointMallEntity pointMallEntity = pointMallEntityMapper.selectByPrimaryKey(convertProductModel.getProduct_id());
		
		Integer totalPoint = customerPointEntityMapper.selectTotalPointByCustomerId(convertProductModel.getCustomer_id());
		Integer convertedNo = pointConvertEntityMapper.selectPointProductConvertedNo(convertProductModel.getProduct_id());
		Date date = new Date();
		if(pointMallEntity.getPoint()<=totalPoint && pointMallEntity.getProduct_num() > convertedNo){
			convertProductModel.setConvert_type(pointMallEntity.getConvert_type());
			convertProductModel.setCrater_time(date);
			convertProductModel.setConvert_state(CONVERT_UNUSED);
			CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(convertProductModel.getCustomer_id());
			StringBuffer convert_code = new StringBuffer();
			convert_code.append(customerEntity.getAccount().substring(0, 3));
			convert_code.append(java.util.UUID.randomUUID().toString().toUpperCase().substring(0, 8));
			convert_code.append(customerEntity.getAccount().substring(7, 11));
			convertProductModel.setConvert_code(convert_code.toString());
			convertProductModel.setConvert_point(pointMallEntity.getPoint());
			
			CustomerPointEntity customerPointEntity = new CustomerPointEntity();
			customerPointEntity.setCustomer_id(convertProductModel.getCustomer_id());
			customerPointEntity.setName(pointMallEntity.getProduct_title());
			customerPointEntity.setPoint(-convertProductModel.getConvert_point());
			customerPointEntity.setType(CONVERT_POINT_TYPE);
			customerPointEntity.setTime(date);
			int insertSelective = customerPointEntityMapper.insertSelective(customerPointEntity);
			int addstate = pointConvertEntityMapper.insertSelective(convertProductModel);
			if(addstate>0 && insertSelective>0){
				return SysConstant.SUCCESS_CODE;
			}
			return SysConstant.FAILURE_CODE;
		}
		return SysConstant.POINT_NOT_ENOUGH_CODE;
	}


	@Override
	public synchronized Map<String, Object> addPointConvert(ConvertProductModel convertProductModel) {
		BasePointMallEntity pointMallEntity = pointMallEntityMapper.selectByPrimaryKey(convertProductModel.getProduct_id());
		convertProductModel.setConvert_type(pointMallEntity.getConvert_type());
		convertProductModel.setCrater_time(new Date());
		convertProductModel.setConvert_state(CONVERT_UNUSED);
		convertProductModel.setConvert_point(pointMallEntity.getPoint());
		int num=0;
		Map<String, Object> map=new HashMap<>();
		List<String> list = new ArrayList<>();
		List<Long> resultList = new ArrayList<>();
		for (int i = 0; i < convertProductModel.getMobiles().size(); i++) {
			convertProductModel.setId(null);
			CustomerEntity customer = customerEntityMapper.selectByAccount(convertProductModel.getMobiles().get(i));
	   if(customer!=null){
			convertProductModel.setCustomer_id(customer.getId());
	     }
			StringBuffer convert_code = new StringBuffer();
				convert_code.append(convertProductModel.getMobiles().get(i).substring(0, 3));
				convert_code.append(java.util.UUID.randomUUID().toString().toUpperCase().substring(0, 8));
				convert_code.append(convertProductModel.getMobiles().get(i).substring(7, 11));
				convertProductModel.setConvert_code(convert_code.toString());
				int addstate = pointConvertEntityMapper.insertSelective(convertProductModel);
				if(addstate>0){
					num+=1;
					resultList.add(convertProductModel.getId());
				}else {
					list.add(convertProductModel.getMobiles().get(i));
				}
		}
		map.put("执行个数", convertProductModel.getMobiles().size()+"个");
		map.put("执行id", resultList);
		map.put("成功个数", num+"个");
		map.put("失败账号", list);
		return map;
	}


	@Override
	public Page<ConvertProductModel> selectConvertRecordForPage(Page<ConvertProductModel> page) {
		List<ConvertProductModel> records = pointConvertEntityMapper.selectConvertRecordForPage(page);
		page.setData(records);
		page.setParam(null);
		return page;
	}


	@Override
	public Integer updateConvertFlow(ConvertProductModel convertProductModel) {
		List<ConvertProductModel> flowPackage = pointConvertEntityMapper.selectFlowPackage(convertProductModel.getConvert_code());
		if(flowPackage.size() ==0||flowPackage.get(0).getValid_state() ==false || !flowPackage.get(0).getCustomer_id().equals(convertProductModel.getCustomer_id()) ){
			return sendFlowServiceImpl.FAILURE;
		}
		convertProductModel.setId(flowPackage.get(0).getId());
		StringBuffer ctcc= new StringBuffer();
		StringBuffer cucc= new StringBuffer();
		StringBuffer cmcc= new StringBuffer();
		for (ConvertProductModel productModel : flowPackage) {
			String[] split = productModel.getFlow_code().split(",");
			for (int j = 0; j < split.length; j++) {
				FlowPackageModel flowPackageModel = pointConvertEntityMapper.selectFlowDetailsById(Long.valueOf(split[j]));
			switch (flowPackageModel.getOperator_code()){
			case SysConstant.CMCC:
				cmcc.append(flowPackageModel.getPackage_num()+",");
              break;
			case SysConstant.CTCC:
				ctcc.append(flowPackageModel.getPackage_num()+",");
               break;
			case SysConstant.CUCC:
				cucc.append(flowPackageModel.getPackage_num()+",");
               break;				
			}
			}
		}
		String ct = null;
		String cm = null;
		String cu = null;
		if(!ctcc.toString().isEmpty()){
			ct=ctcc.toString().substring(0, ctcc.toString().length()-1);
		}
		if(!cmcc.toString().isEmpty()){
			cm=cmcc.toString().substring(0, cmcc.toString().length()-1);
		}
		if(!cucc.toString().isEmpty()){
			cu=cucc.toString().substring(0, cucc.toString().length()-1);
		}
		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(convertProductModel.getCustomer_id());
		 Integer sendFlow = sendFlowServiceImpl.InsertSendFlow(customerEntity.getAccount(), convertProductModel.getConvert_code(), ct, cu, cm);
		 if(sendFlow != sendFlowServiceImpl.SUCCESS){
			 return sendFlow;
		 }
		 convertProductModel.setConvert_state(CONVERT_USING);
		 convertProductModel.setConvert_time(new Date());
		 int updateByPrimaryKeySelective = pointConvertEntityMapper.updateByPrimaryKeySelective(convertProductModel);
		 if(updateByPrimaryKeySelective==0){
			 return sendFlowServiceImpl.FAILURE;
		 }
		 return sendFlowServiceImpl.SUCCESS;
	}

	
	@Override
	public Map<String, Object> updateConvertFlows(ConvertProductModel convertProduct) {
		Map<String, Object> map=new HashMap<>();
		List<Long> failIds = new ArrayList<>();
		int num = 0;
		List<ConvertProductModel> products = pointConvertEntityMapper.selectAllByPrimaryKey(convertProduct);
		for (ConvertProductModel convertProductModel : products) {
			List<ConvertProductModel> flowPackage = pointConvertEntityMapper.selectFlowPackage(convertProductModel.getConvert_code());
			if(flowPackage.size() ==0||flowPackage.get(0).getValid_state() ==false || !flowPackage.get(0).getCustomer_id().equals(convertProductModel.getCustomer_id()) ){
				failIds.add(convertProductModel.getId());
			}
			convertProductModel.setId(flowPackage.get(0).getId());
			StringBuffer ctcc= new StringBuffer();
			StringBuffer cucc= new StringBuffer();
			StringBuffer cmcc= new StringBuffer();
			for (ConvertProductModel productModel : flowPackage) {
				String[] split = productModel.getFlow_code().split(",");
				for (int j = 0; j < split.length; j++) {
					FlowPackageModel flowPackageModel = pointConvertEntityMapper.selectFlowDetailsById(Long.valueOf(split[j]));
				switch (flowPackageModel.getOperator_code()){
				case SysConstant.CMCC:
					cmcc.append(flowPackageModel.getPackage_num()+",");
	              break;
				case SysConstant.CTCC:
					ctcc.append(flowPackageModel.getPackage_num()+",");
	               break;
				case SysConstant.CUCC:
					cucc.append(flowPackageModel.getPackage_num()+",");
	               break;				
				}
				}
			}
			String ct = null;
			String cm = null;
			String cu = null;
			if(!ctcc.toString().isEmpty()){
				ct=ctcc.toString().substring(0, ctcc.toString().length()-1);
			}
			if(!cmcc.toString().isEmpty()){
				cm=cmcc.toString().substring(0, cmcc.toString().length()-1);
			}
			if(!cucc.toString().isEmpty()){
				cu=cucc.toString().substring(0, cucc.toString().length()-1);
			}
			CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(convertProductModel.getCustomer_id());
			 Integer sendFlow = sendFlowServiceImpl.InsertSendFlow(customerEntity.getAccount(), convertProductModel.getConvert_code(), ct, cu, cm);
			 if(sendFlow != sendFlowServiceImpl.SUCCESS){
				 failIds.add(convertProductModel.getId());
			 }
			 convertProductModel.setConvert_state(CONVERT_USING);
			 convertProductModel.setConvert_time(new Date());
			 int updateByPrimaryKeySelective = pointConvertEntityMapper.updateByPrimaryKeySelective(convertProductModel);
			 if(updateByPrimaryKeySelective==0){
				 failIds.add(convertProductModel.getId());
			 }else{
				 convertProduct.getIds().remove(convertProductModel.getId());
			 }
		}
		map.put("失败id", failIds);
		map.put("成功个数",  convertProduct.getIds());
		return map;
	}

	@Override
	public PointProductModel selectPointProductDetails(ConvertProductModel convertProductModel) {
		PointProductModel queryPointProductDetails = pointMallEntityMapper.queryPointProductDetails(convertProductModel.getId());
		if(!convertProductModel.getLbs().isEmpty() && queryPointProductDetails.getShops().size()>0){
			
		for (ShopEntity shop : queryPointProductDetails.getShops()) {
			shop.setDistance(DistanceUtil.getDistance(shop.getLbs(), convertProductModel.getLbs()));
			shop.setMap(null);
		}
		}
		Integer convertedNo = pointConvertEntityMapper.selectPointProductConvertedNo(convertProductModel.getId());
		queryPointProductDetails.setConvertedNo(convertedNo);
		queryPointProductDetails.setStock(queryPointProductDetails.getProduct_num()-convertedNo);
		return queryPointProductDetails;
	}


	@Override
	public boolean updateFlowUsedStateByConvertCode(Map<String, Object> map) {
		
		int i = pointConvertEntityMapper.updateConvertStateByConvertCode(map);
		if(i>0){
			return true;
		}
			return false;
	}




}
