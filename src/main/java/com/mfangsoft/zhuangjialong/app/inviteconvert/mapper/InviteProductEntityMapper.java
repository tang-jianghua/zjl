package com.mfangsoft.zhuangjialong.app.inviteconvert.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteProductEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface InviteProductEntityMapper {
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
    int insert(InviteProductEntity record);

    int insertSelective(InviteProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    InviteProductEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(InviteProductEntity record);
    
    /*
     * 查询所有上架产品
     */
    List<InviteProductEntity> selectAll();
}