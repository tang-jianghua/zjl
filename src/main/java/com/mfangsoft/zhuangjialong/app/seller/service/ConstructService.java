package com.mfangsoft.zhuangjialong.app.seller.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructServiceType;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerBalanceApplyEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerConstructInfoEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月4日
* 
*/
@Service
public interface ConstructService {
       
	public Page<ConstructModel> queryConstructs(Page<ConstructModel> param);
	    
	    public ConstructModel queryConstructDetails(ConstructModel param);
	    
	    public boolean constructverify(SellerConstructInfoEntity record);
	    
	    public Page<ConstructAppointmentModel> selectAppointmentOfConstructerForPage(Page<ConstructAppointmentModel> param);
	    
	    public boolean workerchangestate(ConstructAppointmentModel param);
	    
	    public ConstructModel queryworkerdetailbyid(ConstructModel param);
	    
	    public List<ConstructServiceType> queryservicetype();
	    
	    public SellerConstructInfoEntity selectStateBySellerId(Long seller_id);
	    
	    public boolean modifyworkingstate(Integer break_off_state, Long seller_id);
	    
	    public ResponseMessage<ConstructModel> boundzhifubao(ConstructModel param);
	    
	    public ResponseMessage<SellerBalanceApplyEntity> drawandverifymoney(SellerBalanceApplyEntity param);
	    
	    public Guild selectInvitCount(Guild g);
	    
	    public List<RegionEntity> queryserviceprovencelist();
	    
	    public List<RegionEntity> queryserviceCityByprovence(String code);
	    
	    public List<RegionEntity> queryserviceCountryByCity(String code);
	    
	    public boolean modifyConstructorInfo(ConstructModel constructModel);
	    
	    public Integer modifyConstructorbreakoffstate(ConstructModel constructModel);
}
