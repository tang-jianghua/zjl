package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillTimeEntity;
@WriterRepository
public interface PromotionSeckillTimeEntityMapper {
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
    int insert(PromotionSeckillTimeEntity record);

    int insertSelective(PromotionSeckillTimeEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionSeckillTimeEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionSeckillTimeEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionSeckillTimeEntity record);
    
    int updateBrandIdByPrimaryKey(@Param("id")Long id,@Param("brand_id")String brand_id);
    
    /*
     * 查询两秒后的秒杀活动
     */
    
    List<PromotionSeckillTimeEntity> selectAtNowForProductCore();
}