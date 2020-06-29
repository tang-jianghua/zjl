package com.mfangsoft.zhuangjialong.app.seller.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructServiceType;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerInfoEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.seller.model.SellerModel;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;


@WriterRepository
public interface SellerEntityMapper {
	
	SellerEntity selectPrimaryByAccount(@Param("account")String account);
	
	Long selectPartIdBysellerId(@Param("seller_id") Long seller_id);
	
	Guild selectByAccount(String account);
	
	int updateViewTuiguangTime(@Param("id") Long id,@Param("view_tuiguang_time") Date view_tuiguang_time);
	
	int updateViewOrderTime(@Param("id") Long id,@Param("view_orders_time") Date view_orders_time);
	
	int updateViewShigongTime(@Param("id") Long id,@Param("view_shigong_time") Date view_shigong_time);
	
	Double selectRateByCustomerId(@Param("customer_id") Long customer_id);
	
	List<SellerEntity> selectGuildSellerListPage(Page<SellerEntity> param);
	
	Long queryConstructInfoId(@Param("id") Long id);
	
	Integer queryConstructBreakOffState(@Param("id") Long id);
	
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Long id);

    
    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(SellerEntity record);

    int insertSelective(SellerEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    SellerEntity selectByPrimaryKey(Long id);

    Map<String,Object> selectByPrimaryKeyForBack(Long id);
    
    int updateByPrimaryKeySelective(SellerEntity record);
    
    int  updateTuiguangTime(@Param("id") Long id, @Param("date") Date date);
    
    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(SellerEntity record);
    
    /**
     * 通过区域，姓名，服务类型获取施工列表
     *
     * @MLTH_generated
     */
    List<ConstructModel> selectConstructByNameAndServiceTypeForPage(Page<ConstructModel> param);
    
    /**
     * 通过id查找施工人员详细信息
     *
     * @MLTH_generated
     */
    ConstructModel selectConstructDetailsById(Long id);
    
    
    List<Map<String,Object>> getTuiguangSellerForPage(Page<Map<String,Object>> page);
    
    List<Map<String,Object>> getSeller23ForPage(Page<Map<String,Object>> page);
    
    ConstructModel queryworkerdetailbyid(@Param("id") Long id);
    
    ConstructModel queryworkerdetailbackbyid(@Param("id") Long id);
    
    int updateConstructInfoIdBySellerId(@Param("seller_id") Long seller_id, @Param("construct_info_id") Long construct_info_id);
    
    List<ConstructServiceType> queryservicetype();
    
    int boundzhifubao(@Param("seller_id")Long seller_id,@Param("ali_account") String ali_account);
    
    Double guilderCanDrawedMoneySum(@Param("seller_id") Long seller_id);
    
    Double guilderDrawedMoney(@Param("seller_id") Long seller_id);
    
    int updateSellerState(@Param("state") Integer state, @Param("seller_id") Long seller_id);
    
    int insertSellerInfo(SellerInfoEntity sellerInfoEntity);
    
    int selectGuildSellerTodayCount(@Param("id") Long id, @Param("create_time")Date create_time);
    
    int selectGuildSellerSumCount(@Param("id") Long id);
    
    int selectGuildSellerUnreadCount(@Param("id") Long id);
    
    /*
     * 根据卖家id获取合伙人
     */
    Long selectPartnerBySellerId(Long id);
    
    /*
     * 查询店铺导购
     */
    List<SellerModel> selectShopGuideresForPage(Page<SellerModel> page);
    
    /*
     * 通过手机号或者姓名查询
     */
    SellerEntity selectByAccountOrName(SellerModel sellerModel);
    
    /*
     * 通过手机号或者姓名查询
     */
    SellerEntity selectByAccountOrNameWithoutId(SellerModel sellerModel);
    
    /*
     * 查询单个施工导购
     */
    Map<String, Object> selectShopGuider(Long id);
    
    /*
     * 根据店铺id查询通过认证的店铺导购
     */
    List<Map<String, Object>> selectPassShopGuiderByShopId(Long shop_id);
}