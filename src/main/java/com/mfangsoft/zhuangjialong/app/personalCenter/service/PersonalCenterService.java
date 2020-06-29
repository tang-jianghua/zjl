package com.mfangsoft.zhuangjialong.app.personalCenter.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CollectBrandModel;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.HistoryEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.PartnerSendMessageModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

/**
* @description：个人中心接口
* @author：Jianghua.Tang 
* @date：2016年6月12日
* 
*/
@Service
public interface PersonalCenterService {
      
	  /**
     * 添加足迹
     *
     * @MLTH_generated
     */
	public boolean addHistory(HistoryEntity historyEntity);
	
	 /**
     * 查询关注品牌
     *
     * @MLTH_generated
     */
	 List<CollectBrandModel> selectBrandByCustomerId(Map<String, Object> param);
	 
	 /**
	  * 查询消费者足迹
	  *
	  * @MLTH_generated
	  */
	 List<Map<String, Object>>  getHistoryByCustomerId(HistoryEntity param);
	 /**
	     * 查询消费者足迹
	     *
	     * @MLTH_generated
	     */
	 List<ProductListModel>  getSqlHistoryByCustomerId(HistoryEntity param);
		 
	 /**
	     * 删除消费者所有足迹
	     *
	     * @MLTH_generated
	     */
		 void updateAllHistory(HistoryEntity param);
		 
		 /**
		  * 查询收藏产品
		  *
		  * @MLTH_generated
		  */
		 Page<Map<String, Object>> getCollectionProduct(Page<Map<String, Object>> param);
		 /**
		     * 查询收藏产品
		     *
		     * @MLTH_generated
		     */
		 Page<ProductListModel> getSqlCollectionProduct(Page<ProductListModel> param);
		 
		    /**
		     * 获取消费者个人现金券
		     *
		     * @MLTH_generated
		     */
		    List<UnionPromotion> selectCashByCustomerId(Long customer_id);
		 
		    /**
		     * 获取所有空间
		     *
		     * @MLTH_generated
		     */
		    Map<String, Object> selectAllSpaces();
		 
		    /**
		     * 获取所有风格
		     *
		     * @MLTH_generated
		     */
		    List<ClassAttrValueEntity> selectStyles();
		    
		    /**
		     * 获取所有品类
		     *
		     * @MLTH_generated
		     */
		    List<BuildClassEntity> selectAllClasses();
		 
		    List<CustomerCouponsModel> selectRedBagByCustomerId(CustomerCouponsModel c);
		    
		 List<MessageEntity> selectByCustomer_Id(Long customer_id);
	 
		 int updateByCustomerIdSelective(MessageEntity record);
		 
		 int deleteMessageByCustomerId(Long customer_id);
		 
		 int selectUnReadByCustomerId(Long customer_id);
		 
		 boolean updateCollections(Map<String, Object> param);
		 
		 void updatemessagestate(Long customer_id,Long[] id_array);
		 
		 void updatereceivemessage(CustomerEntity record);
		 
		 CustomerEntity queryreceivemessage(Long customer_id);
		 
		 boolean sendpartnermessage(PartnerSendMessageModel pModel);
		 
		 ResponseMessage<List<CustomerCouponsModel>> queryCoupons(CustomerCouponsModel c);
		 
		 void get2DMarkToStream(String url, HttpServletResponse response);
}
