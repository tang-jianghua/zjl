package com.mfangsoft.zhuangjialong.app.promotion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionMessage;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionNoteEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface PromotionNoteEntityMapper {
	
	
	PromotionNoteEntity selectByCustomerId(@Param("pid") Long pid,@Param("time_id") Long time_id,@Param("customer_id") Long customer_id ,@Param("product_id") Long product_id);
	
	List<PromotionMessage> selectNoteForMessage();
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
    int insert(PromotionNoteEntity record);

    int insertSelective(PromotionNoteEntity record);

    int updateByCustomerId(PromotionNoteEntity record);
    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionNoteEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionNoteEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionNoteEntity record);
}