package com.mfangsoft.zhuangjialong.app.evaluation.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationEntityMapper;
import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationImageEntityMapper;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationEntity;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationImageEntity;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationModel;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationNumModel;
import com.mfangsoft.zhuangjialong.app.evaluation.service.EvaluationService;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderProductEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderStateRelationEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProductEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.order.util.OrderStatusEnum;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.common.message.service.impl.SendMessageServiceImpl;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.MySolrDoc;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BaseBrandProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月24日
* 
*/

@Service
public class EvaluationServiceImpl extends SuperCore implements EvaluationService{

	@Autowired
	EvaluationEntityMapper evaluationEntityMapper;
	@Autowired
	EvaluationImageEntityMapper evaluationImageEntityMapper;
	@Autowired
	BrandNewProductEntityMapper brandNewProductEntityMapper;
	@Autowired
	OrderStateRelationEntityMapper orderStateRelationEntityMapper;
	@Autowired
	OrderProductEntityMapper orderProductEntityMapper;
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;
	@Autowired
	BaseBrandProductEntityMapper baseBrandProductEntityMapper;
	@Autowired
	SendMessageServiceImpl sendMessageServiceImpl;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	
	@Override
	public boolean addEvaluation(EvaluationEntity record) {

		record.setCreate_time(new Date());
		evaluationEntityMapper.insertSelective(record);
		long evaluation_id = record.getId();
		if (evaluation_id < 0) {
			throw new RuntimeException();
		}
		if (record.getImages() != null) {
			for (String imge : record.getImages()) {
				EvaluationImageEntity evaluationImageEntity = new EvaluationImageEntity();
				evaluationImageEntity.setEvaluation_id(evaluation_id);
				evaluationImageEntity.setImgurl(imge);
				evaluationImageEntityMapper.insertSelective(evaluationImageEntity);
			}
		}
		List<EvaluationEntity> list = evaluationEntityMapper.selectListByOrderId(record.getOrder_id());

		List<OrderProductEntity> OrderProductEntityList = orderProductEntityMapper.selectListByOrderId(record.getOrder_id());
		int numO = OrderProductEntityList != null ? OrderProductEntityList.size() : 0;
		int num = list != null ? list.size() : 0;
		if (num >= numO) {
			OrderStateRelationEntity recordEntity = new OrderStateRelationEntity();
			recordEntity.setOrder_id(record.getOrder_id());
			recordEntity.setOrder_state_code(OrderStatusEnum.SUCCESS.value);
			recordEntity.setTime(new Date());

			orderStateRelationEntityMapper.insertSelective(recordEntity);
			
			boolean flag = true;
			for(int i =0; i<list.size();i++){
				EvaluationEntity e = list.get(i);
				if(e.getContent()!=null && e.getContent().length() > SysConstant.Pingjia_Char_Count){
					flag = true;
				}else{
					flag = false;
					break;
				}
			}
			Long customer_id = null;
			Long product_id = null;
			if(list != null && list.size() >0){
				customer_id = list.get(0).getCustomer_id();
				product_id = list.get(0).getProduct_id();
			}
			
			if(flag && customer_id != null && product_id != null){
				BaseBrandProductEntity baseBrandProductEntity = baseBrandProductEntityMapper.selectByPrimaryKey(product_id);
				
				
				List<CustomerPointType> list_pointType = customerPointEntityMapper.selectPointType();
				CustomerPointType result = null;
				for(CustomerPointType c : list_pointType){
					if(c.getType().equals(SysConstant.Order_Pingjia_Point_Id)){
						result = c;
					}
				}
				
				CustomerPointEntity customerPointEntity = new CustomerPointEntity();
				customerPointEntity.setCustomer_id(customer_id);
				customerPointEntity.setName(baseBrandProductEntity.getProduct_title());
				customerPointEntity.setTime(new Date());
				customerPointEntity.setType(SysConstant.Order_Pingjia_Point_Id);
				customerPointEntity.setPoint(result.getPoint());
				customerPointEntityMapper.insertSelective(customerPointEntity);
				
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setCustomer_id(customer_id);
				messageEntity.setType_id(MessageConstant.PointPingjia);
				messageEntity.setParams(result.getPoint().toString());
				messageEntity.setTime(new Date());
				messageEntityMapper.insertSelective(messageEntity);
				
				sendMessageServiceImpl.sendMessage(customer_id, result.getPoint());
			}
			
			/*boolean b = evaluationSolrServiceImpl.addEvaluation(record);
			if(b){
				System.out.println("添加评论成功");
			}else{
				System.out.println("添加评论失败");
			}
			
			return b;*/
		}
		return true;
	}

	@Override
	public EvaluationNumModel selectEvaluationNumByProductId(EvaluationEntity param) {
		EvaluationNumModel evaluationNumModel = evaluationEntityMapper.selectEvaluationNumByProductId(param.getProduct_id());
		if(evaluationNumModel !=null){
		if(evaluationNumModel.getGood_num() == null){
			evaluationNumModel.setGood_num(0L);
		}
		if(evaluationNumModel.getGeneral_num() == null){
			evaluationNumModel.setGeneral_num(0L);
		}
		if(evaluationNumModel.getBad_num()== null){
			evaluationNumModel.setBad_num(0L);
		}
		if(evaluationNumModel.getImg_num() == null){
			evaluationNumModel.setImg_num(0L);
		}
		evaluationNumModel.setTotal(evaluationNumModel.getGeneral_num()+evaluationNumModel.getGood_num()+evaluationNumModel.getBad_num());
		return evaluationNumModel;
		}else{
			EvaluationNumModel evaluationNum = new EvaluationNumModel();
			evaluationNum.setTotal(0L);
			evaluationNum.setGood_num(0L);
			evaluationNum.setGeneral_num(0L);
			evaluationNum.setBad_num(0L);
			evaluationNum.setImg_num(0L);
			return evaluationNum;
		}
		
	}


	@Override
	public OrderProduct getonproductforevaluation(EvaluationEntity param) {
		return brandNewProductEntityMapper.selectNameImgByPrimaryKey(param.getProduct_id());
	}

	@Override
	public Page<EvaluationModel> getSqlEvaluationByProductIdForPage(Page<EvaluationModel> page) {
		List<EvaluationModel> list = evaluationEntityMapper.selectEvaluationByProductIdForPage(page);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setEva_images(evaluationImageEntityMapper.selectImagesByEvaluationId(list.get(i).getId()));
		}
		page.setArrModel(null);
		page.setParam(null);
		page.setData(list);
		return page;
	}


}
