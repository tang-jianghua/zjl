package com.mfangsoft.zhuangjialong.app.personalCenter.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.HistoryEntity;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface HistoryEntityMapper {
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
    int insert(HistoryEntity record);

    int insertSelective(HistoryEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    HistoryEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HistoryEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(HistoryEntity record);
    
    /**
     * 通过消费者id获取产品id  
     *
     * @MLTH_generated
     */
    List<Map<String, String>> selectProductIdByCustomerId(HistoryEntity entity);
    
    /**
     * 删除所有记录 
     *
     * @MLTH_generated
     */
    void updateAllHistory(HistoryEntity param);
    
    /**
     * 查看是否浏览过某产品（不分状态）
     *
     * @MLTH_generated
     */
    HistoryEntity selectByHistoryEntity(HistoryEntity param);
    
    
    /**
     * 通过消费者id获取产品  
     *
     * @MLTH_generated
     */
     List<ProductListModel> selectProductsByCustomerId(HistoryEntity historyEntity);
    
}