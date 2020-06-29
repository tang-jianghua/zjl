package com.mfangsoft.zhuangjialong.integration.seller.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructServiceType;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.seller.model.Order;
@Service
public interface SellerService {
	
	
	ResponseMessage<String>  addSeller(SellerEntity sellerEntity);
	
	Boolean modifySeller(SellerEntity sellerEntity);
	
	ResponseMessage<Integer> modifySellerState(SellerEntity sellerEntity);
	
	Map<String,Object> getOneSeller(SellerEntity sellerEntity);
	
	Page<Map<String,Object>> getTuiguangSellerForPage(Page<Map<String,Object>> page);
	
	Page<Map<String,Object>> getSellerForPage(Page<Map<String,Object>> page);

	Page<Map<String,Object>> selectConstructAppointmentBackForPage(Page<Map<String,Object>> param);
	
	Page<Order> selectOrderListBackForPage(Page<Order> param);
	
	ConstructAppointmentModel selectConstructerAppointmentAndTraceingBackByAppopintId(ConstructAppointmentModel param);
	
	ConstructModel queryconstructdetailbackbyid(ConstructModel param);
	
	Page<Map<String,Object>> queryCustomerBackForPage(Page<Map<String,Object>> page);
	
	Boolean saveconstructdetailback(ConstructModel param);
	
	List<ConstructServiceType> queryservicetypeback();
	
	boolean modifyVerifystate(ConstructModel param);
	
	List<ConstructStateRelation> getconstructstate();
	
	Page<Map<String,String>> getEvaluationsForPage(Page<Map<String,String>> param);
	
	
}
