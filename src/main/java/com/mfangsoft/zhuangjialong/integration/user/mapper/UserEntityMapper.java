package com.mfangsoft.zhuangjialong.integration.user.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@WriterRepository
public interface UserEntityMapper {
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
    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    UserEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserEntity record);
    
    List<UserEntity> selectUserForPage(Page<UserEntity>  page);
    
    int openUser(Long id);
    
    int closeUser(Long id);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UserEntity record);
    
    
    int modifyPassword(Map<String,Object> map);
    
    UserEntity getUserByAccount(String account);
    
    int resetpassword(Map<String,Object> map);
    /*
     * 根据type获取的开发者id
     */
    long getKFZIdByType();
    
    List<UserEntity> selectUserByDep(Long role_id);
}