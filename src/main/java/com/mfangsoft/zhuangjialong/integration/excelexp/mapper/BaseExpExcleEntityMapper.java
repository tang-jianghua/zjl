package com.mfangsoft.zhuangjialong.integration.excelexp.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.excelexp.model.BaseExpExcleEntity;
@WriterRepository
public interface BaseExpExcleEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(BaseExpExcleEntity record);

    int insertSelective(BaseExpExcleEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseExpExcleEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseExpExcleEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseExpExcleEntity record);
    
    BaseExpExcleEntity  getExpExcelEntity(String module_code); 
}