package com.mfangsoft.zhuangjialong.app.personalCenter.mapper;

import java.util.Map;
import java.util.List;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface CustomerPointEntityMapper {
	
	List<CustomerPointType> selectPointType();
	
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
    int insert(CustomerPointEntity record);

    int insertSelective(CustomerPointEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    CustomerPointEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerPointEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(CustomerPointEntity record);
    
    Integer selectTotalPointByCustomerId(Long customer_id);
    List<CustomerPointEntity> selectPointsByCustomerIdForPage(Page<CustomerPointEntity> page);
    
    Integer selectSumPoint(Long customer_id);
    
    List<CustomerPointEntity> querypointlist(Long customer_id);
    
    
}