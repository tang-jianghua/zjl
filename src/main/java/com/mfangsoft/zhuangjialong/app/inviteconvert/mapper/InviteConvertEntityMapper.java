package com.mfangsoft.zhuangjialong.app.inviteconvert.mapper;

import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteConvertEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface InviteConvertEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(String convert_no);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(InviteConvertEntity record);

    int insertSelective(InviteConvertEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    InviteConvertEntity selectByPrimaryKey(String convert_no);

    int updateByPrimaryKeySelective(InviteConvertEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(InviteConvertEntity record);
    /*
     * 查询消费者已消耗的邀请人数量
     */
    int  selectConvertedInviteNo(Long customer_id);
}