package com.mfangsoft.zhuangjialong.app.weixin.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.weixin.model.WeiXinStore;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface WeiXinStoreMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer sid);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(WeiXinStore record);

    int insertSelective(WeiXinStore record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    WeiXinStore selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(WeiXinStore record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(WeiXinStore record);
    
    List<WeiXinStore> selectStoreListByBusinessName(String business_name);
}